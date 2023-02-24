package com.noti.noti.book.adapter.in.web.controller;

import com.noti.noti.book.adapter.in.web.response.GetAllBooksResponse;
import com.noti.noti.book.application.port.in.GetAllBooksQuery;
import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetAllBooksController {

  private final GetAllBooksQuery getAllBooksQuery;

  @Operation(tags = "교재 조회 API ", summary = "GetAllBooks", description = "선생님에 해당하는 모든 교재 조회",
      responses = {
          @ApiResponse(responseCode = "200", description = "성공",
              useReturnTypeSchema = true),
          @ApiResponse(responseCode = "500", description = "서버에러", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))}),
          @ApiResponse(responseCode = "401", description = "인증되지 않은 유저입니다", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))}),
          @ApiResponse(responseCode = "403", description = "권한이 없습니다", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))}),
          @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스입니댜", content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))})
      })
  @Parameter(name = "userDetails", hidden = true)
  @GetMapping("/api/teacher/books")
  public ResponseEntity<SuccessResponse<List<GetAllBooksResponse>>> getAllBooks(
      @AuthenticationPrincipal UserDetails userDetails) {
    long teacherId = Long.parseLong(userDetails.getUsername());

    List<GetAllBooksResponse> response = new ArrayList<>();
    getAllBooksQuery.getAllBooks(teacherId)
        .forEach(bookDto -> response.add(GetAllBooksResponse.from(bookDto)));

    return ResponseEntity.ok(SuccessResponse.create200SuccessResponse(response));
  }
}
