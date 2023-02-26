package com.noti.noti.homework.application.port.out;

import com.noti.noti.homework.domain.model.Homework;

public interface SaveHomeworkPort {

  Homework saveHomework(Homework homework);

}
