package com.noti.noti.lesson.application.port.out;

public interface CheckLessonExistencePort {

  /**
   * 수업의 존재를 확인한다
   * @param lessonId 수업 id
   * @return 해당하는 id를 가진 수업의 존재여부
   */
  boolean existsById(Long lessonId);


}
