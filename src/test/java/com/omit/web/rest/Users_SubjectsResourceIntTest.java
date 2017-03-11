package com.omit.web.rest;

import com.omit.OmitApp;

import com.omit.domain.Users_Subjects;
import com.omit.repository.Users_SubjectsRepository;
import com.omit.service.Users_SubjectsService;
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
 * Test class for the Users_SubjectsResource REST controller.
 *
 * @see Users_SubjectsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OmitApp.class)
public class Users_SubjectsResourceIntTest {

    @Autowired
    private Users_SubjectsRepository users_SubjectsRepository;

    @Autowired
    private Users_SubjectsService users_SubjectsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUsers_SubjectsMockMvc;

    private Users_Subjects users_Subjects;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Users_SubjectsResource users_SubjectsResource = new Users_SubjectsResource(users_SubjectsService);
        this.restUsers_SubjectsMockMvc = MockMvcBuilders.standaloneSetup(users_SubjectsResource)
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
    public static Users_Subjects createEntity(EntityManager em) {
        Users_Subjects users_Subjects = new Users_Subjects();
        return users_Subjects;
    }

    @Before
    public void initTest() {
        users_Subjects = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsers_Subjects() throws Exception {
        int databaseSizeBeforeCreate = users_SubjectsRepository.findAll().size();

        // Create the Users_Subjects

        restUsers_SubjectsMockMvc.perform(post("/api/users-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(users_Subjects)))
            .andExpect(status().isCreated());

        // Validate the Users_Subjects in the database
        List<Users_Subjects> users_SubjectsList = users_SubjectsRepository.findAll();
        assertThat(users_SubjectsList).hasSize(databaseSizeBeforeCreate + 1);
        Users_Subjects testUsers_Subjects = users_SubjectsList.get(users_SubjectsList.size() - 1);
    }

    @Test
    @Transactional
    public void createUsers_SubjectsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = users_SubjectsRepository.findAll().size();

        // Create the Users_Subjects with an existing ID
        Users_Subjects existingUsers_Subjects = new Users_Subjects();
        existingUsers_Subjects.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsers_SubjectsMockMvc.perform(post("/api/users-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUsers_Subjects)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Users_Subjects> users_SubjectsList = users_SubjectsRepository.findAll();
        assertThat(users_SubjectsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUsers_Subjects() throws Exception {
        // Initialize the database
        users_SubjectsRepository.saveAndFlush(users_Subjects);

        // Get all the users_SubjectsList
        restUsers_SubjectsMockMvc.perform(get("/api/users-subjects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(users_Subjects.getId().intValue())));
    }

    @Test
    @Transactional
    public void getUsers_Subjects() throws Exception {
        // Initialize the database
        users_SubjectsRepository.saveAndFlush(users_Subjects);

        // Get the users_Subjects
        restUsers_SubjectsMockMvc.perform(get("/api/users-subjects/{id}", users_Subjects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(users_Subjects.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUsers_Subjects() throws Exception {
        // Get the users_Subjects
        restUsers_SubjectsMockMvc.perform(get("/api/users-subjects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsers_Subjects() throws Exception {
        // Initialize the database
        users_SubjectsService.save(users_Subjects);

        int databaseSizeBeforeUpdate = users_SubjectsRepository.findAll().size();

        // Update the users_Subjects
        Users_Subjects updatedUsers_Subjects = users_SubjectsRepository.findOne(users_Subjects.getId());

        restUsers_SubjectsMockMvc.perform(put("/api/users-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsers_Subjects)))
            .andExpect(status().isOk());

        // Validate the Users_Subjects in the database
        List<Users_Subjects> users_SubjectsList = users_SubjectsRepository.findAll();
        assertThat(users_SubjectsList).hasSize(databaseSizeBeforeUpdate);
        Users_Subjects testUsers_Subjects = users_SubjectsList.get(users_SubjectsList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingUsers_Subjects() throws Exception {
        int databaseSizeBeforeUpdate = users_SubjectsRepository.findAll().size();

        // Create the Users_Subjects

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUsers_SubjectsMockMvc.perform(put("/api/users-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(users_Subjects)))
            .andExpect(status().isCreated());

        // Validate the Users_Subjects in the database
        List<Users_Subjects> users_SubjectsList = users_SubjectsRepository.findAll();
        assertThat(users_SubjectsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUsers_Subjects() throws Exception {
        // Initialize the database
        users_SubjectsService.save(users_Subjects);

        int databaseSizeBeforeDelete = users_SubjectsRepository.findAll().size();

        // Get the users_Subjects
        restUsers_SubjectsMockMvc.perform(delete("/api/users-subjects/{id}", users_Subjects.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Users_Subjects> users_SubjectsList = users_SubjectsRepository.findAll();
        assertThat(users_SubjectsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Users_Subjects.class);
    }
}
