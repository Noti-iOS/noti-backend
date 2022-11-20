package com.noti.noti.error;

public enum ErrorCode {

  // Common
  INVALID_INPUT_VALUE(400, "C001", " 옳바르지 않는 입력 값 입니다"),
  METHOD_NOT_ALLOWED(405, "C002", " 옳바르지 않는 입력 값 입니다"),
  ENTITY_NOT_FOUND(400, "C003", " 정보가 존재하지 않습니다"),
  INTERNAL_SERVER_ERROR(500, "C004", "서버 에러"),
  INVALID_TYPE_VALUE(400, "C005", " 옳바르지 않는 타입의 값을 입력했습니다"),
  HANDLE_ACCESS_DENIED(403, "C006", "접근이 거부되었습니다"),


  /*
  아래에 해당 도메인에 대한 예외 작성
  */

  // Student
  STUDENT_NOT_FOUND(404, "S001", " 해당 학생 정보가 존재하지 않습니다");


  private int status;
  private final String code;
  private final String message;

  ErrorCode(final int status, final String code, final String message) {
    this.status = status;
    this.message = message;
    this.code = code;
  }

  public int getStatus() {
    return status;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

}
