package com.noti.noti.book.adapter.in.web.controller;

import com.noti.noti.book.adapter.in.web.response.GetAllBooksResponse;
import com.noti.noti.book.application.port.in.GetAllBooksQuery;
import com.noti.noti.common.adapter.in.web.response.SuccessResponse;
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

  @GetMapping("/api/teacher/books")
  public ResponseEntity getAllBooks(@AuthenticationPrincipal UserDetails userDetails) {
    long teacherId = Long.parseLong(userDetails.getUsername());

    List<GetAllBooksResponse> response = new ArrayList<>();
    getAllBooksQuery.getAllBooks(teacherId)
        .forEach(bookDto -> response.add(GetAllBooksResponse.from(bookDto)));

    return ResponseEntity.ok(SuccessResponse.create200SuccessResponse(response));
  }
}
