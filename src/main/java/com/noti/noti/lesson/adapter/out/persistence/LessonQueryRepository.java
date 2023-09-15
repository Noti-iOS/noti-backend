package com.noti.noti.lesson.adapter.out.persistence;

import static com.noti.noti.homework.adapter.out.persistence.jpa.model.QHomeworkJpaEntity.homeworkJpaEntity;
import static com.noti.noti.lesson.adapter.out.persistence.jpa.model.QLessonJpaEntity.lessonJpaEntity;
import static com.noti.noti.student.adapter.out.persistence.jpa.model.QStudentJpaEntity.studentJpaEntity;
import static com.noti.noti.studentlesson.adapter.out.persistence.jpa.model.QStudentLessonJpaEntity.studentLessonJpaEntity;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import com.noti.noti.lesson.application.port.out.FrequencyOfLessons;
import com.noti.noti.lesson.application.port.out.OutCreatedLesson;
import com.noti.noti.lesson.application.port.out.LessonDto;
import com.noti.noti.lesson.application.port.out.StudentsInLesson;
import com.noti.noti.lesson.application.port.out.TodaysLesson;
import com.noti.noti.lesson.application.port.out.TodaysLessonSearchConditon;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
class LessonQueryRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 수업에 해당하는 학생목록을 조회하는 메서드
   * @param lessonId
   * @return
   */
  public Optional<StudentsInLesson> findLessonAndStudentsById(Long lessonId) {
    Map<Long, StudentsInLesson> transform = queryFactory
        .from(lessonJpaEntity)
        .leftJoin(studentLessonJpaEntity)
        .on(lessonJpaEntity.id.eq(studentLessonJpaEntity.lessonJpaEntity.id))
        .where(eqLessonId(lessonId))
        .transform(groupBy(lessonJpaEntity.id).as(Projections.fields(StudentsInLesson.class,
            Expressions.asNumber(lessonId).as("lessonId"),
            list(studentLessonJpaEntity.studentJpaEntity.id).as("studentIds"))));
    return Optional.ofNullable(transform.get(lessonId));
  }

  /**
   * 선생님의 수업목록을 조회하는 메서드
   *
   * @param todaysLessonSearchConditon 선생님의 ID 조건
   * @return 선생님의 수업목록과 수업에 해당하는 학생 목록
   */
  public List<TodaysLesson> findTodayLesson(TodaysLessonSearchConditon todaysLessonSearchConditon) {
    return queryFactory
        .from(lessonJpaEntity)
        .leftJoin(studentLessonJpaEntity)
        .on(studentLessonJpaEntity.lessonJpaEntity.id.eq(lessonJpaEntity.id))
        .leftJoin(studentLessonJpaEntity.studentJpaEntity, studentJpaEntity)
        .where(eqTeacherId(todaysLessonSearchConditon.getTeacherId()))
        .orderBy(lessonJpaEntity.startTime.asc(), studentJpaEntity.nickname.asc())
        .transform(groupBy(lessonJpaEntity.id).list(
            Projections.fields(TodaysLesson.class,
                lessonJpaEntity.id.as("lessonId"),
                lessonJpaEntity.lessonName,
                lessonJpaEntity.startTime,
                lessonJpaEntity.endTime,
                lessonJpaEntity.days,
                list(Projections.fields(TodaysLesson.LessonOfStudent.class,
                    studentLessonJpaEntity.studentJpaEntity.id.as("studentId"),
                    studentLessonJpaEntity.studentJpaEntity.nickname.as("studentNickname"),
                    studentJpaEntity.profileImage,
                    studentLessonJpaEntity.focusStatus).skipNulls()).as("students"))));
  }

  /**
   * 선생님에 해당하는 모든 수업 조회
   *
   * @param teacherId
   * @return
   */
  List<LessonDto> findAllLessonsByTeacherId(Long teacherId) {
    return queryFactory.select(Projections.fields(LessonDto.class,
            lessonJpaEntity.id,
            lessonJpaEntity.lessonName))
        .from(lessonJpaEntity)
        .where(eqTeacherId(teacherId))
        .fetch();
  }


  private BooleanExpression eqTeacherId(Long teacherId) {
    log.info("teacher Id: {} ", teacherId);
    return teacherId != null ? lessonJpaEntity.teacherJpaEntity.id.eq(teacherId) : null;
  }

  private BooleanExpression eqLessonId(Long lessonId) {
    log.info("lesson Id: {} ", lessonId);
    return lessonId != null ? lessonJpaEntity.id.eq(lessonId) : null;
  }

  /**
   * 숙제가 있는 날과 그 날의 분반 개수를 조회하는 메서드
   * @param start 시작 날짜
   * @param end   끝 날짜
   * @return 시작 ~ 끝 날짜에서 숙제가 있는 날의 날짜와 그 날짜의 분반 개수목록
   */
  public List<FrequencyOfLessons> findFrequencyOfLesson(LocalDateTime start, LocalDateTime end,
      Long teacherId) {

    return queryFactory
        .select(Projections.constructor(FrequencyOfLessons.class,
            homeworkJpaEntity.endTime.dayOfMonth().as("dayOfMonth"),
            lessonJpaEntity.id.countDistinct().as("frequencyOfLesson")))
        .from(homeworkJpaEntity)
        .join(homeworkJpaEntity.lessonJpaEntity, lessonJpaEntity)
        .where(
            homeworkJpaEntity.endTime.between(start, end.minusSeconds(1)),
            eqTeacherId(teacherId))
        .groupBy(homeworkJpaEntity.endTime.dayOfMonth())
        .fetch();
  }

  public List<OutCreatedLesson> findCreatedLessons(Long teacherId) {

    return queryFactory
        .select(Projections.constructor(OutCreatedLesson.class,
            lessonJpaEntity.id.as("lessonId"),
            lessonJpaEntity.lessonName))
        .from(lessonJpaEntity)
        .where(eqTeacherId(teacherId))
        .fetch();
  }

}
