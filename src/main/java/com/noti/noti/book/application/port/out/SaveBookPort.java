package com.noti.noti.book.application.port.out;

import com.noti.noti.book.domain.model.Book;

public interface SaveBookPort {

  Book saveBook(Book book);
}
