package com.noti.noti.book.adapter.in.web.response;

import com.noti.noti.book.application.port.out.BookDto;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "모든 교재 조회 응답")
public class GetAllBooksResponse {
  @Schema(description = "교재 ID", example = "1")
  @NotNull @Min(1)
  private Long id;
  @Schema(description = "교재명", example = "수학의 정석")
  @NotBlank
  private String title;

  private GetAllBooksResponse(Long id, String title) {
    this.id = id;
    this.title = title;
  }

  public static GetAllBooksResponse from(BookDto book) {
    return new GetAllBooksResponse(book.getId(), book.getTitle());
  }
}
