package com.noti.noti.lesson.adapter.in.web.controller;

import com.noti.noti.error.ErrorResponse;
import com.noti.noti.lesson.adapter.in.web.dto.response.CreatedLessonsDto;
import com.noti.noti.lesson.adapter.in.web.dto.response.FrequencyOfLessonsDto;
import com.noti.noti.lesson.application.port.in.CreatedLessonCommand;
import com.noti.noti.lesson.application.port.in.GetCreatedLessonsQuery;
import com.noti.noti.lesson.application.port.in.InCreatedLesson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class GetCreatedLessonsController {

  private final GetCreatedLessonsQuery getCreatedLessonsQuery;

  @Operation(summary = "CreatedLessonInfo", description = "선생님이 생성한 분반을 리스트로 제공한다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "성공",
          content = {@Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = CreatedLessonsDto.class)))}),
      @ApiResponse(responseCode = "500", description = "서버에러", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))}),
      @ApiResponse(responseCode = "400", description = "올바르지 않은 값입니다.", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))})
  })
  @GetMapping("/api/teacher/calendar/createdLessons")
  @Parameter(name = "UserDetails", hidden = true)
  public ResponseEntity<List<CreatedLessonsDto>> getLessonsByTeacherId(@AuthenticationPrincipal
      UserDetails userDetails) {
    Long teacherId = Long.parseLong(userDetails.getUsername());

    List<InCreatedLesson> inCreatedLessons = getCreatedLessonsQuery.createdLessons(new CreatedLessonCommand(teacherId));
    List<CreatedLessonsDto> dto = inCreatedLessons.stream().map(CreatedLessonsDto::new).collect(Collectors.toList());

    return ResponseEntity.ok(dto);
  }


}
