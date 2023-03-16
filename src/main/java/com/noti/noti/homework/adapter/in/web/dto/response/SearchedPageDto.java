package com.noti.noti.homework.adapter.in.web.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class SearchedPageDto {

  private String nextCursorId;
  private List<SearchedHomework> searchedHomeworks = new ArrayList<>();

  public SearchedPageDto(List<com.noti.noti.homework.application.port.out.SearchedHomework> searchedHomeworks) {
    this.nextCursorId = searchedHomeworks.get(searchedHomeworks.size() - 1).getCursorId();
    searchedHomeworks.forEach(s -> this.searchedHomeworks.add(new SearchedHomework(s)));
  }

  private class SearchedHomework {

    private String homeworkName;
    private String lessonName;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;

    public SearchedHomework(
        com.noti.noti.homework.application.port.out.SearchedHomework searchedHomework) {
      this.homeworkName = searchedHomework.getHomeworkName();
      this.lessonName = searchedHomework.getLessonName();
      this.startTime = searchedHomework.getStartTime();
      this.endTime = searchedHomework.getEndTime();
      this.startDate = searchedHomework.getStartDate().toLocalDate();
    }

  }

}
