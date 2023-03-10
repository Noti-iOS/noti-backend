package com.noti.noti.homework.application.port.in;

import com.noti.noti.homework.domain.model.Homework;
import java.util.List;
import java.util.function.Function;

public interface AddHomeworksUsecase {

  void addHomeworks(AddHomeworksCommand addHomeworksCommand);
}
