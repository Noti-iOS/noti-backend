package com.noti.noti.book.adapter.out.persistence;

import com.noti.noti.book.adapter.out.persistence.jpa.BookJpaRepository;
import com.noti.noti.book.application.port.out.SaveBookPort;
import com.noti.noti.book.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookPersistenceAdapter implements SaveBookPort {

  private final BookJpaRepository bookJpaRepository;
  private final BookMapper bookMapper;

  @Override
  public Book saveBook(Book book) {
    return null;
  }
}
