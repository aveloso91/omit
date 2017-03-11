package com.omit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Subjects.
 */
@Entity
@Table(name = "subjects")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Subjects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Courses course;

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Users_Subjects> users_subjects = new HashSet<>();

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Questions> questions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Subjects name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Courses getCourse() {
        return course;
    }

    public Subjects course(Courses courses) {
        this.course = courses;
        return this;
    }

    public void setCourse(Courses courses) {
        this.course = courses;
    }

    public Set<Users_Subjects> getUsers_subjects() {
        return users_subjects;
    }

    public Subjects users_subjects(Set<Users_Subjects> users_Subjects) {
        this.users_subjects = users_Subjects;
        return this;
    }

    public Subjects addUsers_subjects(Users_Subjects users_Subjects) {
        this.users_subjects.add(users_Subjects);
        users_Subjects.setSubject(this);
        return this;
    }

    public Subjects removeUsers_subjects(Users_Subjects users_Subjects) {
        this.users_subjects.remove(users_Subjects);
        users_Subjects.setSubject(null);
        return this;
    }

    public void setUsers_subjects(Set<Users_Subjects> users_Subjects) {
        this.users_subjects = users_Subjects;
    }

    public Set<Questions> getQuestions() {
        return questions;
    }

    public Subjects questions(Set<Questions> questions) {
        this.questions = questions;
        return this;
    }

    public Subjects addQuestions(Questions questions) {
        this.questions.add(questions);
        questions.setSubject(this);
        return this;
    }

    public Subjects removeQuestions(Questions questions) {
        this.questions.remove(questions);
        questions.setSubject(null);
        return this;
    }

    public void setQuestions(Set<Questions> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Subjects subjects = (Subjects) o;
        if (subjects.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, subjects.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Subjects{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
