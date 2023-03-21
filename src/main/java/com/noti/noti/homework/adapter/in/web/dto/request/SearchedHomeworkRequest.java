package com.noti.noti.homework.adapter.in.web.dto.request;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchedHomeworkRequest {

  private Long teacherId;

  @NotNull
  private String keyword;

  @Min(0)
  @NotNull
  private int size;

  @NotNull
  private String cursorId;


  public SearchedHomeworkRequest(Long teacherId, String keyword, int size, String cursorId) {
    this.teacherId = teacherId;
    this.keyword = keyword;
    this.size = size;
    this.cursorId = cursorId;
  }
}
