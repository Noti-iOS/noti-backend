package com.noti.noti.homework.application.port.in;

import java.util.List;

public interface GetHomeworkContentQuery {

  /**
   * 전달한 날짜와 분반에 해당하는 숙제 목록을 조회한다.
   * @param command 날짜, 수업 id 전달
   * @return 날짜와 분반에 해당하는 숙제 목록 반환
   */
  List<InHomeworkContent> getHomeworkContents(HomeworkContentCommand command);

}
