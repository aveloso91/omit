//package com.omit.web.rest;
//
//import com.omit.OmitApp;
//
//import com.omit.domain.Questions;
//import com.omit.repository.QuestionsRepository;
//import com.omit.service.QuestionsService;
//import com.omit.web.rest.errors.ExceptionTranslator;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * Test class for the QuestionsResource REST controller.
// *
// * @see QuestionsResource
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = OmitApp.class)
//public class QuestionsResourceIntTest {
//
//    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
//    private static final String UPDATED_TEXT = "BBBBBBBBBB";
//
//    @Autowired
//    private QuestionsRepository questionsRepository;
//
//    @Autowired
//    private QuestionsService questionsService;
//
//    @Autowired
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//    @Autowired
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//    @Autowired
//    private ExceptionTranslator exceptionTranslator;
//
//    @Autowired
//    private EntityManager em;
//
//    private MockMvc restQuestionsMockMvc;
//
//    private Questions questions;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        QuestionsResource questionsResource = new QuestionsResource(questionsService, userRepository, subjectsRepository);
//        this.restQuestionsMockMvc = MockMvcBuilders.standaloneSetup(questionsResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setMessageConverters(jacksonMessageConverter).build();
//    }
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Questions createEntity(EntityManager em) {
//        Questions questions = new Questions()
//                .text(DEFAULT_TEXT);
//        return questions;
//    }
//
//    @Before
//    public void initTest() {
//        questions = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createQuestions() throws Exception {
//        int databaseSizeBeforeCreate = questionsRepository.findAll().size();
//
//        // Create the Questions
//
//        restQuestionsMockMvc.perform(post("/api/questions")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(questions)))
//            .andExpect(status().isCreated());
//
//        // Validate the Questions in the database
//        List<Questions> questionsList = questionsRepository.findAll();
//        assertThat(questionsList).hasSize(databaseSizeBeforeCreate + 1);
//        Questions testQuestions = questionsList.get(questionsList.size() - 1);
//        assertThat(testQuestions.getText()).isEqualTo(DEFAULT_TEXT);
//    }
//
//    @Test
//    @Transactional
//    public void createQuestionsWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = questionsRepository.findAll().size();
//
//        // Create the Questions with an existing ID
//        Questions existingQuestions = new Questions();
//        existingQuestions.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restQuestionsMockMvc.perform(post("/api/questions")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(existingQuestions)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Alice in the database
//        List<Questions> questionsList = questionsRepository.findAll();
//        assertThat(questionsList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    public void getAllQuestions() throws Exception {
//        // Initialize the database
//        questionsRepository.saveAndFlush(questions);
//
//        // Get all the questionsList
//        restQuestionsMockMvc.perform(get("/api/questions?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(questions.getId().intValue())))
//            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())));
//    }
//
//    @Test
//    @Transactional
//    public void getQuestions() throws Exception {
//        // Initialize the database
//        questionsRepository.saveAndFlush(questions);
//
//        // Get the questions
//        restQuestionsMockMvc.perform(get("/api/questions/{id}", questions.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(questions.getId().intValue()))
//            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingQuestions() throws Exception {
//        // Get the questions
//        restQuestionsMockMvc.perform(get("/api/questions/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateQuestions() throws Exception {
//        // Initialize the database
//        questionsService.save(questions);
//
//        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
//
//        // Update the questions
//        Questions updatedQuestions = questionsRepository.findOne(questions.getId());
//        updatedQuestions
//                .text(UPDATED_TEXT);
//
//        restQuestionsMockMvc.perform(put("/api/questions")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedQuestions)))
//            .andExpect(status().isOk());
//
//        // Validate the Questions in the database
//        List<Questions> questionsList = questionsRepository.findAll();
//        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
//        Questions testQuestions = questionsList.get(questionsList.size() - 1);
//        assertThat(testQuestions.getText()).isEqualTo(UPDATED_TEXT);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingQuestions() throws Exception {
//        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
//
//        // Create the Questions
//
//        // If the entity doesn't have an ID, it will be created instead of just being updated
//        restQuestionsMockMvc.perform(put("/api/questions")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(questions)))
//            .andExpect(status().isCreated());
//
//        // Validate the Questions in the database
//        List<Questions> questionsList = questionsRepository.findAll();
//        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate + 1);
//    }
//
//    @Test
//    @Transactional
//    public void deleteQuestions() throws Exception {
//        // Initialize the database
//        questionsService.save(questions);
//
//        int databaseSizeBeforeDelete = questionsRepository.findAll().size();
//
//        // Get the questions
//        restQuestionsMockMvc.perform(delete("/api/questions/{id}", questions.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<Questions> questionsList = questionsRepository.findAll();
//        assertThat(questionsList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Questions.class);
//    }
//}
