package com.omit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.omit.domain.Degrees;
import com.omit.service.DegreesService;
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
 * REST controller for managing Degrees.
 */
@RestController
@RequestMapping("/api")
public class DegreesResource {

    private final Logger log = LoggerFactory.getLogger(DegreesResource.class);

    private static final String ENTITY_NAME = "degrees";
        
    private final DegreesService degreesService;

    public DegreesResource(DegreesService degreesService) {
        this.degreesService = degreesService;
    }

    /**
     * POST  /degrees : Create a new degrees.
     *
     * @param degrees the degrees to create
     * @return the ResponseEntity with status 201 (Created) and with body the new degrees, or with status 400 (Bad Request) if the degrees has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/degrees")
    @Timed
    public ResponseEntity<Degrees> createDegrees(@RequestBody Degrees degrees) throws URISyntaxException {
        log.debug("REST request to save Degrees : {}", degrees);
        if (degrees.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new degrees cannot already have an ID")).body(null);
        }
        Degrees result = degreesService.save(degrees);
        return ResponseEntity.created(new URI("/api/degrees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /degrees : Updates an existing degrees.
     *
     * @param degrees the degrees to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated degrees,
     * or with status 400 (Bad Request) if the degrees is not valid,
     * or with status 500 (Internal Server Error) if the degrees couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/degrees")
    @Timed
    public ResponseEntity<Degrees> updateDegrees(@RequestBody Degrees degrees) throws URISyntaxException {
        log.debug("REST request to update Degrees : {}", degrees);
        if (degrees.getId() == null) {
            return createDegrees(degrees);
        }
        Degrees result = degreesService.save(degrees);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, degrees.getId().toString()))
            .body(result);
    }

    /**
     * GET  /degrees : get all the degrees.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of degrees in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/degrees")
    @Timed
    public ResponseEntity<List<Degrees>> getAllDegrees(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Degrees");
        Page<Degrees> page = degreesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/degrees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /degrees/:id : get the "id" degrees.
     *
     * @param id the id of the degrees to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the degrees, or with status 404 (Not Found)
     */
    @GetMapping("/degrees/{id}")
    @Timed
    public ResponseEntity<Degrees> getDegrees(@PathVariable Long id) {
        log.debug("REST request to get Degrees : {}", id);
        Degrees degrees = degreesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(degrees));
    }

    /**
     * DELETE  /degrees/:id : delete the "id" degrees.
     *
     * @param id the id of the degrees to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/degrees/{id}")
    @Timed
    public ResponseEntity<Void> deleteDegrees(@PathVariable Long id) {
        log.debug("REST request to delete Degrees : {}", id);
        degreesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
