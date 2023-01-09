package com.noti.noti.lesson.adapter.out.persistence;

import static com.noti.noti.lesson.adapter.out.persistence.jpa.model.QLessonJpaEntity.lessonJpaEntity;
import static com.noti.noti.student.adapter.out.persistence.jpa.model.QStudentJpaEntity.studentJpaEntity;
import static com.noti.noti.studentlesson.adapter.out.persistence.jpa.model.QStudentLessonJpaEntity.studentLessonJpaEntity;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import com.noti.noti.lesson.application.port.out.TodaysLesson;
import com.noti.noti.lesson.application.port.out.TodaysLessonSearchConditon;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class LessonQueryRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 선생님의 수업목록을 조회하는 메서드
   * @param todaysLessonSearchConditon
   * 선생님의 ID 조건
   * @return
   * 선생님의 수업목록과 수업에 해당하는 학생 목록
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

  private BooleanExpression eqTeacherId(Long teacherId) {
    log.info("teacher Id: {} ", teacherId);
    return teacherId != null ? lessonJpaEntity.teacherJpaEntity.id.eq(teacherId) : null;
  }
}
