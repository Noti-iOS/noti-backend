package com.noti.noti.homework.application.port.in;

import java.util.List;

public interface GetFilteredHomeworkQuery {

  /**
   * 주어진 년-월에서 주어진 분반의 숙제가 있는 날짜, 그 날의 숙제 빈도를 반환
   * @param command
   * @return 년, 월, 수업 id가 들어있는 InFilteredHomeworkFrequency 리스트
   */
  List<InFilteredHomeworkFrequency> getFilteredHomeworks(FilteredHomeworkCommand command);

}
