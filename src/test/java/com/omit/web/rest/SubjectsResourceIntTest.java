package com.omit.web.rest;

import com.omit.OmitApp;

import com.omit.domain.Subjects;
import com.omit.repository.SubjectsRepository;
import com.omit.service.SubjectsService;
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
 * Test class for the SubjectsResource REST controller.
 *
 * @see SubjectsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OmitApp.class)
public class SubjectsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SubjectsRepository subjectsRepository;

    @Autowired
    private SubjectsService subjectsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSubjectsMockMvc;

    private Subjects subjects;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubjectsResource subjectsResource = new SubjectsResource(subjectsService);
        this.restSubjectsMockMvc = MockMvcBuilders.standaloneSetup(subjectsResource)
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
    public static Subjects createEntity(EntityManager em) {
        Subjects subjects = new Subjects()
                .name(DEFAULT_NAME);
        return subjects;
    }

    @Before
    public void initTest() {
        subjects = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubjects() throws Exception {
        int databaseSizeBeforeCreate = subjectsRepository.findAll().size();

        // Create the Subjects

        restSubjectsMockMvc.perform(post("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjects)))
            .andExpect(status().isCreated());

        // Validate the Subjects in the database
        List<Subjects> subjectsList = subjectsRepository.findAll();
        assertThat(subjectsList).hasSize(databaseSizeBeforeCreate + 1);
        Subjects testSubjects = subjectsList.get(subjectsList.size() - 1);
        assertThat(testSubjects.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSubjectsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subjectsRepository.findAll().size();

        // Create the Subjects with an existing ID
        Subjects existingSubjects = new Subjects();
        existingSubjects.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubjectsMockMvc.perform(post("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSubjects)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Subjects> subjectsList = subjectsRepository.findAll();
        assertThat(subjectsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSubjects() throws Exception {
        // Initialize the database
        subjectsRepository.saveAndFlush(subjects);

        // Get all the subjectsList
        restSubjectsMockMvc.perform(get("/api/subjects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subjects.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSubjects() throws Exception {
        // Initialize the database
        subjectsRepository.saveAndFlush(subjects);

        // Get the subjects
        restSubjectsMockMvc.perform(get("/api/subjects/{id}", subjects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subjects.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubjects() throws Exception {
        // Get the subjects
        restSubjectsMockMvc.perform(get("/api/subjects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubjects() throws Exception {
        // Initialize the database
        subjectsService.save(subjects);

        int databaseSizeBeforeUpdate = subjectsRepository.findAll().size();

        // Update the subjects
        Subjects updatedSubjects = subjectsRepository.findOne(subjects.getId());
        updatedSubjects
                .name(UPDATED_NAME);

        restSubjectsMockMvc.perform(put("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubjects)))
            .andExpect(status().isOk());

        // Validate the Subjects in the database
        List<Subjects> subjectsList = subjectsRepository.findAll();
        assertThat(subjectsList).hasSize(databaseSizeBeforeUpdate);
        Subjects testSubjects = subjectsList.get(subjectsList.size() - 1);
        assertThat(testSubjects.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSubjects() throws Exception {
        int databaseSizeBeforeUpdate = subjectsRepository.findAll().size();

        // Create the Subjects

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSubjectsMockMvc.perform(put("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjects)))
            .andExpect(status().isCreated());

        // Validate the Subjects in the database
        List<Subjects> subjectsList = subjectsRepository.findAll();
        assertThat(subjectsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSubjects() throws Exception {
        // Initialize the database
        subjectsService.save(subjects);

        int databaseSizeBeforeDelete = subjectsRepository.findAll().size();

        // Get the subjects
        restSubjectsMockMvc.perform(delete("/api/subjects/{id}", subjects.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Subjects> subjectsList = subjectsRepository.findAll();
        assertThat(subjectsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subjects.class);
    }
}
