package com.omit.service;

import com.omit.domain.Questions_Default;
import com.omit.repository.Questions_DefaultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Questions_Default.
 */
@Service
@Transactional
public class Questions_DefaultService {

    private final Logger log = LoggerFactory.getLogger(Questions_DefaultService.class);
    
    private final Questions_DefaultRepository questions_DefaultRepository;

    public Questions_DefaultService(Questions_DefaultRepository questions_DefaultRepository) {
        this.questions_DefaultRepository = questions_DefaultRepository;
    }

    /**
     * Save a questions_Default.
     *
     * @param questions_Default the entity to save
     * @return the persisted entity
     */
    public Questions_Default save(Questions_Default questions_Default) {
        log.debug("Request to save Questions_Default : {}", questions_Default);
        Questions_Default result = questions_DefaultRepository.save(questions_Default);
        return result;
    }

    /**
     *  Get all the questions_Defaults.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Questions_Default> findAll() {
        log.debug("Request to get all Questions_Defaults");
        List<Questions_Default> result = questions_DefaultRepository.findAll();

        return result;
    }

    /**
     *  Get one questions_Default by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Questions_Default findOne(Long id) {
        log.debug("Request to get Questions_Default : {}", id);
        Questions_Default questions_Default = questions_DefaultRepository.findOne(id);
        return questions_Default;
    }

    /**
     *  Delete the  questions_Default by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Questions_Default : {}", id);
        questions_DefaultRepository.delete(id);
    }
}
