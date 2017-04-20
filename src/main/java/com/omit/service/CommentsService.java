package com.omit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Alejandro on 2/4/17.
 */

@Service
@Transactional
public class CommentsService {

    private final Logger log = LoggerFactory.getLogger(SubjectsService.class);

}
