package com.noti.noti.book.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.noti.noti.book.application.port.out.FindBookCondition;
import com.noti.noti.book.domain.model.Book;
import com.noti.noti.config.QuerydslTestConfig;
import com.noti.noti.teacher.adpater.out.persistence.TeacherMapper;
import com.noti.noti.teacher.domain.Teacher;
import java.util.Optional;
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
@Import({BookPersistenceAdapter.class, BookMapper.class, TeacherMapper.class,
    QuerydslTestConfig.class, BookQueryRepository.class})
@DisplayName("BookPersistenceAdapterTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
class BookPersistenceAdapterTest {

  @Autowired
  BookPersistenceAdapter bookPersistenceAdapter;

  @Nested
  class FindBookById_메서드는 {

    final Long ID = 1L;

    @Sql("/data/book.sql")
    @Nested
    class 유효한_ID_값이_주어지면 {

      @Test
      void ID에_해당하는_Optional_Book_객체를_반환한다() {
        Optional<Book> book = bookPersistenceAdapter.findBookById(ID);

        assertThat(book).isPresent()
            .hasValueSatisfying(existBook -> assertThat(existBook.getId()).isEqualTo(ID));
      }
    }

    @Nested
    class 유효하지_않는_ID_값이_주어지면 {

      @Test
      void 비어있는_Optional_객체를_반환한다() {
        Optional<Book> book = bookPersistenceAdapter.findBookById(1L);

        assertThat(book).isNotPresent();
      }
    }
  }

  @Nested
  class saveBook_메서드는 {

    @Nested
    class 유효한_Book_객체가_주어지면 {

      @Test
      void 성공적으로_객체를_저장하고_저장된_엔티티를_도메인_객체로_변환해_반환한다() {
        Book book = bookPersistenceAdapter.saveBook(createBook());

        assertThat(book.getId()).isNotNull();
      }
    }
  }

  @Sql("/data/book.sql")
  @Nested
  class isExistedBook_메서드는 {

    @Nested
    class 조건에_해당하는_Book이_존재하면 {

      @Test
      void true를_반환한다() {
        boolean result = bookPersistenceAdapter.isExistedBook(createCondition("수학의정석", 1L));

        assertThat(result).isTrue();
      }
    }

    @Nested
    class 조건에_해당하는_Book이_존재하지_않으면 {

      @Test
      void false를_반환한다() {
        boolean result = bookPersistenceAdapter.isExistedBook(createCondition("수학의정석", 2L));

        assertThat(result).isFalse();
      }
    }
  }

  Book createBook() {
    return Book.builder().title("수학의 정석3").teacher(createTeacher()).build();
  }

  FindBookCondition createCondition(String title, Long teacherId) {
    return new FindBookCondition(title, teacherId);
  }

  Teacher createTeacher() {
    return Teacher.builder().id(1L).build();
  }
}