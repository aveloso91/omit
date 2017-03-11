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
 * A Courses.
 */
@Entity
@Table(name = "courses")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Courses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Degrees degree;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Subjects> subjects = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Courses name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Degrees getDegree() {
        return degree;
    }

    public Courses degree(Degrees degrees) {
        this.degree = degrees;
        return this;
    }

    public void setDegree(Degrees degrees) {
        this.degree = degrees;
    }

    public Set<Subjects> getSubjects() {
        return subjects;
    }

    public Courses subjects(Set<Subjects> subjects) {
        this.subjects = subjects;
        return this;
    }

    public Courses addSubjects(Subjects subjects) {
        this.subjects.add(subjects);
        subjects.setCourse(this);
        return this;
    }

    public Courses removeSubjects(Subjects subjects) {
        this.subjects.remove(subjects);
        subjects.setCourse(null);
        return this;
    }

    public void setSubjects(Set<Subjects> subjects) {
        this.subjects = subjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Courses courses = (Courses) o;
        if (courses.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, courses.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Courses{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
