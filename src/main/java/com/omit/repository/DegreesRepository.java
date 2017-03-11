package com.omit.repository;

import com.omit.domain.Degrees;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Degrees entity.
 */
@SuppressWarnings("unused")
public interface DegreesRepository extends JpaRepository<Degrees,Long> {

}
