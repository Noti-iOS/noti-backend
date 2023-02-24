package com.noti.noti.lesson.application.service;

import static com.noti.noti.common.MonkeyUtils.TEACHER_FIXTURE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.lesson.application.port.out.FindLessonPort;
import com.noti.noti.lesson.application.port.out.LessonDto;
import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
import com.noti.noti.teacher.application.port.out.FindTeacherPort;
import java.util.List;
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
class GetAllLessonsServiceTest {

  @InjectMocks
  GetAllLessonsService getAllLessonsService;

  @Mock
  FindTeacherPort findTeacherPort;

  @Mock
  FindLessonPort findLessonPort;

  @Nested
  class getAllLessons_메서드는 {

    @Nested
    class ID에_해당하는_선생님이_존재하지_않으면 {

      @Test
      void TeacherNotFoundException_예외가_발생한다() {
        when(findTeacherPort.findById(anyLong())).thenThrow(new TeacherNotFoundException());

        assertAll(
            () -> assertThatThrownBy(
                () -> getAllLessonsService.getAllLessons(anyLong()))
                .isInstanceOf(TeacherNotFoundException.class),
            () -> verify(findLessonPort, never()).findAllLessonsByTeacherId(anyLong())

        );
      }
    }

    @Nested
    class 선생님_ID에_해당하는_교재가_존재하지_않으면 {

      @Test
      void 비어있는_리스트를_반환한다() {
        when(findTeacherPort.findById(anyLong())).thenReturn(Optional.of(TEACHER_FIXTURE));
        when(findLessonPort.findAllLessonsByTeacherId(anyLong())).thenReturn(List.of());

        List<LessonDto> allLessons = getAllLessonsService.getAllLessons(anyLong());

        assertThat(allLessons).isEmpty();
      }
    }

    @Nested
    class 선생님_ID에_해당하는_교재가_존재하면 {

      @Test
      void 해당하는_BookDto_리스트를_반환한다() {
        List<LessonDto> givenLessonDtos = MonkeyUtils.MONKEY.giveMeBuilder(LessonDto.class)
            .set("id", 1L)
            .setNotNull("lessonName")
            .sampleList(5);

        when(findTeacherPort.findById(anyLong())).thenReturn(Optional.of(TEACHER_FIXTURE));
        when(findLessonPort.findAllLessonsByTeacherId(anyLong())).thenReturn(givenLessonDtos);

        List<LessonDto> allLessons = getAllLessonsService.getAllLessons(anyLong());

        assertThat(allLessons).hasSize(5);
      }
    }
  }
}