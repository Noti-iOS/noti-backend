package com.noti.noti.book.application.port.in;

import com.noti.noti.book.application.port.out.BookDto;
import java.util.List;

/**
 * @author 임호준
 * @description 선생님의 모든 교재를 조회하기 위한 interface
 */
public interface GetAllBooksQuery {

  List<BookDto> getAllBooks(Long teacherId);
}
