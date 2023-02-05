package com.noti.noti.book.application.port.out;

import com.noti.noti.book.domain.model.Book;
import java.util.Optional;

public interface FindBookPort {

  Optional<Book> findBookById(Long id);
}
