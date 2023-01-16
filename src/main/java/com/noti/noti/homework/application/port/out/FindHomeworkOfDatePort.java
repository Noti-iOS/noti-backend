package com.noti.noti.homework.application.port.out;

import com.noti.noti.homework.adapter.in.web.dto.HomeworkOfGivenDateDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FindHomeworkOfDatePort {

  List<HomeworkOfGivenDateDto> findHomeworksBy(LocalDate date, Long teacherId);

}
