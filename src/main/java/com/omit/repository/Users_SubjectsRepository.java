package com.omit.repository;

import com.omit.domain.Users_Subjects;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Users_Subjects entity.
 */
@SuppressWarnings("unused")
public interface Users_SubjectsRepository extends JpaRepository<Users_Subjects,Long> {

    @Query("select users_Subjects from Users_Subjects users_Subjects where users_Subjects.user.login = ?#{principal.username}")
    List<Users_Subjects> findByUserIsCurrentUser();

}
