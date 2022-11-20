package com.noti.noti.student.exception;

import com.noti.noti.error.ErrorCode;
import com.noti.noti.error.exception.EntityNotFoundException;

public class StudentNotFoundException extends EntityNotFoundException {

  public StudentNotFoundException(Long id) {
    super("학생 ID: "+ id, ErrorCode.STUDENT_NOT_FOUND);
  }
}
