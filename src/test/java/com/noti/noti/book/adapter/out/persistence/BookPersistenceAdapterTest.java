package com.noti.noti.book.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.noti.noti.book.domain.model.Book;
import com.noti.noti.config.QuerydslTestConfig;
import com.noti.noti.teacher.adpater.out.persistence.TeacherMapper;
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
    QuerydslTestConfig.class})
@DisplayName("BookPersistenceAdapterTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
class BookPersistenceAdapterTest {

  @Autowired
  BookPersistenceAdapter bookPersistenceAdapter;

  @Nested
  class FindBookById_메서드는 {

    @Sql("/data/book.sql")
    @Nested
    class 유효한_ID_값이_주어지면 {

      final Long ID = 1L;

      @Test
      void ID에_해당하는_Optional_Book_객체를_반환한다 (){
        Optional<Book> book = bookPersistenceAdapter.findBookById(ID);

        assertThat(book)
            .isPresent()
            .hasValueSatisfying(existBook -> assertThat(existBook.getId()).isEqualTo(ID));
      }
    }

    @Nested
    class 유효하지_않는_ID_값이_주어지면 {

      @Test
      void 비어있는_Optional_객체를_반환한다 (){
        Optional<Book> book = bookPersistenceAdapter.findBookById(1L);

        assertThat(book).isNotPresent();
      }
    }
  }
}