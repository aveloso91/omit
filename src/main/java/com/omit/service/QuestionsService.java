package com.omit.service;

import com.omit.domain.Questions;
import com.omit.repository.QuestionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Questions.
 */
@Service
@Transactional
public class QuestionsService {

    private final Logger log = LoggerFactory.getLogger(QuestionsService.class);
    
    private final QuestionsRepository questionsRepository;

    public QuestionsService(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    /**
     * Save a questions.
     *
     * @param questions the entity to save
     * @return the persisted entity
     */
    public Questions save(Questions questions) {
        log.debug("Request to save Questions : {}", questions);
        Questions result = questionsRepository.save(questions);
        return result;
    }

    /**
     *  Get all the questions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Questions> findAll(Pageable pageable) {
        log.debug("Request to get all Questions");
        Page<Questions> result = questionsRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one questions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Questions findOne(Long id) {
        log.debug("Request to get Questions : {}", id);
        Questions questions = questionsRepository.findOne(id);
        return questions;
    }

    /**
     *  Delete the  questions by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Questions : {}", id);
        questionsRepository.delete(id);
    }
}
