package com.noti.noti.lesson.application.exception;

import com.noti.noti.error.ErrorCode;
import com.noti.noti.error.exception.BusinessException;

public class LessonNotFoundException extends BusinessException {

  public LessonNotFoundException() {
    super(ErrorCode.LESSON_NOT_FOUND);
  }
}
