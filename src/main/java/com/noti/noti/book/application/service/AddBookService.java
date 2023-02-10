package com.noti.noti.book.application.service;

import com.noti.noti.book.application.port.in.AddBookCommand;
import com.noti.noti.book.application.port.in.AddBookUsecase;
import com.noti.noti.book.application.port.out.FindBookCondition;
import com.noti.noti.book.application.port.out.FindBookPort;
import com.noti.noti.book.application.port.out.SaveBookPort;
import com.noti.noti.book.domain.model.Book;
import com.noti.noti.book.exception.DuplicatedTitleBookException;
import com.noti.noti.teacher.application.exception.TeacherNotFoundException;
import com.noti.noti.teacher.application.port.out.FindTeacherPort;
import com.noti.noti.teacher.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddBookService implements AddBookUsecase {

  private final FindTeacherPort findTeacherPort;
  private final FindBookPort findBookPort;
  private final SaveBookPort saveBookPort;

  @Transactional
  @Override
  public Book apply(AddBookCommand addBookCommand) {
    Teacher requestTeacher = findTeacher(addBookCommand.getTeacherId());
    verifyBook(new FindBookCondition(addBookCommand.getTitle(), requestTeacher.getId()));
    Book book = Book.builder().teacher(requestTeacher).title(addBookCommand.getTitle()).build();

    return saveBookPort.saveBook(book);
  }

  private Teacher findTeacher(Long teacherId){
    return findTeacherPort.findById(teacherId)
        .orElseThrow(() -> new TeacherNotFoundException(teacherId));
  }

  private void verifyBook(FindBookCondition condition) {
    if(findBookPort.isExistedBook(condition)){
      throw new DuplicatedTitleBookException();
    }
  }
}
