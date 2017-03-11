package com.omit.web.rest;

import com.omit.OmitApp;

import com.omit.domain.Degrees;
import com.omit.repository.DegreesRepository;
import com.omit.service.DegreesService;
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
 * Test class for the DegreesResource REST controller.
 *
 * @see DegreesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OmitApp.class)
public class DegreesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DegreesRepository degreesRepository;

    @Autowired
    private DegreesService degreesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDegreesMockMvc;

    private Degrees degrees;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DegreesResource degreesResource = new DegreesResource(degreesService);
        this.restDegreesMockMvc = MockMvcBuilders.standaloneSetup(degreesResource)
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
    public static Degrees createEntity(EntityManager em) {
        Degrees degrees = new Degrees()
                .name(DEFAULT_NAME);
        return degrees;
    }

    @Before
    public void initTest() {
        degrees = createEntity(em);
    }

    @Test
    @Transactional
    public void createDegrees() throws Exception {
        int databaseSizeBeforeCreate = degreesRepository.findAll().size();

        // Create the Degrees

        restDegreesMockMvc.perform(post("/api/degrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degrees)))
            .andExpect(status().isCreated());

        // Validate the Degrees in the database
        List<Degrees> degreesList = degreesRepository.findAll();
        assertThat(degreesList).hasSize(databaseSizeBeforeCreate + 1);
        Degrees testDegrees = degreesList.get(degreesList.size() - 1);
        assertThat(testDegrees.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDegreesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = degreesRepository.findAll().size();

        // Create the Degrees with an existing ID
        Degrees existingDegrees = new Degrees();
        existingDegrees.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDegreesMockMvc.perform(post("/api/degrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDegrees)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Degrees> degreesList = degreesRepository.findAll();
        assertThat(degreesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDegrees() throws Exception {
        // Initialize the database
        degreesRepository.saveAndFlush(degrees);

        // Get all the degreesList
        restDegreesMockMvc.perform(get("/api/degrees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(degrees.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDegrees() throws Exception {
        // Initialize the database
        degreesRepository.saveAndFlush(degrees);

        // Get the degrees
        restDegreesMockMvc.perform(get("/api/degrees/{id}", degrees.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(degrees.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDegrees() throws Exception {
        // Get the degrees
        restDegreesMockMvc.perform(get("/api/degrees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDegrees() throws Exception {
        // Initialize the database
        degreesService.save(degrees);

        int databaseSizeBeforeUpdate = degreesRepository.findAll().size();

        // Update the degrees
        Degrees updatedDegrees = degreesRepository.findOne(degrees.getId());
        updatedDegrees
                .name(UPDATED_NAME);

        restDegreesMockMvc.perform(put("/api/degrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDegrees)))
            .andExpect(status().isOk());

        // Validate the Degrees in the database
        List<Degrees> degreesList = degreesRepository.findAll();
        assertThat(degreesList).hasSize(databaseSizeBeforeUpdate);
        Degrees testDegrees = degreesList.get(degreesList.size() - 1);
        assertThat(testDegrees.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDegrees() throws Exception {
        int databaseSizeBeforeUpdate = degreesRepository.findAll().size();

        // Create the Degrees

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDegreesMockMvc.perform(put("/api/degrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degrees)))
            .andExpect(status().isCreated());

        // Validate the Degrees in the database
        List<Degrees> degreesList = degreesRepository.findAll();
        assertThat(degreesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDegrees() throws Exception {
        // Initialize the database
        degreesService.save(degrees);

        int databaseSizeBeforeDelete = degreesRepository.findAll().size();

        // Get the degrees
        restDegreesMockMvc.perform(delete("/api/degrees/{id}", degrees.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Degrees> degreesList = degreesRepository.findAll();
        assertThat(degreesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Degrees.class);
    }
}
