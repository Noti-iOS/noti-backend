package com.noti.noti.common.adapter.in.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SuccessResponse<T> {
  public static final String SUCCESS_MESSAGE = "성공";

  @Schema(description = "성공 여부", example = "성공")
  private String message;

  @Schema(description = "상태 코드", example = "200")
  private int status;

  @Schema(description = "응답 데이터", nullable = true)
  private T data;

  public static <T> SuccessResponse create201SuccessResponse(T data) {

    return new SuccessResponse(SUCCESS_MESSAGE, 201, data);
  }

  public static SuccessResponse create201SuccessResponse() {

    return new SuccessResponse(SUCCESS_MESSAGE, 201, null);
  }

  public static <T> SuccessResponse create200SuccessResponse(T data) {

    return new SuccessResponse(SUCCESS_MESSAGE, 200, data);
  }
}
