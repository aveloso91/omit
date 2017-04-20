package com.omit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.omit.domain.User;
import com.omit.service.CommentsService;
import com.omit.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Alejandro on 2/4/17.
 */

@RestController
@RequestMapping("/api")
public class CommentsResource {
    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "subjects";

    private final CommentsService commentsService;

    public CommentsResource(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping("/comments/{subject}")
    @Timed
    public void getAllSubjects(@PathVariable String subject){

        log.debug("REST request to get a page of Subjects", subject);

    }
}
