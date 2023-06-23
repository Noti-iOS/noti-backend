package com.noti.noti.homework.adapter.in.web.dto.response;

import com.noti.noti.homework.application.port.in.InSearchedPageDto;
import com.noti.noti.homework.application.port.in.InSearchedPageDto.InSearchedHomework;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchedPageDto {

  @Schema(description = "검색 결과 목록 중 마지막 원소의 커서 아이디, 다음 원소가 없다면 빈 문자열(\"\")을 반환", example = "202305011310070000000011")
  private String nextCursorId;

  @Schema(description = "검색 결과로 나온 숙제 목록")
  private List<SearchedHomeworkDto> searchedHomeworks = new ArrayList<>();

  @Schema(description = "마지막 검색 결과임을 알려주는 값, 마지막 검색 페이지라면 true를 반환", example = "true")
  private boolean last;

  public SearchedPageDto(InSearchedPageDto inSearchedPageDto) {
    this.nextCursorId = inSearchedPageDto.getNextCursorId();
    this.last = inSearchedPageDto.getLast();
    inSearchedPageDto.getSearchedHomeworks()
        .forEach(in -> this.searchedHomeworks.add(new SearchedHomeworkDto(in)));
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  private class SearchedHomeworkDto {

    @Schema(description = "검색결과로 나온 숙제 이름", example = "쎈 수학 p.10 ~ p.12")
    private String homeworkName;

    @Schema(description = "숙제를 낸 수업 이름", example = "수학 A반")
    private String lessonName;

    @Schema(description = "수업의 강의 시작시간", example = "17:00")
    private LocalTime startTime;

    @Schema(description = "수업의 강의 끝나는 시간", example = "19:00")
    private LocalTime endTime;

    @Schema(description = "숙제를 낸 시간", example = "2023-02-01")
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
