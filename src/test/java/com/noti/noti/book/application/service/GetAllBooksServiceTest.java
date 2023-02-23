package com.noti.noti.book.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.noti.noti.book.application.port.out.BookDto;
import com.noti.noti.book.application.port.out.FindBookPort;
import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
import com.noti.noti.teacher.application.port.out.FindTeacherPort;
import java.util.List;
import java.util.Optional;
import net.jqwik.api.Arbitraries;
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
class GetAllBooksServiceTest {

  @InjectMocks
  GetAllBooksService getAllBooksService;
  @Mock
  FindTeacherPort findTeacherPort;
  @Mock
  FindBookPort findBookPort;


  @Nested
  class _메서드는 {

    @Nested
    class 선생님_ID에_해당하는_교재가_있으면 {

      @Test
      void 해당_BookDto_List를_반환한다() {
        List<BookDto> givenBookDtos = MonkeyUtils.MONKEY.giveMeBuilder(BookDto.class)
            .set("id", Arbitraries.longs().greaterOrEqual(1L))
            .sampleList(5);

        when(findTeacherPort.findById(anyLong())).thenReturn(
            Optional.of(MonkeyUtils.TEACHER_FIXTURE));
        when(findBookPort.findBooksByTeacherId(anyLong())).thenReturn(givenBookDtos);

        List<BookDto> books = getAllBooksService.getAllBooks(1L);

        assertThat(books.size()).isEqualTo(5);
      }
    }

    @Nested
    class 선생님_ID에_해당하는_교재가_없으면 {

      @Test
      void 비어있는_List을_반환한다() {
        when(findTeacherPort.findById(anyLong())).thenReturn(
            Optional.of(MonkeyUtils.TEACHER_FIXTURE));
        when(findBookPort.findBooksByTeacherId(anyLong())).thenReturn(List.of());

        List<BookDto> books = getAllBooksService.getAllBooks(1L);

        assertThat(books).isEmpty();
      }
    }

    @Nested
    class ID에_해당하는_선생님이_존재하지_않으면 {

      @Test
      void TeacherNotFoundException_예외가_발생한다() {
        when(findTeacherPort.findById(anyLong())).thenReturn(Optional.empty());

        assertAll(
            () -> assertThatThrownBy(() -> getAllBooksService.getAllBooks(1L))
                .isInstanceOf(TeacherNotFoundException.class),
            () -> verify(findBookPort, never()).findBooksByTeacherId(anyLong())
        );
      }
    }
  }
}