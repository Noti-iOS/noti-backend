package com.noti.noti.lesson.adapter.out.persistence;

import com.noti.noti.lesson.adapter.out.persistence.jpa.model.LessonJpaEntity;
import com.noti.noti.lesson.domain.model.Lesson;
import com.noti.noti.teacher.adpater.out.persistence.TeacherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LessonMapper {
  private final TeacherMapper teacherMapper;

  public Lesson mapToDomainEntity(LessonJpaEntity lessonJpaEntity) {
    return Lesson.builder()
        .id(lessonJpaEntity.getId())
        .days(lessonJpaEntity.getDaySet())
        .lessonName(lessonJpaEntity.getLessonName())
        .startTime(lessonJpaEntity.getStartTime())
        .endTime(lessonJpaEntity.getEndTime())
        .createdAt(lessonJpaEntity.getCreatedAt())
        .modifiedAt(lessonJpaEntity.getModifiedAt())
        .teacher(teacherMapper.mapToDomainEntity(lessonJpaEntity.getTeacher()))
        .build();
  }

  public LessonJpaEntity mapToJpaEntity(Lesson lesson) {
    return LessonJpaEntity.builder()
        .id(lesson.getId())
        .daySet(lesson.getDays())
        .lessonName(lesson.getLessonName())
        .endTime(lesson.getEndTime())
        .startTime(lesson.getStartTime())
        .teacher(teacherMapper.mapToJpaEntity(lesson.getTeacher()))
        .build();
  }
}
