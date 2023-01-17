package com.noti.noti.error;

public enum ErrorCode {

  // Common
  INVALID_INPUT_VALUE(400, "C001", " 올바르지 않은 입력 값입니다"),
  METHOD_NOT_ALLOWED(405, "C002", " 올바르지 않은 호출입니다"),
  ENTITY_NOT_FOUND(400, "C003", " 정보가 존재하지 않습니다"),
  INTERNAL_SERVER_ERROR(500, "C004", "서버 에러"),
  INVALID_TYPE_VALUE(400, "C005", " 바르지 않는 타입의 값을 입력했습니다"),
  HANDLE_ACCESS_DENIED(403, "C006", "접근이 거부되었습니다"),
  AUTHENTICATION_ENTRY_POINT(401, "C007", "인증되지 않은 사용자입니다"),
  AUTHENTICATION_FAILED(401, "C008", "인증 실패"),
  OAUTH_AUTHENTICATION_FAILED(401, "C009", "Oauth server 인증 실패입니다"),
  INVALID_REQUEST(400, "C010", "잘못된 요청입니다"),

  /*
  아래에 해당 도메인에 대한 예외 작성
  */

  /**
   * JWT 관련 error code
   */
  EXPIRED_JWT(401, "J001", "만료된 토근입니다"),
  UNSUPPORTED_JWT(401, "J002", "지원하지 않는 토근입니다"),
  MALFORMED_JWT(401, "J003", "잘못된 토근입니다"),
  INVALID_SIGNATURE_JWT(401, "J004", "잘못된 토근입니다"),
  ILLEGAL_ARGUMENT_JWT(401, "J005", "토큰이 비어있거나 잘못되었습니다"),

  // Student
  STUDENT_NOT_FOUND(404, "S001", "해당 학생 정보가 존재하지 않습니다"),

  /*
  Teacher 관련 예외
   */
  TEACHER_NOT_FOUND(404, "T001", "해당 선생님 정보가 존재하지 않습니다");

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
