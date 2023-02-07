package com.noti.noti.lessonbook.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.noti.noti.book.adapter.out.persistence.BookMapper;
import com.noti.noti.book.domain.model.Book;
import com.noti.noti.common.adapter.out.persistance.DaySetConvertor;
import com.noti.noti.config.QuerydslTestConfig;
import com.noti.noti.lesson.adapter.out.persistence.LessonMapper;
import com.noti.noti.lesson.domain.model.Lesson;
import com.noti.noti.lessonbook.domain.model.LessonBook;
import com.noti.noti.teacher.adpater.out.persistence.TeacherMapper;
import com.noti.noti.teacher.domain.Teacher;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@ActiveProfiles("test")
@Import({LessonBookPersistenceAdapter.class, LessonBookMapper.class, BookMapper.class,
    LessonMapper.class, TeacherMapper.class, QuerydslTestConfig.class, DaySetConvertor.class})
@DisplayName("LessonBookPersistenceAdapterTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
class LessonBookPersistenceAdapterTest {

  @Autowired
  LessonBookPersistenceAdapter lessonBookPersistenceAdapter;

  LessonBook createLessonBook() {
    return LessonBook.builder()
        .lesson(Lesson.builder().id(1L).teacher(Teacher.builder().id(1L).build()).build())
        .book(Book.builder().id(1L).teacher(Teacher.builder().id(1L).build()).build())
        .build();
  }

  List<LessonBook> createLessonList() {
    return List.of(
        LessonBook.builder()
            .lesson(Lesson.builder().id(1L).teacher(Teacher.builder().id(1L).build()).build())
            .book(Book.builder().id(1L).teacher(Teacher.builder().id(1L).build()).build())
            .build(),
        LessonBook.builder()
            .lesson(Lesson.builder().id(1L).teacher(Teacher.builder().id(1L).build()).build())
            .book(Book.builder().id(2L).teacher(Teacher.builder().id(1L).build()).build())
            .build(),
        LessonBook.builder()
            .lesson(Lesson.builder().id(1L).teacher(Teacher.builder().id(1L).build()).build())
            .book(Book.builder().id(3L).teacher(Teacher.builder().id(1L).build()).build())
            .build()
    );
  }

  @Sql("/data/lesson-book.sql")
  @Nested
  class saveLessonBook_메서드는 {

    @Nested
    class 유효한_LessonBook_객체가_주어지면 {

      @Test
      void 성공적으로_해당_객체를_저장하고_저장된_LessonBook_객체를_반환한다() {
        LessonBook savedLessonBook = lessonBookPersistenceAdapter.saveLessonBook(
            createLessonBook());

        assertThat(savedLessonBook.getBook().getId()).isEqualTo(1L);
      }
    }
  }

  @Sql("/data/lesson-book.sql")
  @Nested
  class saveAllLessonBooks_메서드는 {

    @Nested
    class 유효한_LessonBook_리스트가_주어지면 {

      @Test
      void 성공적으로_해당_객체를_저장하고_저장된_LessonBook_리스트를_반환한다() {
        List<LessonBook> savedLessonBookList = lessonBookPersistenceAdapter.saveAllLessonBooks(
            createLessonList());

        assertThat(savedLessonBookList).hasSize(3);
      }
    }
  }

}