package com.omit.repository;

import com.omit.domain.Questions_Default;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Questions_Default entity.
 */
@SuppressWarnings("unused")
public interface Questions_DefaultRepository extends JpaRepository<Questions_Default,Long> {

}
