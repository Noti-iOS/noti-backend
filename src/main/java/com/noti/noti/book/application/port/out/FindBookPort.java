package com.noti.noti.book.application.port.out;

import com.noti.noti.book.domain.model.Book;
import java.util.List;
import java.util.Optional;

public interface FindBookPort {

  Optional<Book> findBookById(Long id);

  Optional<Book> findBookByTitle(String title);

  List<BookDto> findBooksByTeacherId(Long teacherId);

  boolean isExistedBook(FindBookCondition condition);
}
