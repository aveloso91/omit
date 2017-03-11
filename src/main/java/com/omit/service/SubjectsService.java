package com.omit.service;

import com.omit.domain.Subjects;
import com.omit.repository.SubjectsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Subjects.
 */
@Service
@Transactional
public class SubjectsService {

    private final Logger log = LoggerFactory.getLogger(SubjectsService.class);
    
    private final SubjectsRepository subjectsRepository;

    public SubjectsService(SubjectsRepository subjectsRepository) {
        this.subjectsRepository = subjectsRepository;
    }

    /**
     * Save a subjects.
     *
     * @param subjects the entity to save
     * @return the persisted entity
     */
    public Subjects save(Subjects subjects) {
        log.debug("Request to save Subjects : {}", subjects);
        Subjects result = subjectsRepository.save(subjects);
        return result;
    }

    /**
     *  Get all the subjects.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Subjects> findAll(Pageable pageable) {
        log.debug("Request to get all Subjects");
        Page<Subjects> result = subjectsRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one subjects by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Subjects findOne(Long id) {
        log.debug("Request to get Subjects : {}", id);
        Subjects subjects = subjectsRepository.findOne(id);
        return subjects;
    }

    /**
     *  Delete the  subjects by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Subjects : {}", id);
        subjectsRepository.delete(id);
    }
}
