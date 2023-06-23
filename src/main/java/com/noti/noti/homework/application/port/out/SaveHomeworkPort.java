package com.noti.noti.homework.application.port.out;

import com.noti.noti.homework.domain.model.Homework;
import java.util.List;

public interface SaveHomeworkPort {

  List<Long> saveAllHomeworks(List<Homework> homeworks);
}
