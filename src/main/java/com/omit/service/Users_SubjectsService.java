package com.omit.service;

import com.omit.domain.Users_Subjects;
import com.omit.repository.Users_SubjectsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Users_Subjects.
 */
@Service
@Transactional
public class Users_SubjectsService {

    private final Logger log = LoggerFactory.getLogger(Users_SubjectsService.class);
    
    private final Users_SubjectsRepository users_SubjectsRepository;

    public Users_SubjectsService(Users_SubjectsRepository users_SubjectsRepository) {
        this.users_SubjectsRepository = users_SubjectsRepository;
    }

    /**
     * Save a users_Subjects.
     *
     * @param users_Subjects the entity to save
     * @return the persisted entity
     */
    public Users_Subjects save(Users_Subjects users_Subjects) {
        log.debug("Request to save Users_Subjects : {}", users_Subjects);
        Users_Subjects result = users_SubjectsRepository.save(users_Subjects);
        return result;
    }

    /**
     *  Get all the users_Subjects.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Users_Subjects> findAll(Pageable pageable) {
        log.debug("Request to get all Users_Subjects");
        Page<Users_Subjects> result = users_SubjectsRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one users_Subjects by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Users_Subjects findOne(Long id) {
        log.debug("Request to get Users_Subjects : {}", id);
        Users_Subjects users_Subjects = users_SubjectsRepository.findOne(id);
        return users_Subjects;
    }

    /**
     *  Delete the  users_Subjects by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Users_Subjects : {}", id);
        users_SubjectsRepository.delete(id);
    }
}
