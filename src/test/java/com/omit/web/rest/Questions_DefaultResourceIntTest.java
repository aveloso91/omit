package com.omit.web.rest;

import com.omit.OmitApp;

import com.omit.domain.Questions_Default;
import com.omit.repository.Questions_DefaultRepository;
import com.omit.service.Questions_DefaultService;
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
 * Test class for the Questions_DefaultResource REST controller.
 *
 * @see Questions_DefaultResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OmitApp.class)
public class Questions_DefaultResourceIntTest {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    @Autowired
    private Questions_DefaultRepository questions_DefaultRepository;

    @Autowired
    private Questions_DefaultService questions_DefaultService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuestions_DefaultMockMvc;

    private Questions_Default questions_Default;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Questions_DefaultResource questions_DefaultResource = new Questions_DefaultResource(questions_DefaultService);
        this.restQuestions_DefaultMockMvc = MockMvcBuilders.standaloneSetup(questions_DefaultResource)
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
    public static Questions_Default createEntity(EntityManager em) {
        Questions_Default questions_Default = new Questions_Default()
                .text(DEFAULT_TEXT);
        return questions_Default;
    }

    @Before
    public void initTest() {
        questions_Default = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestions_Default() throws Exception {
        int databaseSizeBeforeCreate = questions_DefaultRepository.findAll().size();

        // Create the Questions_Default

        restQuestions_DefaultMockMvc.perform(post("/api/questions-defaults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questions_Default)))
            .andExpect(status().isCreated());

        // Validate the Questions_Default in the database
        List<Questions_Default> questions_DefaultList = questions_DefaultRepository.findAll();
        assertThat(questions_DefaultList).hasSize(databaseSizeBeforeCreate + 1);
        Questions_Default testQuestions_Default = questions_DefaultList.get(questions_DefaultList.size() - 1);
        assertThat(testQuestions_Default.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void createQuestions_DefaultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questions_DefaultRepository.findAll().size();

        // Create the Questions_Default with an existing ID
        Questions_Default existingQuestions_Default = new Questions_Default();
        existingQuestions_Default.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestions_DefaultMockMvc.perform(post("/api/questions-defaults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingQuestions_Default)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Questions_Default> questions_DefaultList = questions_DefaultRepository.findAll();
        assertThat(questions_DefaultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQuestions_Defaults() throws Exception {
        // Initialize the database
        questions_DefaultRepository.saveAndFlush(questions_Default);

        // Get all the questions_DefaultList
        restQuestions_DefaultMockMvc.perform(get("/api/questions-defaults?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questions_Default.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())));
    }

    @Test
    @Transactional
    public void getQuestions_Default() throws Exception {
        // Initialize the database
        questions_DefaultRepository.saveAndFlush(questions_Default);

        // Get the questions_Default
        restQuestions_DefaultMockMvc.perform(get("/api/questions-defaults/{id}", questions_Default.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questions_Default.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuestions_Default() throws Exception {
        // Get the questions_Default
        restQuestions_DefaultMockMvc.perform(get("/api/questions-defaults/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestions_Default() throws Exception {
        // Initialize the database
        questions_DefaultService.save(questions_Default);

        int databaseSizeBeforeUpdate = questions_DefaultRepository.findAll().size();

        // Update the questions_Default
        Questions_Default updatedQuestions_Default = questions_DefaultRepository.findOne(questions_Default.getId());
        updatedQuestions_Default
                .text(UPDATED_TEXT);

        restQuestions_DefaultMockMvc.perform(put("/api/questions-defaults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestions_Default)))
            .andExpect(status().isOk());

        // Validate the Questions_Default in the database
        List<Questions_Default> questions_DefaultList = questions_DefaultRepository.findAll();
        assertThat(questions_DefaultList).hasSize(databaseSizeBeforeUpdate);
        Questions_Default testQuestions_Default = questions_DefaultList.get(questions_DefaultList.size() - 1);
        assertThat(testQuestions_Default.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestions_Default() throws Exception {
        int databaseSizeBeforeUpdate = questions_DefaultRepository.findAll().size();

        // Create the Questions_Default

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuestions_DefaultMockMvc.perform(put("/api/questions-defaults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questions_Default)))
            .andExpect(status().isCreated());

        // Validate the Questions_Default in the database
        List<Questions_Default> questions_DefaultList = questions_DefaultRepository.findAll();
        assertThat(questions_DefaultList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQuestions_Default() throws Exception {
        // Initialize the database
        questions_DefaultService.save(questions_Default);

        int databaseSizeBeforeDelete = questions_DefaultRepository.findAll().size();

        // Get the questions_Default
        restQuestions_DefaultMockMvc.perform(delete("/api/questions-defaults/{id}", questions_Default.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Questions_Default> questions_DefaultList = questions_DefaultRepository.findAll();
        assertThat(questions_DefaultList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Questions_Default.class);
    }
}
