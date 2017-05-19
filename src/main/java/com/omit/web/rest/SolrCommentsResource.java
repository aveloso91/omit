package com.omit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.omit.domain.SolrComment;
import com.omit.service.SolrCommentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by Alejandro on 5/5/17.
 */

@RestController
@RequestMapping("/api")
public class SolrCommentsResource {
    private final Logger log = LoggerFactory.getLogger(SolrCommentsResource.class);

    private static final String ENTITY_NAME = "solrComments";

    private final SolrCommentsService solrCommentsService;

    public SolrCommentsResource(SolrCommentsService solrCommentsService) {
        this.solrCommentsService = solrCommentsService;
    }

    /**
     * Get all comments of solr by subject and teacher.
     * @param idSubject
     * @param idTeacher
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping("/solrcomments/findCommentsByTS/{idSubject}/{idTeacher}")
    @Timed
    public List<SolrComment> findCommentsByTS (@PathVariable Long idSubject, @PathVariable Long idTeacher) throws URISyntaxException {
        log.debug("Request to get SolrComments of Teacher and Subject: {} {}", idTeacher, idSubject);
        List<SolrComment> commentList = solrCommentsService.findSolrCommentsBySubjectTeacher(idSubject,idTeacher);
        return commentList;
    }

    @RequestMapping("/solrcomments/saveComment/{idOwner}/{idSubject}/{idTeacher}/{text}/{score}")
    @Timed
    public void saveSolrComment (@PathVariable Long idOwner,@PathVariable Long idSubject,@PathVariable Long idTeacher,
                                 @PathVariable String text,@PathVariable int score){

        solrCommentsService.saveSolrComment(idOwner,idSubject,idTeacher,text,score);
    }
}
