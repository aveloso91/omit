package com.omit.service;

import com.omit.domain.SolrComment;
import com.omit.repository.SCommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro on 5/5/17.
 */
@Service
@Transactional
public class SolrCommentsService {
    private final Logger log = LoggerFactory.getLogger(QuestionsService.class);

    private final SCommentRepository sCommentRepository;

    public SolrCommentsService(SCommentRepository sCommentRepository) {
        this.sCommentRepository = sCommentRepository;
    }

    public List<SolrComment> findSolrCommentsBySubjectTeacher(Long idSubject ,Long idTeacher){
        List<SolrComment> commentList = sCommentRepository.findAllBySubjectAndTeacher(idSubject,idTeacher);
        return commentList;
    }

    public void saveSolrComment(Long idOwner, Long idSubject, Long idTeacher, String text, int score){
        SolrComment solrComment = new SolrComment();
        solrComment.setOwner(idOwner);
        solrComment.setSubject(idSubject);
        solrComment.setTeacher(idTeacher);
        solrComment.setText(text);
        solrComment.setScore(score);
        solrComment.setDate(new Date());
        solrComment.setId(UUID.randomUUID().toString());

        sCommentRepository.save(solrComment);
    }
}
