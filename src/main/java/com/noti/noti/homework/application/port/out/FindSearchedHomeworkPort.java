package com.noti.noti.homework.application.port.out;

import java.util.List;

public interface FindSearchedHomeworkPort {

   List<SearchedHomework> findSearchedHomeworks(Long teacherId, String keyword, int size, String cursorId);

}
