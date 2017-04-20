package com.omit.repository;

import com.omit.domain.Questions;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Questions entity.
 */
@SuppressWarnings("unused")
public interface QuestionsRepository extends JpaRepository<Questions,Long> {

    @Query("select questions from Questions questions where questions.user.login = ?#{principal.username}")
    List<Questions> findByUserIsCurrentUser();

    @Query("select questions from Questions questions where questions.user.id = ?1 and questions.subject.id = ?2")
    List<Questions> findQuestionsBySubjectTeacher(Long var1, Long var2);
}
