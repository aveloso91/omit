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
 * A Degrees.
 */
@Entity
@Table(name = "degrees")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Degrees implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "degree")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Courses> courses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Degrees name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Courses> getCourses() {
        return courses;
    }

    public Degrees courses(Set<Courses> courses) {
        this.courses = courses;
        return this;
    }

    public Degrees addCourses(Courses courses) {
        this.courses.add(courses);
        courses.setDegree(this);
        return this;
    }

    public Degrees removeCourses(Courses courses) {
        this.courses.remove(courses);
        courses.setDegree(null);
        return this;
    }

    public void setCourses(Set<Courses> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Degrees degrees = (Degrees) o;
        if (degrees.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, degrees.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Degrees{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
