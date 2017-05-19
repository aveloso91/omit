package com.omit.repository;

import com.omit.domain.SolrQuestion;
import com.omit.domain.Subjects;
import com.omit.domain.User;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

/**
 * Created by Alejandro on 5/5/17.
 */
public interface SQuestionRepository extends SolrCrudRepository<SolrQuestion, String> {

    List<SolrQuestion> findAllBySubjectAndTeacher(Long subject, Long teacher);

    List<SolrQuestion> findAllBySubjectAndTeacherAndOwner(Long subject, Long teacher, Long owner);

    List<SolrQuestion> findAllBySubjectAndTeacherAndDateBetween(String subject, String teacher, String initDate, String endDate);
}
