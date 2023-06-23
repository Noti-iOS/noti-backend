package com.noti.noti.homework.adapter.in.web.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class SearchedHomeworkRequest {

//  @NotNull
//  @Schema(description = "선생님 id", required = true, example = "1")
//  private Long teacherId;

  @NotNull
  @Schema(description = "검색어", required = true, example = "영어단어 day")
  private String keyword;

  @Min(0)
  @NotNull
  @Schema(description = "검색 결과 목록 수", required = true, example = "수학")
  private int size;

  @NotNull
  @Size(min = 24, max = 24)
  @Schema(description = "다음 요청시 전달할 커서 아이디, 맨 처음 요청은 \"000000000000000000000000\"으로 요청 문자열 길이는 24", required = true,
      example = "202305011310070000000011")
  private String cursorId;


  public SearchedHomeworkRequest(String keyword, int size, String cursorId) {
//    this.teacherId = teacherId;
    this.keyword = keyword;
    this.size = size;
    this.cursorId = cursorId;
  }
}
