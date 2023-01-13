package com.noti.noti.lesson.adapter.in.web.controller;

import com.noti.noti.error.ErrorResponse;
import com.noti.noti.homework.adapter.in.web.dto.FrequencyOfLessonsDto;
import com.noti.noti.homework.adapter.out.persistence.HomeworkPersistenceAdapter;
import com.noti.noti.lesson.adapter.in.web.dto.response.TodaysLessonHomeworkDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetFrequencyOfLessonsController {

  private final HomeworkPersistenceAdapter homeworkPersistenceAdapter;


  @Operation()
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "성공",
          content = {@Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = FrequencyOfLessonsDto.class)))}),
      @ApiResponse(responseCode = "500", description = "서버에러", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))}),
      @ApiResponse(responseCode = "400", description = "올바르지 않은 값입니다.", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))})
  })
  @Parameter(name = "userDetails", hidden = true)
  @GetMapping("/api/teacher/calendar/{year}/{month}")
  public ResponseEntity getFrequencyOfLessons(
      @PathVariable String year, @PathVariable String month,
      @AuthenticationPrincipal UserDetails userDetails) {


    long teacherId = Long.parseLong(userDetails.getUsername()); // 선생님이 개설한 것만 봐야함 (모두 보면 놉)

    /**
     * 선생님1  - 수업1 - 숙제1, 숙제2, 숙제3 - d1
     *        - 수업2 - 숙제4, 숙제5, 숙제6 - d2
     *        - 수업1 - 숙제7, 숙제8 - d3
     * 선생님2  - 수업3 - 숙제9 - d1
     *        - 수업3 - 숙제10, 숙제11 - d3
     *        - 수업4 - 숙제12, 숙제13, 숙제14 - d4
     * 선생님3  - 수업5 - 숙제15, 숙제16, 숙제17 - d1
     *        - 수업5 - 숙제18, 숙제19, - d5
     *        - 수업5 - 숙제20, 숙제21, 숙제22 - d6
     *
     *
     * 1. 수업 테이블에서 선생님의 id가 같은 수업 객체 조회
     * 2. 조회한 객체의 수업 id를 조회
     * 3. 그 수업 id로 숙제 객체에서 숙제 조회
     * 4. 조회한 숙제의 endTime을 groupBy 하여 수업의 id를 distinct count 하기 + endTime 구하기
     *
     * A
     * select l.id as lessonId
     * from lesson l
     * where l.teacherId = 주어진Id
     *
     *
     *--> 선생님 id 가져오기
     *--> lesson 에서 선생님 id로 검색 -> 튜플임
     *
     *
     *
     * B
     * select count(distinct h.lessonId) as cnt, h.endTime
     * from (select l.id as lessonId from lesson l where l.teacherId=주어진Id) a left join homework h
     * on a.lessonId = h.lessonId
     * where h.endTime between startTime and endTime
     * groupBy(h.endTime)
     *
     *
     *
     *
     *
     */



    String yearMonth = new StringBuilder().append(year).append("-").append(month).toString();
    List<FrequencyOfLessonsDto> responseDto = homeworkPersistenceAdapter.findFrequencyOfLessons(yearMonth, teacherId);

    return ResponseEntity.ok(responseDto);
  }


}
