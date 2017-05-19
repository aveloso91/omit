package com.omit.domain;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * Created by Alejandro on 5/5/17.
 */
public class SolrComment {
    @Field
    private String id;

    @Field
    private Long owner;

    @Field
    private Long subject;

    @Field
    private Long teacher;

    @Field
    private String text;

    @Field
    private int score;

    @Field
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
