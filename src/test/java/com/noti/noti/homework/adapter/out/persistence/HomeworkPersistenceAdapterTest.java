package com.noti.noti.homework.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.noti.noti.book.adapter.out.persistence.BookMapper;
import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.common.adapter.out.persistance.DaySetConvertor;
import com.noti.noti.config.QuerydslTestConfig;
import com.noti.noti.homework.application.port.out.SearchedHomework;
import com.noti.noti.homework.application.port.out.TodayHomeworkCondition;
import com.noti.noti.homework.application.port.out.TodaysHomework;
import com.noti.noti.lesson.adapter.out.persistence.LessonMapper;
import com.noti.noti.teacher.adpater.out.persistence.TeacherMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@ActiveProfiles("test")
@Import({HomeworkPersistenceAdapter.class, HomeworkMapper.class, LessonMapper.class,
    BookMapper.class, TeacherMapper.class, DaySetConvertor.class, HomeworkQueryRepository.class,
    QuerydslTestConfig.class})
@DisplayName("HomeworkPersistenceAdapterTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Slf4j
class HomeworkPersistenceAdapterTest {

  @Autowired
  HomeworkPersistenceAdapter homeworkPersistenceAdapter;

  @InjectMocks
  HomeworkPersistenceAdapter homeworkPersistenceAdapterMock;

  @Mock
  HomeworkQueryRepository homeworkQueryRepositoryMockBean;


  @Sql("/data/homework.sql")
  @Nested
  class findTodaysHomeworks_메소드는 {

    @Nested
    class 수업이_없는_선생님_ID가_주어지면 {

      final TodayHomeworkCondition condition = new TodayHomeworkCondition(3L, LocalDateTime.now());
      @Test
      void 비어있는_List를_반환한다(){
        List<TodaysHomework> todaysHomeworks = homeworkPersistenceAdapter.findTodaysHomeworks(
            condition);

        assertThat(todaysHomeworks).isEmpty();
      }
    }

    @Nested
    class 오늘의_요일에_해당하는_숙제가_없으면 {

      final TodayHomeworkCondition condition = new TodayHomeworkCondition(2L,
          LocalDateTime.of(2022, Month.DECEMBER, 31, 12, 00));
      @Test
      void 비어있는_List를_반환한다(){
        List<TodaysHomework> todaysHomeworks = homeworkPersistenceAdapter.findTodaysHomeworks(
            condition);

        assertThat(todaysHomeworks).isEmpty();
      }
    }

    @Nested
    class 모든_숙제가_마감되었다면 {

      final TodayHomeworkCondition condition = new TodayHomeworkCondition(1L,
          LocalDateTime.of(2023, Month.JANUARY, 2, 12, 00));
      @Test
      void 비어있는_List를_반환한다(){
        List<TodaysHomework> todaysHomeworks = homeworkPersistenceAdapter.findTodaysHomeworks(
            condition);

        assertThat(todaysHomeworks).isEmpty();
      }
    }

    @Nested
    class 모든_조건이_유효하면 {

      final TodayHomeworkCondition condition = new TodayHomeworkCondition(1L,
          LocalDateTime.now());
      @Test
      void 숙제목록과_숙제에_해당하는_학생목록_List를_반환한다(){
        List<TodaysHomework> todaysHomeworks = homeworkPersistenceAdapter.findTodaysHomeworks(
            condition);

        assertThat(todaysHomeworks).isNotEmpty();
      }
    }
  }


  @Nested
  class findSearchedHomeworks_메소드는 {
    @Nested
    class 빈리스트가_오면 {

      @Test
      void 빈리스트_반환() {
        Mockito.when(homeworkQueryRepositoryMockBean.findSearchedHomework(Mockito.anyLong(), Mockito.anyString(), Mockito.eq(0), Mockito.anyString()))
            .thenReturn(List.of());
        List<SearchedHomework> searchedHomeworkList = homeworkPersistenceAdapterMock.findSearchedHomeworks(1L, "math",
            0, "1");

        assertThat(searchedHomeworkList).isEmpty();
      }

    }

    @Nested
    class 비어있지_않은_리스트가_오면 {
      @Test
      void SearchedHomework_리스트_반환() {
        Mockito.when(homeworkQueryRepositoryMockBean.findSearchedHomework(Mockito.anyLong(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyString()))
            .thenReturn(MonkeyUtils.MONKEY.giveMeBuilder(SearchedHomework.class).sampleList(3));
        List<SearchedHomework> searchedHomeworkList = homeworkPersistenceAdapterMock.findSearchedHomeworks(Mockito.anyLong(), Mockito.anyString(),
            Mockito.anyInt(), Mockito.anyString());
        assertThat(searchedHomeworkList).isNotEmpty();
      }

    }
  }


}