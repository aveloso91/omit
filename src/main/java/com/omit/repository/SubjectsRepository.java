package com.omit.repository;

import com.omit.domain.Subjects;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Subjects entity.
 */
@SuppressWarnings("unused")
public interface SubjectsRepository extends JpaRepository<Subjects,Long> {

}
