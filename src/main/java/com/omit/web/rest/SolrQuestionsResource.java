package com.omit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.omit.domain.SolrQuestion;
import com.omit.service.SolrQuestionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

/**
 * Created by Alejandro on 5/5/17.
 */

@RestController
@RequestMapping("/api")
public class SolrQuestionsResource {
    private final Logger log = LoggerFactory.getLogger(SolrQuestionsResource.class);

    private static final String ENTITY_NAME = "solrQuestions";

    private final SolrQuestionsService solrQuestionsService;

    public SolrQuestionsResource(SolrQuestionsService solrQuestionsService) {
        this.solrQuestionsService = solrQuestionsService;
    }

    /**
     * Get all questions answered of solr by subject and teacher.
     * @param idSubject
     * @param idTeacher
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping("/solrquestions/findQuestionsByTS/{idSubject}/{idTeacher}")
    @Timed
    public List<SolrQuestion> findQuestionsByTS (@PathVariable Long idSubject, @PathVariable Long idTeacher) throws URISyntaxException {
        log.debug("Request to get SolrQuestions of Teacher and Subject: {} {}", idTeacher, idSubject);
        List<SolrQuestion> questionList = solrQuestionsService.findSolrQuestionsBySubjectTeacher(idSubject,idTeacher);
        return questionList;
    }

    @RequestMapping("/solrquestions/saveQuestion/{idOwner}/{idSubject}/{idTeacher}/{text}/{score}")
    @Timed
    public void saveSolrQuestion (@PathVariable Long idOwner,@PathVariable Long idSubject,@PathVariable Long idTeacher,
                                  @PathVariable String text,@PathVariable int score){

        solrQuestionsService.saveSolrQuestion(idOwner,idSubject,idTeacher,text,score);
    }
}
