package com.noti.noti.homework.adapter.in.web;

import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.error.ErrorResponse;
import com.noti.noti.homework.adapter.in.web.dto.request.SearchedHomeworkRequest;
import com.noti.noti.homework.adapter.in.web.dto.response.SearchedPageDto;
import com.noti.noti.homework.application.port.in.GetSearchedHomeworkQuery;
import com.noti.noti.homework.application.port.in.InSearchedPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetSearchedHomeworkController {

  private final GetSearchedHomeworkQuery getSearchedHomeworkQuery;

  @Operation(tags = "전체 숙제 검색 조회 API", summary = "getSearchedHomeworkInfo", description = "검색어를 포함하는 숙제 목록을 페이징 하여 반환한다.",
      responses = {
          @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "500", description = "서버에러", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))}),
          @ApiResponse(responseCode = "401", description = "안증되지 않은 유저입니다", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))}),
          @ApiResponse(responseCode = "400", description = "올바르지 않은 값입니다.", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))})
      })
  @GetMapping("/api/teacher/calendar/search")
  public ResponseEntity<SuccessResponse<SearchedPageDto>> getSearchedHomeworkInfo(@RequestBody @Valid SearchedHomeworkRequest request) {

//    Long teacherId = Long.parseLong(userDetails.getUsername());

    InSearchedPageDto searchedHomeworks = getSearchedHomeworkQuery.getSearchedHomeworks(request.getTeacherId(), request.getKeyword(), request.getCursorId(), request.getSize());
    SearchedPageDto response = new SearchedPageDto(searchedHomeworks);

    return ResponseEntity.ok().body(SuccessResponse.create200SuccessResponse(response));
  }

}
