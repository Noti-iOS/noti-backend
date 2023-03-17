package com.noti.noti.homework.application.port.in;

public interface GetSearchedHomeworkQuery {

  InSearchedPageDto getSearchedHomeworks(Long teacherId, String keyword, String cursorId, int size);

}
