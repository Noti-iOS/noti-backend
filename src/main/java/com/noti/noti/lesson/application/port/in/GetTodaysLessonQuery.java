package com.noti.noti.lesson.application.port.in;

public interface GetTodaysLessonQuery {

  TodaysLessonHomework getTodaysLessons(Long teacherId);
}
