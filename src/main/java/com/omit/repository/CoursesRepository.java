package com.omit.repository;

import com.omit.domain.Courses;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Courses entity.
 */
@SuppressWarnings("unused")
public interface CoursesRepository extends JpaRepository<Courses,Long> {

}
