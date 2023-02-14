package com.noti.noti.book.adapter.out.persistence;

import com.noti.noti.book.adapter.out.persistence.jpa.BookJpaRepository;
import com.noti.noti.book.application.port.out.FindBookCondition;
import com.noti.noti.book.application.port.out.FindBookPort;
import com.noti.noti.book.application.port.out.SaveBookPort;
import com.noti.noti.book.domain.model.Book;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookPersistenceAdapter implements SaveBookPort, FindBookPort {

  private final BookJpaRepository bookJpaRepository;
  private final BookQueryRepository bookQueryRepository;
  private final BookMapper bookMapper;

  @Override
  public Book saveBook(Book book) {
    Book savedBook = bookMapper.mapToDomainEntity(
        bookJpaRepository.save(bookMapper.mapToJpaEntity(book)));
    return savedBook;
  }

  @Override
  public Optional<Book> findBookById(Long id) {
    return bookJpaRepository.findById(id).map(bookMapper::mapToDomainEntity);
  }

  @Override
  public Optional<Book> findBookByTitle(String title) {
    return bookJpaRepository.findByTitle(title).map(bookMapper::mapToDomainEntity);
  }

  @Override
  public boolean isExistedBook(FindBookCondition condition) {
    return bookQueryRepository.isExistedBook(condition);
  }
}
