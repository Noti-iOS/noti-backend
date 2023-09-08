package com.noti.noti.studenthomework.adapter.out.persistence;

import static com.noti.noti.homework.adapter.out.persistence.jpa.model.QHomeworkJpaEntity.homeworkJpaEntity;
import static com.noti.noti.lesson.adapter.out.persistence.jpa.model.QLessonJpaEntity.lessonJpaEntity;
import static com.noti.noti.studenthomework.adapter.out.persistence.jpa.model.QStudentHomeworkJpaEntity.studentHomeworkJpaEntity;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import com.noti.noti.studenthomework.application.port.out.OutHomeworkOfGivenDate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class StudentHomeworkQueryRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 주어진 날짜에 해당하는 수업 및 숙제 목록 반환
   * @param date 알고자 하는 숙제목록의 날짜
   * @param teacherId 선생님 Id
   * @return 수업과 숙제목록, 숙제를 받은 학생의 수와 숙제를 완료한 학생의 수
   */
  public List<OutHomeworkOfGivenDate> findHomeworkOfCalendar(Long lessonId, LocalDateTime date, Long teacherId) {

    return queryFactory
        .from(studentHomeworkJpaEntity)
        .join(studentHomeworkJpaEntity.homeworkJpaEntity, homeworkJpaEntity)
        .join(homeworkJpaEntity.lessonJpaEntity, lessonJpaEntity)
        .where(eqTeacherId(teacherId),
            eqYearAndMonthOfStartTime(date),
            eqLessonId(lessonId)
        )
        .groupBy(studentHomeworkJpaEntity.homeworkJpaEntity)
        .transform(
            groupBy(homeworkJpaEntity.lessonJpaEntity).list(
                Projections.fields(OutHomeworkOfGivenDate.class,
                    lessonJpaEntity.id.as("lessonId"),
                    lessonJpaEntity.lessonName,
                    lessonJpaEntity.startTime.as("startTimeOfLesson"),
                    lessonJpaEntity.endTime.as("endTimeOfLesson"),
                    list(Projections.fields(OutHomeworkOfGivenDate.HomeworkDto.class,
                        homeworkJpaEntity.id.as("homeworkId"),
                        homeworkJpaEntity.homeworkName.as("homeworkName"),
                        studentHomeworkJpaEntity.studentJpaEntity.id.count().as("studentCnt"),
                        studentHomeworkJpaEntity.homeworkStatus.when(true).then(1L).otherwise(0L)
                            .sum().as("completeCnt")
                    )).as("homeworks")
                )
            ));


  }

  private BooleanExpression eqTeacherId(Long teacherId) {
    log.info("teacher Id: {} ", teacherId);
    return teacherId != null ? lessonJpaEntity.teacherJpaEntity.id.eq(teacherId) : null;
  }

  private BooleanExpression eqYearAndMonthOfStartTime(LocalDateTime date) {
    return date != null ? homeworkJpaEntity.startTime.between(date, date.plusDays(1).minusSeconds(1)) : null;
  }

  private BooleanExpression eqLessonId(Long lessonId) {
    log.info("lessonId: {} ", lessonId);
    return lessonId != null ? studentHomeworkJpaEntity.homeworkJpaEntity.lessonJpaEntity.id.eq(lessonId) : null;
  }
}
