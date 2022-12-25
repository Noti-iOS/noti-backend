package com.noti.noti.lesson.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.noti.noti.common.adapter.out.persistance.DaySetConvertor;
import com.noti.noti.lesson.adapter.out.persistence.jpa.model.LessonJpaEntity;
import com.noti.noti.lesson.domain.model.Lesson;
import com.noti.noti.teacher.adpater.out.persistence.TeacherMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("LessonMapper 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
class LessonMapperTest {

  private LessonMapper lessonMapper = new LessonMapper(new TeacherMapper(), new DaySetConvertor());

  @Nested
  class mapToDomainEntity_메소드는 {

    final LessonJpaEntity givenLessonJpaEntity =
        LessonJpaEntity.builder()
            .id(1L)
            .lessonName("lesson")
            .build();

    @Nested
    class Lesson_Jpa_Entity_객체가_주어지면 {

      @Test
      void Lesson_Domain_객체로_변환하고_변환된_객체를_반환한다() {
        Lesson mappedEntity = lessonMapper.mapToDomainEntity(givenLessonJpaEntity);
        assertThat(mappedEntity.getId()).isEqualTo(givenLessonJpaEntity.getId());
      }
    }
  }

  @Nested
  class mapToJpaEntity_메소드는 {

    @Nested
    class Lesson_Domain_객체가_주어지면 {

      final Lesson givenLesson =
          Lesson.builder()
              .id(1L)
              .lessonName("Lesson")
              .build();

      @Test
      void Lesson_Jpa_Entity_객체로_변환하고_변환된_객체를_반환한다() {
        LessonJpaEntity mappedLessonJpaEntity = lessonMapper.mapToJpaEntity(givenLesson);
        assertThat(mappedLessonJpaEntity.getId()).isEqualTo(givenLesson.getId());
      }
    }
  }


}