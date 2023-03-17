package com.noti.noti.homework.adapter.in.web.dto.response;

import com.noti.noti.homework.application.port.in.InSearchedPageDto;
import com.noti.noti.homework.application.port.in.InSearchedPageDto.InSearchedHomework;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class SearchedPageDto {

  private String nextCursorId;
  private List<SearchedHomeworkDto> searchedHomeworks = new ArrayList<>();
  private boolean last;

  public SearchedPageDto(InSearchedPageDto inSearchedPageDto) {
    this.nextCursorId = inSearchedPageDto.getNextCursorId();
    this.last = inSearchedPageDto.getLast();
    inSearchedPageDto.getSearchedHomeworks()
        .forEach(in -> this.searchedHomeworks.add(new SearchedHomeworkDto(in)));
  }

  private class SearchedHomeworkDto {

    private String homeworkName;
    private String lessonName;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;

    public SearchedHomeworkDto(InSearchedHomework in) {
      this.homeworkName = in.getHomeworkName();
      this.lessonName = in.getLessonName();
      this.startTime = in.getStartTime();
      this.endTime = in.getEndTime();
      this.startDate = in.getStartDate();
    }

  }

}
