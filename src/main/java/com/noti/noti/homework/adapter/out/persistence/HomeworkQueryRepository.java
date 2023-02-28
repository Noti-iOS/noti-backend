package com.noti.noti.homework.adapter.out.persistence;

import static com.noti.noti.homework.adapter.out.persistence.jpa.model.QHomeworkJpaEntity.homeworkJpaEntity;
import static com.noti.noti.lesson.adapter.out.persistence.jpa.model.QLessonJpaEntity.lessonJpaEntity;
import static com.noti.noti.studenthomework.adapter.out.persistence.jpa.model.QStudentHomeworkJpaEntity.studentHomeworkJpaEntity;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import com.noti.noti.homework.application.port.out.OutFilteredHomeworkFrequency;
import com.noti.noti.homework.application.port.out.TodayHomeworkCondition;
import com.noti.noti.homework.application.port.out.TodaysHomework;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class HomeworkQueryRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 오늘의 숙제 목록과 숙제에 해당하는 학생목록을 조회하는 메서드.
   *
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
   * 파라미터로 보낸 수업의 숙제를 확인하는 것
   * @param teacherId 선생님 id
   * @param lessonId 조회할 수업 id
   * @return 해당 수업의 숙제가 있는 날짜와 해당 날짜에 있는 숙제 수를 반환
   */
  public List<OutFilteredHomeworkFrequency> findFilteredHomeworkFrequency(Long teacherId, Long lessonId, LocalDateTime startDateOfMonth, LocalDateTime endDateOfMonth) {
    return queryFactory
        .select(Projections.constructor(OutFilteredHomeworkFrequency.class,
            homeworkJpaEntity.startTime.as("date"),
            homeworkJpaEntity.id.count().as("homeworkCnt")
        ))
        .from(homeworkJpaEntity)
        .innerJoin(homeworkJpaEntity.lessonJpaEntity, lessonJpaEntity)
        .where(
            eqTeacherId(teacherId),
            eqLessonId(lessonId),
            betweenYearMonth(startDateOfMonth, endDateOfMonth)
        )
        .groupBy(lessonJpaEntity.startTime)
        .fetch();
  }

  private BooleanExpression eqLessonId(Long lessonId) {
    log.info("lesson Id: {} ", lessonId);
    return lessonId != null ? lessonJpaEntity.teacherJpaEntity.id.eq(lessonId) : null;
  }

  private BooleanExpression betweenYearMonth(LocalDateTime start, LocalDateTime end) {
    log.info("startTime endTime: {}, {} ", start, end);
    if (start != null && end != null) {
      return homeworkJpaEntity.startTime.between(start, end);
    } else {
      return null;
    }
  }






}
