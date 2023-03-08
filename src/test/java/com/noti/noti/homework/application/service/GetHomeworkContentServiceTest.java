package com.noti.noti.homework.application.service;

import static com.noti.noti.common.MonkeyUtils.MONKEY;
import static net.jqwik.api.Arbitraries.integers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.noti.noti.homework.application.port.in.HomeworkContentCommand;
import com.noti.noti.homework.application.port.in.InHomeworkContent;
import com.noti.noti.homework.application.port.out.FindHomeworkContentPort;
import com.noti.noti.homework.application.port.out.OutHomeworkContent;
import com.noti.noti.lesson.application.port.out.CheckLessonExistencePort;
import com.noti.noti.lesson.exception.LessonNotFoundException;
import java.time.LocalDateTime;
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


@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("GetHomeworkContentServiceTest 클래스")
class GetHomeworkContentServiceTest {

  @InjectMocks
  GetHomeworkContentService getHomeworkContentService;

  @Mock
  FindHomeworkContentPort findHomeworkContentPort;

  @Mock
  CheckLessonExistencePort checkLessonExistencePort;

  @Nested
  class getHomeworkContents_메서드는 {

    @Nested
    class 모든_조건을_충족할_때 {

      @Test
      void Out_리스트와_같은_내용의_In_리스트_반환() {
        //given
        HomeworkContentCommand command = MONKEY.giveMeBuilder(HomeworkContentCommand.class)
            .setNotNull("lessonId")
            .setNotNull("date")
            .sample();
        List<OutHomeworkContent> outList = createListRandomSizeBetween(1, 10);

        when(checkLessonExistencePort.existsById(anyLong())).thenReturn(true);
        when(findHomeworkContentPort.findHomeworkContents(anyLong(), any(LocalDateTime.class)))
            .thenReturn(outList);

        //when
        List<InHomeworkContent> inList = getHomeworkContentService.getHomeworkContents(command);

        //then
        assertThat(inList).usingRecursiveComparison().isEqualTo(outList);
      }

      @Test
      void 비어있는_리스트_반환() {
        //given
        HomeworkContentCommand command = MONKEY.giveMeBuilder(HomeworkContentCommand.class)
            .setNotNull("lessonId")
            .setNotNull("date")
            .sample();
        List<OutHomeworkContent> outList = createListRandomSizeBetween(0, 0);

        when(checkLessonExistencePort.existsById(anyLong())).thenReturn(true);
        when(findHomeworkContentPort.findHomeworkContents(anyLong(), any(LocalDateTime.class)))
            .thenReturn(outList);

        //when
        List<InHomeworkContent> inList = getHomeworkContentService.getHomeworkContents(command);

        //then
        assertThat(inList).isEmpty();
      }
    }

    @Nested
    class 조건을_충족하지_않을_때 {
      @Test
      void lessonId에_해당하는_수업이_없다면_예외발생() {
        //given
        HomeworkContentCommand command = MONKEY.giveMeBuilder(HomeworkContentCommand.class)
            .setNotNull("lessonId")
            .setNotNull("date")
            .sample();

        when(checkLessonExistencePort.existsById(anyLong())).thenReturn(false);

        //when
        //then
        assertThatThrownBy(
            () -> getHomeworkContentService.getHomeworkContents(command)
        ).isInstanceOf(LessonNotFoundException.class);
      }
    }
  }

  private List<OutHomeworkContent> createListRandomSizeBetween(int min, int max) {
    Integer size = integers().between(min, max).sample();
    return MONKEY.giveMeBuilder(OutHomeworkContent.class).sampleList(size);
  }


}