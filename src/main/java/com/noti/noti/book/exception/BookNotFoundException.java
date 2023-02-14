package com.noti.noti.book.exception;

import com.noti.noti.error.ErrorCode;
import com.noti.noti.error.exception.BusinessException;

public class BookNotFoundException extends BusinessException {

  public BookNotFoundException(Long id) {
    super("id: " + id, ErrorCode.BOOK_NOT_FOUND);
  }
}
