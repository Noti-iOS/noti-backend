package com.noti.noti.lesson.exception;

import com.noti.noti.error.ErrorCode;
import com.noti.noti.error.exception.BusinessException;

public class LessonNotFoundException extends BusinessException {

  public LessonNotFoundException(Long id) {
    super("수업 ID : " + id, ErrorCode.LESSON_NOT_FOUND);
  }
}
