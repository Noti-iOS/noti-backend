package com.noti.noti.homework.application.port.in;

import com.noti.noti.homework.application.port.out.SearchedHomework;
import java.util.List;

public interface GetSearchedHomeworkQuery {

  List<SearchedHomework> getSearchedHomeworks(SearchedHomeworkCommand command);

}
