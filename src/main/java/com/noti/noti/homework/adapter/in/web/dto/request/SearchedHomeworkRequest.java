package com.noti.noti.homework.adapter.in.web.dto.request;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SearchedHomeworkRequest {


  @NotBlank
  private Long teacherId;

  @NotNull
  private String keyword;

  @Min(0)
  @NotBlank
  private int size;

  @NotNull
  private String cursorId;




}
