package com.omit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.omit.domain.Users_Subjects;
import com.omit.service.Users_SubjectsService;
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
 * REST controller for managing Users_Subjects.
 */
@RestController
@RequestMapping("/api")
public class Users_SubjectsResource {

    private final Logger log = LoggerFactory.getLogger(Users_SubjectsResource.class);

    private static final String ENTITY_NAME = "users_Subjects";
        
    private final Users_SubjectsService users_SubjectsService;

    public Users_SubjectsResource(Users_SubjectsService users_SubjectsService) {
        this.users_SubjectsService = users_SubjectsService;
    }

    /**
     * POST  /users-subjects : Create a new users_Subjects.
     *
     * @param users_Subjects the users_Subjects to create
     * @return the ResponseEntity with status 201 (Created) and with body the new users_Subjects, or with status 400 (Bad Request) if the users_Subjects has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/users-subjects")
    @Timed
    public ResponseEntity<Users_Subjects> createUsers_Subjects(@RequestBody Users_Subjects users_Subjects) throws URISyntaxException {
        log.debug("REST request to save Users_Subjects : {}", users_Subjects);
        if (users_Subjects.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new users_Subjects cannot already have an ID")).body(null);
        }
        Users_Subjects result = users_SubjectsService.save(users_Subjects);
        return ResponseEntity.created(new URI("/api/users-subjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /users-subjects : Updates an existing users_Subjects.
     *
     * @param users_Subjects the users_Subjects to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated users_Subjects,
     * or with status 400 (Bad Request) if the users_Subjects is not valid,
     * or with status 500 (Internal Server Error) if the users_Subjects couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/users-subjects")
    @Timed
    public ResponseEntity<Users_Subjects> updateUsers_Subjects(@RequestBody Users_Subjects users_Subjects) throws URISyntaxException {
        log.debug("REST request to update Users_Subjects : {}", users_Subjects);
        if (users_Subjects.getId() == null) {
            return createUsers_Subjects(users_Subjects);
        }
        Users_Subjects result = users_SubjectsService.save(users_Subjects);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, users_Subjects.getId().toString()))
            .body(result);
    }

    /**
     * GET  /users-subjects : get all the users_Subjects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of users_Subjects in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/users-subjects")
    @Timed
    public ResponseEntity<List<Users_Subjects>> getAllUsers_Subjects(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Users_Subjects");
        Page<Users_Subjects> page = users_SubjectsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users-subjects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /users-subjects/:id : get the "id" users_Subjects.
     *
     * @param id the id of the users_Subjects to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the users_Subjects, or with status 404 (Not Found)
     */
    @GetMapping("/users-subjects/{id}")
    @Timed
    public ResponseEntity<Users_Subjects> getUsers_Subjects(@PathVariable Long id) {
        log.debug("REST request to get Users_Subjects : {}", id);
        Users_Subjects users_Subjects = users_SubjectsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(users_Subjects));
    }

    /**
     * DELETE  /users-subjects/:id : delete the "id" users_Subjects.
     *
     * @param id the id of the users_Subjects to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users-subjects/{id}")
    @Timed
    public ResponseEntity<Void> deleteUsers_Subjects(@PathVariable Long id) {
        log.debug("REST request to delete Users_Subjects : {}", id);
        users_SubjectsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
