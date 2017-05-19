package com.omit.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;

/**
 * Created by Alejandro on 5/5/17.
 */

@SolrDocument(solrCoreName = "comments")
public class SolrComment {
    @Id
    @Indexed(name = "id", type = "string")
    private String id;

    @Indexed(name = "owner", type = "long")
    private Long owner;

    @Indexed(name = "subject", type = "long")
    private Long subject;

    @Indexed(name = "teacher", type = "long")
    private Long teacher;

    @Indexed(name = "text", type = "string")
    private String text;

    @Indexed(name = "score", type = "int")
    private int score;

    @Indexed(name = "date", type = "date")
    private Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
