package com.noti.noti.book.adapter.out.persistence;

import com.noti.noti.book.adapter.out.persistence.jpa.model.BookJpaEntity;
import com.noti.noti.book.domain.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

  Book mapToDomainEntity(BookJpaEntity bookJpaEntity) {
    return Book.builder()
        .id(bookJpaEntity.getId())
        .title(bookJpaEntity.getTitle())
        .build();
  }

  BookJpaEntity mapToJpaEntity(Book book) {
    return BookJpaEntity.builder()
        .id(book.getId())
        .title(book.getTitle())
        .build();
  }
}
