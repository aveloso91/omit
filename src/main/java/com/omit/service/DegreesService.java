package com.omit.service;

import com.omit.domain.Degrees;
import com.omit.repository.DegreesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Degrees.
 */
@Service
@Transactional
public class DegreesService {

    private final Logger log = LoggerFactory.getLogger(DegreesService.class);
    
    private final DegreesRepository degreesRepository;

    public DegreesService(DegreesRepository degreesRepository) {
        this.degreesRepository = degreesRepository;
    }

    /**
     * Save a degrees.
     *
     * @param degrees the entity to save
     * @return the persisted entity
     */
    public Degrees save(Degrees degrees) {
        log.debug("Request to save Degrees : {}", degrees);
        Degrees result = degreesRepository.save(degrees);
        return result;
    }

    /**
     *  Get all the degrees.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Degrees> findAll(Pageable pageable) {
        log.debug("Request to get all Degrees");
        Page<Degrees> result = degreesRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one degrees by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Degrees findOne(Long id) {
        log.debug("Request to get Degrees : {}", id);
        Degrees degrees = degreesRepository.findOne(id);
        return degrees;
    }

    /**
     *  Delete the  degrees by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Degrees : {}", id);
        degreesRepository.delete(id);
    }
}
