package com.noti.noti.lesson.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.noti.noti.common.domain.model.Day;
import com.noti.noti.lesson.domain.model.Lesson;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import({LessonPersistenceAdapter.class, LessonMapper.class})
@DisplayName("LessonPersistenceAdapterTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Slf4j
class LessonPersistenceAdapterTest {

  @Autowired
  LessonPersistenceAdapter lessonPersistenceAdapter;

  @Nested
  class save_메소드는 {

    @Nested
    class Lesson_도메인_객체가_주어지면 {

      final Lesson givenLesson =
          Lesson.builder()
              .lessonName("Lesson")
              .days(Set.of(Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY))
              .build();

      @Test
      void 주어진_객체를_저장하고_저장된_mapping된_객체를_반환한다() {
        Lesson savedLesson = lessonPersistenceAdapter.saveLesson(givenLesson);
        log.info("days: {}",savedLesson.getDays());
        assertThat(savedLesson.getId()).isEqualTo(1L);
      }
    }
  }
}