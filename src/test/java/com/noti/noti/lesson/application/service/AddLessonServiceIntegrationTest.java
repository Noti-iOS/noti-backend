package com.noti.noti.lesson.application.service;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.THURSDAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.noti.noti.book.exception.BookNotFoundException;
import com.noti.noti.lesson.adapter.out.persistence.LessonPersistenceAdapter;
import com.noti.noti.lesson.application.port.in.AddLessonCommand;
import com.noti.noti.lesson.application.port.in.AddLessonUsecase;
import com.noti.noti.lesson.domain.model.Lesson;
import com.noti.noti.student.exception.StudentNotFoundException;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@DisplayName("AddServiceIntegrationTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest
public class AddLessonServiceIntegrationTest {

  @Autowired
  AddLessonUsecase addLessonUsecase;

  @Autowired
  LessonPersistenceAdapter lessonPersistenceAdapter;

  final String LESSON_NAME = "MATH";

  AddLessonCommand createCommand() {
    return new AddLessonCommand(1L, LESSON_NAME, Set.of(MONDAY, THURSDAY),
        LocalTime.now(), LocalTime.now(), List.of(1L, 2L), List.of(1L,2L));
  }

  @Nested
  @Transactional
  class 교재_ID가_유효하지_않으면 {

    @Sql("/data/teacher.sql")
    @Test
    void lesson을_저장하지_않고_BookNotFoundException_예외가_발생한다(){
      assertThatThrownBy(() -> addLessonUsecase.apply(createCommand())).isInstanceOf(
          BookNotFoundException.class);

      Optional<Lesson> lesson = lessonPersistenceAdapter.findById(1L);

      assertThat(lesson).isNotPresent();
    }
  }

  @Nested
  @Transactional
  class 학생_ID가_유효하지_않으면 {

    @Sql("/data/book.sql")
    @Test
    void lesson을_저장하지_않고_StudentNotFoundException_예외가_발생한다(){

      assertThatThrownBy(() -> addLessonUsecase.apply(createCommand())).isInstanceOf(
          StudentNotFoundException.class);

      Optional<Lesson> lessonJpaEntity = lessonPersistenceAdapter.findById(1L);

      assertThat(lessonJpaEntity).isNotPresent();
    }
  }
}
