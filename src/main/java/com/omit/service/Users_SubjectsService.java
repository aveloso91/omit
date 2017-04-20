package com.omit.service;

import com.omit.domain.Authority;
import com.omit.domain.Subjects;
import com.omit.domain.User;
import com.omit.domain.Users_Subjects;
import com.omit.repository.Users_SubjectsRepository;
import com.omit.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * Get subjects by User.
     * @param id of User
     * @return List of Subjects
     */
    @Transactional(readOnly = true)
    public List<Subjects> findSubjectsByUser (Long id){
        log.debug("Request to get Subjects of user: {}", id);
        List<Users_Subjects> users_subjectsList =users_SubjectsRepository.findByUserIsCurrentUser();
        List<Subjects> subjectsList = new ArrayList<>();
        for(Users_Subjects u_s : users_subjectsList){
            if(!subjectsList.contains(u_s))
                subjectsList.add(u_s.getSubject());
        }
        return subjectsList;
    }

    /**
     * Get teachers by subject.
     * @param id of subject
     * @return List of teachers
     */
    @Transactional(readOnly = true)
    public List<User> findTeachersBySubject (Long id){
        log.debug("Request to get Teachers of Subject: {}", id);
        List<Users_Subjects> users_subjectsList =users_SubjectsRepository.findUsersBySubject(id);
        List<User> teachersList = new ArrayList<>();
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.TEACHER);
        for(Users_Subjects u_s : users_subjectsList){
            if(u_s.getUser().getAuthorities().contains(authority)){
                teachersList.add(u_s.getUser());
            }
        }
        return teachersList;
    }

    /**
     * Get teachers by subject.
     * @param id of subject
     * @return List of teachers
     */
    @Transactional(readOnly = true)
    public List<User> findStudentsBySubject (Long id){
        log.debug("Request to get Students of Subject: {}", id);
        List<Users_Subjects> users_subjectsList =users_SubjectsRepository.findUsersBySubject(id);
        List<User> studentsList = new ArrayList<>();
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.STUDENT);
        for(Users_Subjects u_s : users_subjectsList){
            if(u_s.getUser().getAuthorities().contains(authority)){
                studentsList.add(u_s.getUser());
            }
        }
        return studentsList;
    }
}
