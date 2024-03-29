package com.noti.noti.homework.adapter.out.persistence;

import static com.noti.noti.homework.adapter.out.persistence.jpa.model.QHomeworkJpaEntity.homeworkJpaEntity;
import static com.noti.noti.lesson.adapter.out.persistence.jpa.model.QLessonJpaEntity.lessonJpaEntity;
import static com.noti.noti.studenthomework.adapter.out.persistence.jpa.model.QStudentHomeworkJpaEntity.studentHomeworkJpaEntity;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.core.types.dsl.Expressions.as;
import static com.querydsl.core.types.dsl.Expressions.booleanOperation;
import static com.querydsl.core.types.dsl.Expressions.constant;
import static com.querydsl.core.types.dsl.Expressions.dateTemplate;

import com.noti.noti.homework.application.port.out.OutFilteredHomeworkFrequency;
import com.noti.noti.homework.application.port.out.OutHomeworkContent;
import com.noti.noti.homework.application.port.out.SearchedHomework;
import com.noti.noti.homework.application.port.out.TodayHomeworkCondition;
import com.noti.noti.homework.application.port.out.TodaysHomework;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringExpressions;
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
    return now != null ? booleanOperation(Ops.BETWEEN, constant(now),
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
            as(constant(startDateOfMonth.getYear()), "year"),
            as(constant(startDateOfMonth.getMonthValue()), "month"),
            homeworkJpaEntity.startTime.dayOfMonth().as("day"),
            homeworkJpaEntity.id.count().as("homeworkCnt")
        ))
        .from(homeworkJpaEntity)
        .innerJoin(homeworkJpaEntity.lessonJpaEntity, lessonJpaEntity)
        .where(
            eqTeacherId(teacherId),
            eqLessonId(lessonId),
            betweenYearMonth(startDateOfMonth, endDateOfMonth)
        )
        .groupBy(homeworkJpaEntity.startTime.dayOfMonth(), homeworkJpaEntity.startTime.yearMonth())
        .fetch();
  }

  private BooleanExpression eqLessonId(Long lessonId) {
    log.info("lesson Id: {} ", lessonId);
    return lessonId != null ? lessonJpaEntity.id.eq(lessonId) : null;
  }

  private BooleanExpression betweenYearMonth(LocalDateTime start, LocalDateTime end) {
    log.info("startTime endTime: {}, {} ", start, end);
    if (start != null && end != null) {
      return homeworkJpaEntity.startTime.between(start, end);
    } else {
      return null;
    }
  }

  public List<OutHomeworkContent> findHomeworkContents(Long lessonId, LocalDateTime date) {
    return queryFactory
        .select(Projections.constructor(OutHomeworkContent.class,
            homeworkJpaEntity.homeworkName,
            studentHomeworkJpaEntity.studentJpaEntity.id.count().as("studentCnt"),
            studentHomeworkJpaEntity.homeworkStatus.when(true).then(1L).otherwise(0L)
                .sum().as("completeCnt")))
        .from(studentHomeworkJpaEntity)
        .join(studentHomeworkJpaEntity.homeworkJpaEntity, homeworkJpaEntity)
        .where(
            eqLessonOfHomework(lessonId),
            eqYearAndMonthOfStartTime(date)
        )
        .groupBy(homeworkJpaEntity)
        .fetch();
  }

  /**
   * 숙제의 startTime과 id를 오름차순으로 정렬했을 때, cursorId 보다 작은 size개의 homework 목록을 반환한다.
   * @param teacherId 선생님 ID
   * @param keyword 검색어
   * @param size 페이지 안의 컨텐츠 개수
   * @param cursorId 커서 아이디
   * @return homeworkName에 검색어를 포함한 size개의 숙제 목록
   */
  public List<SearchedHomework> findSearchedHomework(Long teacherId, String keyword, int size, String cursorId) {

    return queryFactory
        .select(
            Projections.fields(SearchedHomework.class,
                homeworkJpaEntity.homeworkName,
                lessonJpaEntity.lessonName,
                lessonJpaEntity.startTime,
                lessonJpaEntity.endTime,
                homeworkJpaEntity.startTime.as("startDate"),
                generateCursorIdByTimeAndId(homeworkJpaEntity.startTime, homeworkJpaEntity.id).as("cursorId")
            )
        )
        .from(homeworkJpaEntity)
        .innerJoin(homeworkJpaEntity.lessonJpaEntity, lessonJpaEntity)
        .where(
//            likeHomeworkName(keyword),
            containsHomeworkName(keyword),
            gtNextCursorId(cursorId),
            eqTeacherId(teacherId)
        )
        .orderBy(homeworkJpaEntity.startTime.asc(), homeworkJpaEntity.id.asc())
        .limit(size)
        .fetch();
  }
  private BooleanExpression likeHomeworkName(String keyword) {
    log.info("keyword {}", keyword);
    return keyword != null ? homeworkJpaEntity.homeworkName.like("%" + keyword + "%") : null;
  }

  private BooleanExpression eqYearAndMonthOfStartTime(LocalDateTime date) {
    log.info("startDate : {}, endDate : {}", date, date.plusDays(1).minusSeconds(1));
    return date != null ? homeworkJpaEntity.startTime.between(date, date.plusDays(1).minusSeconds(1)) : null;
  }
  private BooleanExpression containsHomeworkName(String keyword) {
    log.info("keyword {}", keyword);
    return keyword != null ? homeworkJpaEntity.homeworkName.contains(keyword) : null;
  }

  private BooleanExpression eqLessonOfHomework(Long lessonId) {
    return lessonId != null ? homeworkJpaEntity.lessonJpaEntity.id.eq(lessonId) : null;
  }
  private BooleanExpression gtNextCursorId(String cursorId) {
    StringExpression cursorIdByDateAndId = generateCursorIdByTimeAndId(homeworkJpaEntity.startTime, homeworkJpaEntity.id);
    return cursorId.equals("0") ? null : cursorIdByDateAndId.gt(cursorId);
  }

  private StringExpression generateCursorIdByTimeAndId(DateTimePath<LocalDateTime> localDateTime, NumberPath<Long> homeworkId) {
    return lpadExpression(dateFormatExpression(localDateTime, "%Y%m%d%H%i%s"), 14, '0')
        .concat(lpadExpression(homeworkId.stringValue(), 10, '0'));
  }

  private StringExpression lpadExpression(StringExpression expression, int length, char c) {
    return StringExpressions.lpad(expression, length, c);
  }

  private StringExpression dateFormatExpression(DateTimePath<LocalDateTime> localDateTime, String pattern) {
    return dateTemplate(String.class,
        "function('date_format', {0}, {1})",
        localDateTime, ConstantImpl.create(pattern)).stringValue();
  }

}
