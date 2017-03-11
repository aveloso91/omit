package com.omit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.omit.domain.Subjects;
import com.omit.service.SubjectsService;
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
 * REST controller for managing Subjects.
 */
@RestController
@RequestMapping("/api")
public class SubjectsResource {

    private final Logger log = LoggerFactory.getLogger(SubjectsResource.class);

    private static final String ENTITY_NAME = "subjects";
        
    private final SubjectsService subjectsService;

    public SubjectsResource(SubjectsService subjectsService) {
        this.subjectsService = subjectsService;
    }

    /**
     * POST  /subjects : Create a new subjects.
     *
     * @param subjects the subjects to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subjects, or with status 400 (Bad Request) if the subjects has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/subjects")
    @Timed
    public ResponseEntity<Subjects> createSubjects(@RequestBody Subjects subjects) throws URISyntaxException {
        log.debug("REST request to save Subjects : {}", subjects);
        if (subjects.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new subjects cannot already have an ID")).body(null);
        }
        Subjects result = subjectsService.save(subjects);
        return ResponseEntity.created(new URI("/api/subjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subjects : Updates an existing subjects.
     *
     * @param subjects the subjects to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subjects,
     * or with status 400 (Bad Request) if the subjects is not valid,
     * or with status 500 (Internal Server Error) if the subjects couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/subjects")
    @Timed
    public ResponseEntity<Subjects> updateSubjects(@RequestBody Subjects subjects) throws URISyntaxException {
        log.debug("REST request to update Subjects : {}", subjects);
        if (subjects.getId() == null) {
            return createSubjects(subjects);
        }
        Subjects result = subjectsService.save(subjects);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subjects.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subjects : get all the subjects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of subjects in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/subjects")
    @Timed
    public ResponseEntity<List<Subjects>> getAllSubjects(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Subjects");
        Page<Subjects> page = subjectsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/subjects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /subjects/:id : get the "id" subjects.
     *
     * @param id the id of the subjects to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subjects, or with status 404 (Not Found)
     */
    @GetMapping("/subjects/{id}")
    @Timed
    public ResponseEntity<Subjects> getSubjects(@PathVariable Long id) {
        log.debug("REST request to get Subjects : {}", id);
        Subjects subjects = subjectsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(subjects));
    }

    /**
     * DELETE  /subjects/:id : delete the "id" subjects.
     *
     * @param id the id of the subjects to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/subjects/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubjects(@PathVariable Long id) {
        log.debug("REST request to delete Subjects : {}", id);
        subjectsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
