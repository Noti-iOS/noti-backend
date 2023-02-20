package com.noti.noti.teacher.adpater.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.noti.noti.config.QuerydslTestConfig;
import com.noti.noti.teacher.domain.Teacher;
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
@Import({TeacherPersistenceAdapter.class, TeacherMapper.class,
    TeacherQueryRepository.class, QuerydslTestConfig.class})
@DisplayName("TeacherPersistenceAdapterTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Slf4j
class TeacherPersistenceAdapterTest {

  @Autowired
  TeacherPersistenceAdapter teacherPersistenceAdapter;

  @Nested
  class saveTeacher_메서드는 {

    @Nested
    class Teacher_객체가_주어지면 {

      @Test
      void 성공적으로_객체를_저장하고_저장된_Teacher_객체를_반환한다() {
        Teacher teacher = createTeacher();

        Teacher savedTeacher = teacherPersistenceAdapter.saveTeacher(teacher);

        assertThat(savedTeacher.getId()).isNotNull();
      }
    }
  }

  Teacher createTeacher() {
    return Teacher.builder()
        .nickname("teacher")
        .build();
  }
}