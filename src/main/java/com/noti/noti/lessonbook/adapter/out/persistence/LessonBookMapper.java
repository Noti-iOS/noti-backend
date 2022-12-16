package com.noti.noti.lessonbook.adapter.out.persistence;

import com.noti.noti.book.adapter.out.persistence.BookMapper;
import com.noti.noti.lesson.adapter.out.persistence.LessonMapper;
import com.noti.noti.lessonbook.adapter.out.persistence.jpa.model.LessonBookJpaEntity;
import com.noti.noti.lessonbook.domain.model.LessonBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LessonBookMapper {
  private final BookMapper bookMapper;
  private final LessonMapper lessonMapper;

  LessonBookJpaEntity mapToJpaEntity(LessonBook lessonBook) {

    return LessonBookJpaEntity.builder()
        .id(lessonBook.getId())
        .bookJpaEntity(bookMapper.mapToJpaEntity(lessonBook.getBook()))
        .lessonJpaEnity(lessonMapper.mapToJpaEntity(lessonBook.getLesson()))
        .build();
  }

  LessonBook mapToDomainEntity(LessonBookJpaEntity lessonBookJpaEntity) {
    return LessonBook.builder()
        .id(lessonBookJpaEntity.getId())
        .book(bookMapper.mapToDomainEntity(lessonBookJpaEntity.getBookJpaEntity()))
        .lesson(lessonMapper.mapToDomainEntity(lessonBookJpaEntity.getLessonJpaEnity()))
        .build();
  }
}
