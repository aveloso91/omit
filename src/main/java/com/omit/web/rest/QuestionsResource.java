package com.omit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.omit.domain.Questions;
import com.omit.repository.SubjectsRepository;
import com.omit.repository.UserRepository;
import com.omit.service.QuestionsService;
import com.omit.web.rest.util.HeaderUtil;
import com.omit.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Questions.
 */
@RestController
@RequestMapping("/api")
public class QuestionsResource {

    private final Logger log = LoggerFactory.getLogger(QuestionsResource.class);

    private static final String ENTITY_NAME = "questions";

    private final QuestionsService questionsService;

    private final UserRepository userRepository;

    private final SubjectsRepository subjectsRepository;


    public QuestionsResource(QuestionsService questionsService, UserRepository userRepository, SubjectsRepository subjectsRepository) {
        this.questionsService = questionsService;
        this.userRepository = userRepository;
        this.subjectsRepository = subjectsRepository;
    }

    /**
     * POST  /questions : Create a new questions.
     *
     * @param questions the questions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questions, or with status 400 (Bad Request) if the questions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/questions")
    @Timed
    public ResponseEntity<Questions> createQuestions(@RequestBody Questions questions) throws URISyntaxException {
        log.debug("REST request to save Questions : {}", questions);
        if (questions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new questions cannot already have an ID")).body(null);
        }
        Questions result = questionsService.save(questions);
        return ResponseEntity.created(new URI("/api/questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /questions : Updates an existing questions.
     *
     * @param questions the questions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questions,
     * or with status 400 (Bad Request) if the questions is not valid,
     * or with status 500 (Internal Server Error) if the questions couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/questions")
    @Timed
    public ResponseEntity<Questions> updateQuestions(@RequestBody Questions questions) throws URISyntaxException {
        log.debug("REST request to update Questions : {}", questions);
        if (questions.getId() == null) {
            return createQuestions(questions);
        }
        Questions result = questionsService.save(questions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, questions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /questions : get all the questions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of questions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/questions")
    @Timed
    public ResponseEntity<List<Questions>> getAllQuestions(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Questions");
        Page<Questions> page = questionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/questions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /questions/:id : get the "id" questions.
     *
     * @param id the id of the questions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questions, or with status 404 (Not Found)
     */
    @GetMapping("/questions/{id}")
    @Timed
    public ResponseEntity<Questions> getQuestions(@PathVariable Long id) {
        log.debug("REST request to get Questions : {}", id);
        Questions questions = questionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(questions));
    }

    /**
     * DELETE  /questions/:id : delete the "id" questions.
     *
     * @param id the id of the questions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/questions/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestions(@PathVariable Long id) {
        log.debug("REST request to delete Questions : {}", id);
        questionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * Get teachers by subject.
     * @param idTeacher id of teacher
     * @param idSubject id of subject
     * @return List of teachers
     */
    @RequestMapping("/questions/questionsBySubjectTeacher/{idTeacher}/{idSubject}")
    @Timed
    public List<Questions> findQuestionsBySubjectTeacher (@PathVariable Long idTeacher,@PathVariable Long idSubject){
        log.debug("Request to get Questions of Teacher: {}", idTeacher);
        List<Questions> questionsList = questionsService.findQuestionsBySubjectTeacher(idTeacher,idSubject);

        return questionsList;
    }

    /**
     * Get teachers by subject.
     * @param idTeacher id of teacher
     * @param idSubject id of subject
     * @return List of teachers
     */
    @RequestMapping("/questions/saveQuestionsDefaultTS/{text}/{idTeacher}/{idSubject}")
    @Timed
    public void saveQuestionsDefaultTS (@PathVariable String text, @PathVariable Long idTeacher,@PathVariable Long idSubject) throws URISyntaxException {
        log.debug("Saving Questions of Teacher: {}", idTeacher);
        Questions questions = new Questions();
        questions.setText(text);
        questions.setUser(userRepository.findOneWithAuthoritiesById(idTeacher));
        questions.setSubject(subjectsRepository.findOne(idSubject));
        questionsService.save(questions);

    }

}
