package com.noti.noti.homework.adapter.out.persistence;

import static com.noti.noti.homework.adapter.out.persistence.jpa.model.QHomeworkJpaEntity.homeworkJpaEntity;
import static com.noti.noti.lesson.adapter.out.persistence.jpa.model.QLessonJpaEntity.lessonJpaEntity;
import static com.noti.noti.studenthomework.adapter.out.persistence.jpa.model.QStudentHomeworkJpaEntity.studentHomeworkJpaEntity;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import com.noti.noti.homework.adapter.in.web.dto.FrequencyOfLessonsDto;
import com.noti.noti.homework.application.port.out.TodayHomeworkCondition;
import com.noti.noti.homework.application.port.out.TodaysHomework;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.LocalDateType;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class HomeworkQueryRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 오늘의 숙제 목록과 숙제에 해당하는 학생목록을 조회하는 메서드.
   * @param condition 조건을 위한 파라미터.
   * @return 오늘의 숙제 목록과 숙제에 해당하는 학생목록.
   */
  public List<TodaysHomework> findTodayHomeworks(TodayHomeworkCondition condition) {
    return queryFactory
        .from(homeworkJpaEntity)
        .join(homeworkJpaEntity.lessonJpaEntity, lessonJpaEntity)
        .leftJoin(studentHomeworkJpaEntity)
        .on(studentHomeworkJpaEntity.homeworkJpaEntity.id.eq(homeworkJpaEntity.id))
        .where(eqTeacherId(condition.getTeacherId()),
            currentTimeOperation(condition.getCurrentTime()),
            eqDayOfWeek(condition.getCurrentTime().getDayOfWeek()))
        .orderBy(homeworkJpaEntity.endTime.asc())
        .transform(groupBy(homeworkJpaEntity).list(
            Projections.fields(TodaysHomework.class,
                homeworkJpaEntity.lessonJpaEntity.id.as("lessonId"),
                homeworkJpaEntity.id.as("homeworkId"),
                homeworkJpaEntity.homeworkName,
                homeworkJpaEntity.content,
                list(Projections.fields(TodaysHomework.HomeworkOfStudent.class,
                    studentHomeworkJpaEntity.studentJpaEntity.id.as("studentId"),
                    studentHomeworkJpaEntity.homeworkStatus).skipNulls()).as("students"))
        ));
  }

  private BooleanExpression eqTeacherId(Long teacherId) {
    log.info("teacher Id: {} ", teacherId);
    return teacherId != null ? lessonJpaEntity.teacherJpaEntity.id.eq(teacherId) : null;
  }

  private BooleanExpression currentTimeOperation(LocalDateTime now) {
    log.info("now: {}", now);
    return now != null ? Expressions.booleanOperation(Ops.BETWEEN, Expressions.constant(now),
        homeworkJpaEntity.startTime, homeworkJpaEntity.endTime) : null;
  }

  private BooleanExpression eqDayOfWeek(DayOfWeek dayOfWeek) {
    log.info("day: {}", dayOfWeek);
    return dayOfWeek != null ? lessonJpaEntity.days.contains(dayOfWeek.toString()) : null;
  }


  /**
   * 숙제가 있는 날과 그 날의 분반 개수를 조회하는 메서드
   * @param start 시작 날짜
   * @param end 끝 날짜
   * @return 시작 ~ 끝 날짜에서 숙제가 있는 날의 날짜와 그 날짜의 분반 개수목록
   */
  public List<FrequencyOfLessonsDto> findFrequencyOfLesson(LocalDateTime start, LocalDateTime end) {

    DateTemplate<String> formattedEndTime = Expressions.dateTemplate(
        String.class
        , "Date_Format({0}, {1})"
        , homeworkJpaEntity.endTime
        , ConstantImpl.create("%Y-%m-%d")
    );


    return queryFactory
        .select(Projections.fields(FrequencyOfLessonsDto.class,
            formattedEndTime, lessonJpaEntity.countDistinct()))
        .from(homeworkJpaEntity)
        .join(homeworkJpaEntity).on(homeworkJpaEntity.lessonJpaEntity.id.eq(lessonJpaEntity.id))
        .where(homeworkJpaEntity.endTime.in(start, end.plusDays(1)))
        .transform(groupBy(formattedEndTime)
            .list(
                Projections.fields(FrequencyOfLessonsDto.class,
                    formattedEndTime,
                    lessonJpaEntity.countDistinct())
            ));

  }

}
