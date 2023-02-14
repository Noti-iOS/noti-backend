package com.noti.noti.book.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.noti.noti.book.application.port.in.AddBookCommand;
import com.noti.noti.book.application.port.out.FindBookCondition;
import com.noti.noti.book.application.port.out.FindBookPort;
import com.noti.noti.book.application.port.out.SaveBookPort;
import com.noti.noti.book.domain.model.Book;
import com.noti.noti.book.exception.DuplicatedTitleBookException;
import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
import com.noti.noti.teacher.application.port.out.FindTeacherPort;
import com.noti.noti.teacher.domain.Teacher;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class AddBookServiceTest {

  @InjectMocks
  AddBookService addBookService;

  @Mock
  FindTeacherPort findTeacherPort;

  @Mock
  FindBookPort findBookPort;

  @Mock
  SaveBookPort saveBookPort;

  final Long TEACHER_ID = 1L;
  final Long BOOK_ID = 1L;
  final String TITLE = "수학의정석";

  @Nested
  class apply_메서드는 {

    @Nested
    class 중복된_교재명이_존재하면 {

      @Test
      void DuplicatedTitleBookException_예외가_발생한다() {
        when(findTeacherPort.findById(TEACHER_ID)).thenReturn(
            Optional.of(createTeacher(TEACHER_ID)));
        when(findBookPort.isExistedBook(any())).thenReturn(true);

        assertAll(
            () -> assertThatThrownBy(
                () -> addBookService.apply(new AddBookCommand(TEACHER_ID, TITLE))).isInstanceOf(
                DuplicatedTitleBookException.class),
            () -> verify(saveBookPort, never()).saveBook(any(Book.class))
        );
      }
    }

    @Nested
    class 요청에_해당하는_선생님이_존재하지_않으면 {

      @Test
      void TeacherNotFoundException_예외가_발생한다() {
        when(findTeacherPort.findById(TEACHER_ID)).thenReturn(Optional.empty());

        assertAll(
            () -> assertThatThrownBy(
                () -> addBookService.apply(new AddBookCommand(TEACHER_ID, TITLE))).isInstanceOf(
                TeacherNotFoundException.class),
            () -> verify(saveBookPort, never()).saveBook(any(Book.class)),
            () -> verify(findBookPort, never()).isExistedBook(any())
        );
      }
    }

    @Nested
    class 유효한_Command가_주어지면 {

      @Test
      void 성공적으로_정보를_저장하고_Book_객체를_반환한다() {
        when(findTeacherPort.findById(TEACHER_ID)).thenReturn(
            Optional.of(createTeacher(TEACHER_ID)));
        when(findBookPort.isExistedBook(any(FindBookCondition.class))).thenReturn(false);
        when(saveBookPort.saveBook(any(Book.class))).thenReturn(createBook(BOOK_ID, TITLE));

        Book savedBook = addBookService.apply(new AddBookCommand(TEACHER_ID, TITLE));

        assertAll(
            () -> assertThat(savedBook.getId()).isEqualTo(BOOK_ID),
            () -> verify(saveBookPort, times(1)).saveBook(any(Book.class)),
            () -> verify(findBookPort, times(1)).isExistedBook(any(FindBookCondition.class)),
            () -> verify(saveBookPort, times(1)).saveBook(any(Book.class))
        );
      }
    }
  }

  Teacher createTeacher(Long id) {
    return Teacher.builder().id(id).build();
  }

  Book createBook(Long id, String title) {
    return Book.builder().id(id).title(title).build();
  }
}