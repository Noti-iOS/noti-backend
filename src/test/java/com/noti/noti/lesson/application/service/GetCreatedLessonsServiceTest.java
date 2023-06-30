package com.noti.noti.lesson.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.noti.noti.lesson.application.port.in.CreatedLessonCommand;
import com.noti.noti.lesson.application.port.in.InCreatedLesson;
import com.noti.noti.lesson.application.port.out.FindCreatedLessonsPort;
import com.noti.noti.lesson.application.port.out.OutCreatedLesson;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("GetCreatedLessonsServiceTest")
@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class GetCreatedLessonsServiceTest {

  @InjectMocks
  GetCreatedLessonsService getCreatedLessonsService;

  @Mock
  FindCreatedLessonsPort findCreatedLessonsPort;

  private final Long TEACHER_ID = 1L;
  private final CreatedLessonCommand command = new CreatedLessonCommand(TEACHER_ID);

  private List<OutCreatedLesson> createLessons() {
    OutCreatedLesson lesson1 = new OutCreatedLesson(1L, "lesson1");
    OutCreatedLesson lesson2 = new OutCreatedLesson(2L, "lesson2");
    OutCreatedLesson lesson3 = new OutCreatedLesson(3L, "lesson3");
    return List.of(lesson1, lesson2, lesson3);
  }

  private List<OutCreatedLesson> createEmptyLessonList() {
    return List.of();
  }

  @Nested
  class createdLessons_메소드는 {

    @Test
    void 선생님Id를_전달하면_InCreatedLessonList_반환() {
      when(findCreatedLessonsPort.findCreatedLessons(TEACHER_ID)).thenReturn(createLessons());
      List<InCreatedLesson> inCreatedLessons = getCreatedLessonsService.createdLessons(command);
      assertThat(inCreatedLessons.size()).isEqualTo(3);
    }

    @Test
    void 선생님이_생성한_분반이_없으면_빈리스트_반환() {
      when(findCreatedLessonsPort.findCreatedLessons(TEACHER_ID))
          .thenReturn(createEmptyLessonList());
      List<InCreatedLesson> inCreatedLessons = getCreatedLessonsService.createdLessons(command);
      assertThat(inCreatedLessons).isEmpty();

    }
  }




}