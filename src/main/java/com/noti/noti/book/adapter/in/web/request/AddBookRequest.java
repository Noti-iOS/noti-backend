package com.noti.noti.book.adapter.in.web.request;

import com.noti.noti.book.application.port.in.AddBookCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AddBookRequest {

  @NotBlank
  @Schema(description = "교재명", required = true, example = "수학의 정석")
  private String title;

  public AddBookCommand toCommand(Long teacherId) {
    return new AddBookCommand(teacherId, title);
  }
}
