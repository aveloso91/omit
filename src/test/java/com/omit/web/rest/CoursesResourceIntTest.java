package com.omit.web.rest;

import com.omit.OmitApp;

import com.omit.domain.Courses;
import com.omit.repository.CoursesRepository;
import com.omit.service.CoursesService;
import com.omit.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CoursesResource REST controller.
 *
 * @see CoursesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OmitApp.class)
public class CoursesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCoursesMockMvc;

    private Courses courses;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CoursesResource coursesResource = new CoursesResource(coursesService);
        this.restCoursesMockMvc = MockMvcBuilders.standaloneSetup(coursesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Courses createEntity(EntityManager em) {
        Courses courses = new Courses()
                .name(DEFAULT_NAME);
        return courses;
    }

    @Before
    public void initTest() {
        courses = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourses() throws Exception {
        int databaseSizeBeforeCreate = coursesRepository.findAll().size();

        // Create the Courses

        restCoursesMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courses)))
            .andExpect(status().isCreated());

        // Validate the Courses in the database
        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeCreate + 1);
        Courses testCourses = coursesList.get(coursesList.size() - 1);
        assertThat(testCourses.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCoursesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coursesRepository.findAll().size();

        // Create the Courses with an existing ID
        Courses existingCourses = new Courses();
        existingCourses.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoursesMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCourses)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCourses() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList
        restCoursesMockMvc.perform(get("/api/courses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courses.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCourses() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get the courses
        restCoursesMockMvc.perform(get("/api/courses/{id}", courses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courses.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCourses() throws Exception {
        // Get the courses
        restCoursesMockMvc.perform(get("/api/courses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourses() throws Exception {
        // Initialize the database
        coursesService.save(courses);

        int databaseSizeBeforeUpdate = coursesRepository.findAll().size();

        // Update the courses
        Courses updatedCourses = coursesRepository.findOne(courses.getId());
        updatedCourses
                .name(UPDATED_NAME);

        restCoursesMockMvc.perform(put("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCourses)))
            .andExpect(status().isOk());

        // Validate the Courses in the database
        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeUpdate);
        Courses testCourses = coursesList.get(coursesList.size() - 1);
        assertThat(testCourses.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCourses() throws Exception {
        int databaseSizeBeforeUpdate = coursesRepository.findAll().size();

        // Create the Courses

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoursesMockMvc.perform(put("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courses)))
            .andExpect(status().isCreated());

        // Validate the Courses in the database
        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCourses() throws Exception {
        // Initialize the database
        coursesService.save(courses);

        int databaseSizeBeforeDelete = coursesRepository.findAll().size();

        // Get the courses
        restCoursesMockMvc.perform(delete("/api/courses/{id}", courses.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Courses.class);
    }
}
