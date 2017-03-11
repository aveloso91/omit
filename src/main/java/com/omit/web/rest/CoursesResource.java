package com.omit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.omit.domain.Courses;
import com.omit.service.CoursesService;
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
 * REST controller for managing Courses.
 */
@RestController
@RequestMapping("/api")
public class CoursesResource {

    private final Logger log = LoggerFactory.getLogger(CoursesResource.class);

    private static final String ENTITY_NAME = "courses";
        
    private final CoursesService coursesService;

    public CoursesResource(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    /**
     * POST  /courses : Create a new courses.
     *
     * @param courses the courses to create
     * @return the ResponseEntity with status 201 (Created) and with body the new courses, or with status 400 (Bad Request) if the courses has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/courses")
    @Timed
    public ResponseEntity<Courses> createCourses(@RequestBody Courses courses) throws URISyntaxException {
        log.debug("REST request to save Courses : {}", courses);
        if (courses.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new courses cannot already have an ID")).body(null);
        }
        Courses result = coursesService.save(courses);
        return ResponseEntity.created(new URI("/api/courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /courses : Updates an existing courses.
     *
     * @param courses the courses to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated courses,
     * or with status 400 (Bad Request) if the courses is not valid,
     * or with status 500 (Internal Server Error) if the courses couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/courses")
    @Timed
    public ResponseEntity<Courses> updateCourses(@RequestBody Courses courses) throws URISyntaxException {
        log.debug("REST request to update Courses : {}", courses);
        if (courses.getId() == null) {
            return createCourses(courses);
        }
        Courses result = coursesService.save(courses);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, courses.getId().toString()))
            .body(result);
    }

    /**
     * GET  /courses : get all the courses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of courses in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/courses")
    @Timed
    public ResponseEntity<List<Courses>> getAllCourses(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Courses");
        Page<Courses> page = coursesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/courses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /courses/:id : get the "id" courses.
     *
     * @param id the id of the courses to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the courses, or with status 404 (Not Found)
     */
    @GetMapping("/courses/{id}")
    @Timed
    public ResponseEntity<Courses> getCourses(@PathVariable Long id) {
        log.debug("REST request to get Courses : {}", id);
        Courses courses = coursesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(courses));
    }

    /**
     * DELETE  /courses/:id : delete the "id" courses.
     *
     * @param id the id of the courses to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/courses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCourses(@PathVariable Long id) {
        log.debug("REST request to delete Courses : {}", id);
        coursesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
