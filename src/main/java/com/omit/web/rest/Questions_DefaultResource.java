package com.omit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.omit.domain.Questions_Default;
import com.omit.service.Questions_DefaultService;
import com.omit.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Questions_Default.
 */
@RestController
@RequestMapping("/api")
public class Questions_DefaultResource {

    private final Logger log = LoggerFactory.getLogger(Questions_DefaultResource.class);

    private static final String ENTITY_NAME = "questions_Default";
        
    private final Questions_DefaultService questions_DefaultService;

    public Questions_DefaultResource(Questions_DefaultService questions_DefaultService) {
        this.questions_DefaultService = questions_DefaultService;
    }

    /**
     * POST  /questions-defaults : Create a new questions_Default.
     *
     * @param questions_Default the questions_Default to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questions_Default, or with status 400 (Bad Request) if the questions_Default has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/questions-defaults")
    @Timed
    public ResponseEntity<Questions_Default> createQuestions_Default(@RequestBody Questions_Default questions_Default) throws URISyntaxException {
        log.debug("REST request to save Questions_Default : {}", questions_Default);
        if (questions_Default.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new questions_Default cannot already have an ID")).body(null);
        }
        Questions_Default result = questions_DefaultService.save(questions_Default);
        return ResponseEntity.created(new URI("/api/questions-defaults/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /questions-defaults : Updates an existing questions_Default.
     *
     * @param questions_Default the questions_Default to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questions_Default,
     * or with status 400 (Bad Request) if the questions_Default is not valid,
     * or with status 500 (Internal Server Error) if the questions_Default couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/questions-defaults")
    @Timed
    public ResponseEntity<Questions_Default> updateQuestions_Default(@RequestBody Questions_Default questions_Default) throws URISyntaxException {
        log.debug("REST request to update Questions_Default : {}", questions_Default);
        if (questions_Default.getId() == null) {
            return createQuestions_Default(questions_Default);
        }
        Questions_Default result = questions_DefaultService.save(questions_Default);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, questions_Default.getId().toString()))
            .body(result);
    }

    /**
     * GET  /questions-defaults : get all the questions_Defaults.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of questions_Defaults in body
     */
    @GetMapping("/questions-defaults")
    @Timed
    public List<Questions_Default> getAllQuestions_Defaults() {
        log.debug("REST request to get all Questions_Defaults");
        return questions_DefaultService.findAll();
    }

    /**
     * GET  /questions-defaults/:id : get the "id" questions_Default.
     *
     * @param id the id of the questions_Default to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questions_Default, or with status 404 (Not Found)
     */
    @GetMapping("/questions-defaults/{id}")
    @Timed
    public ResponseEntity<Questions_Default> getQuestions_Default(@PathVariable Long id) {
        log.debug("REST request to get Questions_Default : {}", id);
        Questions_Default questions_Default = questions_DefaultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(questions_Default));
    }

    /**
     * DELETE  /questions-defaults/:id : delete the "id" questions_Default.
     *
     * @param id the id of the questions_Default to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/questions-defaults/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestions_Default(@PathVariable Long id) {
        log.debug("REST request to delete Questions_Default : {}", id);
        questions_DefaultService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
