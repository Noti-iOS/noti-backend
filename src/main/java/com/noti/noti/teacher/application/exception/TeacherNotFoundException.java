package com.noti.noti.teacher.application.exception;

import com.noti.noti.error.ErrorCode;
import com.noti.noti.error.exception.BusinessException;

public class TeacherNotFoundException extends BusinessException {

  public TeacherNotFoundException(Long id) {
    super("teahcer ID: "+ id, ErrorCode.TEACHER_NOT_FOUND);
  }

  public TeacherNotFoundException() {
    super(ErrorCode.TEACHER_NOT_FOUND);
  }
}
