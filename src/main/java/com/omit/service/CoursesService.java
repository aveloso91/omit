package com.omit.service;

import com.omit.domain.Courses;
import com.omit.repository.CoursesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Courses.
 */
@Service
@Transactional
public class CoursesService {

    private final Logger log = LoggerFactory.getLogger(CoursesService.class);
    
    private final CoursesRepository coursesRepository;

    public CoursesService(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    /**
     * Save a courses.
     *
     * @param courses the entity to save
     * @return the persisted entity
     */
    public Courses save(Courses courses) {
        log.debug("Request to save Courses : {}", courses);
        Courses result = coursesRepository.save(courses);
        return result;
    }

    /**
     *  Get all the courses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Courses> findAll(Pageable pageable) {
        log.debug("Request to get all Courses");
        Page<Courses> result = coursesRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one courses by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Courses findOne(Long id) {
        log.debug("Request to get Courses : {}", id);
        Courses courses = coursesRepository.findOne(id);
        return courses;
    }

    /**
     *  Delete the  courses by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Courses : {}", id);
        coursesRepository.delete(id);
    }
}
