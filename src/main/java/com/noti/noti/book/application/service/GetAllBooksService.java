package com.noti.noti.book.application.service;

import com.noti.noti.book.application.port.in.GetAllBooksQuery;
import com.noti.noti.book.application.port.out.BookDto;
import com.noti.noti.book.application.port.out.FindBookPort;
import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
import com.noti.noti.teacher.application.port.out.FindTeacherPort;
import com.noti.noti.teacher.domain.Teacher;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 선생님의 모든 교재목록 조회 Service
 * @author 임호준
 */
@Service
@RequiredArgsConstructor
public class GetAllBooksService implements GetAllBooksQuery {

  private final FindTeacherPort findTeacherPort;
  private final FindBookPort findBookPort;

  @Transactional(readOnly = true)
  @Override
  public List<BookDto> getAllBooks(Long teacherId) {
    Teacher foundTeacher = findTeacher(teacherId);
    return findBookPort.findBooksByTeacherId(foundTeacher.getId());
  }

  private Teacher findTeacher(Long teacherId) {
    return findTeacherPort.findById(teacherId).orElseThrow(TeacherNotFoundException::new);
  }
}
