package com.noti.noti.lesson.application.service;

import com.noti.noti.lesson.adapter.in.web.dto.FrequencyOfLessonsDto;
import com.noti.noti.lesson.application.port.in.GetFrequencyOfLessonsQuery;
import com.noti.noti.lesson.application.port.out.FrequencyOfLessonsPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetFrequencyOfLessonsService implements GetFrequencyOfLessonsQuery{

  private final FrequencyOfLessonsPort frequencyOfLessonsPort;

  @Override
  public List<FrequencyOfLessonsDto> findFrequencyOfLessons(String yearMonth, Long teacherId) {
    return frequencyOfLessonsPort.findFrequencyOfLessons(yearMonth, teacherId);
  }
}
