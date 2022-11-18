package com.noti.noti.homework.adapter.out.persistence;

import com.noti.noti.book.adapter.out.persistence.BookMapper;
import com.noti.noti.homework.adapter.out.persistence.jpa.model.HomeworkJpaEntity;
import com.noti.noti.homework.domain.model.Homework;
import com.noti.noti.lesson.adapter.out.persistence.LessonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HomeworkMapper {

  private final LessonMapper lessonMapper;
  private final BookMapper bookMapper;

  public Homework mapToDomainEntity(HomeworkJpaEntity homeworkJpaEntity){
    return Homework.builder()
        .id(homeworkJpaEntity.getId())
        .lesson(lessonMapper.mapToDomainEntity(homeworkJpaEntity.getLesson()))
        .book(bookMapper.mapToDomainEntity(homeworkJpaEntity.getBook()))
        .startTime(homeworkJpaEntity.getStartTime())
        .endTime(homeworkJpaEntity.getEndTime())
        .build();
  }

  public HomeworkJpaEntity mapToJpaEntity(Homework homework) {
    return HomeworkJpaEntity.builder()
        .id(homework.getId())
        .content(homework.getContent())
        .lesson(lessonMapper.mapToJpaEntity(homework.getLesson()))
        .book(bookMapper.mapToJpaEntity(homework.getBook()))
        .startTime(homework.getStartTime())
        .endTime(homework.getEndTime())
        .build();
  }
}
