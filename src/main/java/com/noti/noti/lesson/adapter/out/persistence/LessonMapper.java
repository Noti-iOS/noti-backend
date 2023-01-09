package com.noti.noti.lesson.adapter.out.persistence;

import com.noti.noti.common.adapter.out.persistance.DaySetConvertor;
import com.noti.noti.lesson.adapter.out.persistence.jpa.model.LessonJpaEntity;
import com.noti.noti.lesson.domain.model.Lesson;
import com.noti.noti.teacher.adpater.out.persistence.TeacherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LessonMapper {
  private final TeacherMapper teacherMapper;
  private final DaySetConvertor daySetConvertor;

  public Lesson mapToDomainEntity(LessonJpaEntity lessonJpaEntity) {
    return Lesson.builder()
        .id(lessonJpaEntity.getId())
        .days(daySetConvertor.convertToEntityAttribute(lessonJpaEntity.getDays()))
        .lessonName(lessonJpaEntity.getLessonName())
        .startTime(lessonJpaEntity.getStartTime())
        .endTime(lessonJpaEntity.getEndTime())
        .createdAt(lessonJpaEntity.getCreatedAt())
        .modifiedAt(lessonJpaEntity.getModifiedAt())
        .teacher(teacherMapper.mapToDomainEntity(lessonJpaEntity.getTeacherJpaEntity()))
        .build();
  }

  public LessonJpaEntity mapToJpaEntity(Lesson lesson) {
    return LessonJpaEntity.builder()
        .id(lesson.getId())
        .days(daySetConvertor.convertToDatabaseColumn(lesson.getDays()))
        .lessonName(lesson.getLessonName())
        .endTime(lesson.getEndTime())
        .startTime(lesson.getStartTime())
        .teacherJpaEntity(teacherMapper.mapToJpaEntity(lesson.getTeacher()))
        .build();
  }
}
