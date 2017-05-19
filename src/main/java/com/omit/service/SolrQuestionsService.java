package com.omit.service;

import com.omit.domain.SolrQuestion;
import com.omit.repository.SQuestionRepository;
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
public class SolrQuestionsService {
    private final Logger log = LoggerFactory.getLogger(QuestionsService.class);

    private final SQuestionRepository sQuestionRepository;

    public SolrQuestionsService(SQuestionRepository sQuestionRepository) {
        this.sQuestionRepository = sQuestionRepository;
    }

    public List<SolrQuestion> findSolrQuestionsBySubjectTeacher(Long idSubject ,Long idTeacher){
        List<SolrQuestion> questionList = sQuestionRepository.findAllBySubjectAndTeacher(idSubject,idTeacher);
        return questionList;
    }

    public void saveSolrQuestion(Long idOwner, Long idSubject, Long idTeacher, String text, int score){
        SolrQuestion solrQuestion = new SolrQuestion();
        solrQuestion.setId(UUID.randomUUID().toString());
        solrQuestion.setOwner(idOwner);
        solrQuestion.setSubject(idSubject);
        solrQuestion.setTeacher(idTeacher);
        solrQuestion.setText(text);
        solrQuestion.setScore(score);
        solrQuestion.setDate(new Date());

        sQuestionRepository.save(solrQuestion);
    }

    public List<SolrQuestion> findSolrQuestionsByOwnerSubjectTeacher(Long idOwner, Long idSubject ,Long idTeacher){


        return null;
    }
}
