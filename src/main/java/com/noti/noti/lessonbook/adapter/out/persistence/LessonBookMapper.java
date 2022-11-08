package com.noti.noti.lessonbook.adapter.out.persistence;

import com.noti.noti.book.adapter.out.persistence.jpa.BookJpaRepository;
import com.noti.noti.book.adapter.out.persistence.jpa.model.BookJpaEntity;
import com.noti.noti.lesson.adapter.out.persistence.jpa.LessonJpaRepository;
import com.noti.noti.lesson.adapter.out.persistence.jpa.model.LessonJpaEntity;
import com.noti.noti.lessonbook.adapter.out.persistence.jpa.model.LessonBookJpaEntity;
import com.noti.noti.lessonbook.domain.model.LessonBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LessonBookMapper {
  private BookJpaRepository bookJpaRepository;
  private LessonJpaRepository lessonJpaRepository;

  LessonBookJpaEntity mapToJpaEntity(LessonBook lessonBook) {
    // Exception 정의 필요
    BookJpaEntity bookJpaEntity = bookJpaRepository.findById(lessonBook.getBookId())
        .orElseThrow(() -> new RuntimeException());
    LessonJpaEntity lessonJpaEntity = lessonJpaRepository.findById(lessonBook.getLessonId())
        .orElseThrow(() -> new RuntimeException());

    return LessonBookJpaEntity.builder()
        .id(lessonBook.getId())
        .book(bookJpaEntity)
        .lesson(lessonJpaEntity)
        .build();
  }

  LessonBook mapToDomainEntity(LessonBookJpaEntity lessonBookJpaEntity) {
    return LessonBook.builder()
        .id(lessonBookJpaEntity.getId())
        .bookId(lessonBookJpaEntity.getBook().getId())
        .lessonId(lessonBookJpaEntity.getLesson().getId())
        .build();
  }
}
