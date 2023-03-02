package com.noti.noti.homework.adapter.out.persistence;

import com.noti.noti.homework.adapter.out.persistence.jpa.model.HomeworkJpaEntity;
import com.noti.noti.homework.domain.model.Homework;
import com.noti.noti.lesson.adapter.out.persistence.LessonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HomeworkMapper {

  private final LessonMapper lessonMapper;
  public Homework mapToDomainEntity(HomeworkJpaEntity homeworkJpaEntity){
    return Homework.builder()
        .id(homeworkJpaEntity.getId())
        .homeworkName(homeworkJpaEntity.getHomeworkName())
        .lesson(lessonMapper.mapToDomainEntity(homeworkJpaEntity.getLessonJpaEntity()))
        .startTime(homeworkJpaEntity.getStartTime())
        .endTime(homeworkJpaEntity.getEndTime())
        .build();
  }

  public HomeworkJpaEntity mapToJpaEntity(Homework homework) {
    return HomeworkJpaEntity.builder()
        .id(homework.getId())
        .homeworkName(homework.getHomeworkName())
        .lessonJpaEntity(lessonMapper.mapToJpaEntity(homework.getLesson()))
        .startTime(homework.getStartTime())
        .endTime(homework.getEndTime())
        .build();
  }
}
