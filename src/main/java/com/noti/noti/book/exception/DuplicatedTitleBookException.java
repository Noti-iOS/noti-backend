package com.noti.noti.book.exception;

import com.noti.noti.error.ErrorCode;
import com.noti.noti.error.exception.BusinessException;

public class DuplicatedTitleBookException extends BusinessException {

  public DuplicatedTitleBookException() {
    super(ErrorCode.DUPLICATED_TITLE_BOOK);
  }
}
