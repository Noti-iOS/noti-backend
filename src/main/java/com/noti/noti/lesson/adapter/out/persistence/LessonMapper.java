package com.noti.noti.lesson.adapter.out.persistence;

import com.noti.noti.lesson.adapter.out.persistence.jpa.model.LessonJpaEntity;
import com.noti.noti.lesson.domain.model.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {

  Lesson mapToDomainEntity(LessonJpaEntity lessonJpaEntity) {
    return Lesson.builder()
        .id(lessonJpaEntity.getId())
        .lessonName(lessonJpaEntity.getLessonName())
        .startTime(lessonJpaEntity.getStartTime())
        .endTime(lessonJpaEntity.getEndTime())
        .createdAt(lessonJpaEntity.getCreatedAt())
        .modifiedAt(lessonJpaEntity.getModifiedAt())
        .build();
  }

  LessonJpaEntity mapToJpaEntity(Lesson lesson) {
    return LessonJpaEntity.builder()
        .id(lesson.getId())
        .lessonName(lesson.getLessonName())
        .endTime(lesson.getEndTime())
        .startTime(lesson.getStartTime())
        .build();
  }
}
