package com.noti.noti.book.adapter.out.persistence;

import com.noti.noti.book.adapter.out.persistence.jpa.model.BookJpaEntity;
import com.noti.noti.book.domain.model.Book;
import com.noti.noti.teacher.adpater.out.persistence.TeacherMapper;
import java.util.Calendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

  private final TeacherMapper teacherMapper;

  public Book mapToDomainEntity(BookJpaEntity bookJpaEntity) {

    return Book.builder()
        .id(bookJpaEntity.getId())
        .title(bookJpaEntity.getTitle())
        .teacher(teacherMapper.mapToDomainEntity(bookJpaEntity.getTeacherJpaEntity()))
        .build();
  }

  public BookJpaEntity mapToJpaEntity(Book book) {
    return BookJpaEntity.builder()
        .id(book.getId())
        .title(book.getTitle())
        .teacherJpaEntity(teacherMapper.mapToJpaEntity(book.getTeacher()))
        .build();
  }
}
