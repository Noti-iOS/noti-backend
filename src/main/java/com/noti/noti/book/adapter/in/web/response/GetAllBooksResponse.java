package com.noti.noti.book.adapter.in.web.response;

import com.noti.noti.book.application.port.out.BookDto;
import lombok.Getter;

@Getter
public class GetAllBooksResponse {

  private Long id;
  private String title;

  private GetAllBooksResponse(Long id, String title) {
    this.id = id;
    this.title = title;
  }

  public static GetAllBooksResponse from(BookDto book) {
    return new GetAllBooksResponse(book.getId(), book.getTitle());
  }
}
