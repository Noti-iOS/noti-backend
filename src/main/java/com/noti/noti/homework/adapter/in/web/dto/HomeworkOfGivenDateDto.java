package com.noti.noti.homework.adapter.in.web.dto;

import com.noti.noti.homework.domain.model.Homework;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class HomeworkOfGivenDateDto {

  private Long LessonId;
  private String lessonName;
  private LocalDateTime startTimeOfLesson;
  private LocalDateTime endTimeOfLesson;
  private List<HomeworkDto> homeworks;

  public class HomeworkDto {

    private Long homeworkId;
    private String homeworkContent;
    private Long studentCnt;
    private Long completeCnt;

  }




}
