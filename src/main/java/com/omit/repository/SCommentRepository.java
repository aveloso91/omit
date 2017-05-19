package com.omit.repository;

import com.omit.domain.SolrComment;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

/**
 * Created by Alejandro on 5/5/17.
 */
public interface SCommentRepository extends SolrCrudRepository<SolrComment, String> {

    List<SolrComment> findAllBySubjectAndTeacher(Long subject, Long teacher);

    List<SolrComment> findAllBySubjectAndTeacherAndDateBetween(String subject, String teacher, String initDate, String endDate);
}
