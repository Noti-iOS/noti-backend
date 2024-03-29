package com.noti.noti.book.adapter.in.web.controller;

import com.noti.noti.book.adapter.in.web.request.AddBookRequest;
import com.noti.noti.book.application.port.in.AddBookUsecase;
import com.noti.noti.book.domain.model.Book;
import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
import com.noti.noti.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
public class AddBookController {

  private final AddBookUsecase addBookUsecase;

  @Operation(tags = "교재 추가 API ", summary = "addBook", description = "교재 추가")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "성공",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = SuccessResponse.class))}),
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
  @PostMapping("/api/teacher/books")
  public ResponseEntity addBook(@Valid @RequestBody AddBookRequest addBookRequest,
      @AuthenticationPrincipal UserDetails userDetails) {
    Long teacherId = Long.parseLong(userDetails.getUsername());

    Book savedBook = addBookUsecase.apply(addBookRequest.toCommand(teacherId));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedBook.getId())
        .toUri();

    return ResponseEntity.created(location).body(SuccessResponse.create201SuccessResponse());
  }
}
