package com.noti.noti.lessonbook.adapter.out.persistence;

import com.noti.noti.book.adapter.out.persistence.BookMapper;
import com.noti.noti.book.adapter.out.persistence.jpa.BookJpaRepository;
import com.noti.noti.book.adapter.out.persistence.jpa.model.BookJpaEntity;
import com.noti.noti.lesson.adapter.out.persistence.LessonMapper;
import com.noti.noti.lesson.adapter.out.persistence.jpa.LessonJpaRepository;
import com.noti.noti.lesson.adapter.out.persistence.jpa.model.LessonJpaEntity;
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
        .book(bookMapper.mapToJpaEntity(lessonBook.getBook()))
        .lesson(lessonMapper.mapToJpaEntity(lessonBook.getLesson()))
        .build();
  }

  LessonBook mapToDomainEntity(LessonBookJpaEntity lessonBookJpaEntity) {
    return LessonBook.builder()
        .id(lessonBookJpaEntity.getId())
        .book(bookMapper.mapToDomainEntity(lessonBookJpaEntity.getBook()))
        .lesson(lessonMapper.mapToDomainEntity(lessonBookJpaEntity.getLesson()))
        .build();
  }
}
