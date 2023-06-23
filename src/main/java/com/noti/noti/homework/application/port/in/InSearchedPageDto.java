package com.noti.noti.homework.application.port.in;


import com.noti.noti.homework.application.port.out.SearchedHomework;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InSearchedPageDto {

  private String nextCursorId;
  private List<InSearchedHomework> searchedHomeworks = new ArrayList<>();
  private Boolean last;

  public InSearchedPageDto(List<SearchedHomework> searchedHomeworks, boolean last) {
    this.nextCursorId = (searchedHomeworks.size() < 1) ? "" : searchedHomeworks.get(searchedHomeworks.size()-1).getCursorId();
    searchedHomeworks.forEach(searchedHomework -> this.searchedHomeworks.add(new InSearchedHomework(searchedHomework)));
    this.last = last;
  }

  @Getter
  @NoArgsConstructor
  static public class InSearchedHomework {

    private String homeworkName;
    private String lessonName;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;

    public InSearchedHomework(SearchedHomework searchedHomework) {
      this.homeworkName = searchedHomework.getHomeworkName();
      this.lessonName = searchedHomework.getLessonName();
      this.startTime = searchedHomework.getStartTime();
      this.endTime = searchedHomework.getEndTime();
      this.startDate = searchedHomework.getStartDate().toLocalDate();
    }
  }


}
