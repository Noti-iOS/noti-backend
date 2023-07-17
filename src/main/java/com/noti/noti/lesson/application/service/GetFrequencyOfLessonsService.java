package com.noti.noti.lesson.application.service;

import com.noti.noti.lesson.application.port.in.DateFrequencyOfLessons;
import com.noti.noti.lesson.application.port.in.GetFrequencyOfLessonsQuery;
import com.noti.noti.lesson.application.port.out.FrequencyOfLessons;
import com.noti.noti.lesson.application.port.out.FrequencyOfLessonsPort;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetFrequencyOfLessonsService implements GetFrequencyOfLessonsQuery{

  private final FrequencyOfLessonsPort frequencyOfLessonsPort;

  @Override
  public List<DateFrequencyOfLessons> findFrequencyOfLessons(int year, int month, Long teacherId) {

    List<FrequencyOfLessons> frequencyOfLessons = frequencyOfLessonsPort.findFrequencyOfLessons(year, month, teacherId);
    List<DateFrequencyOfLessons> dateFrequencyOfLessonsList = new ArrayList<>();

    frequencyOfLessons.forEach(
        frequencyLessons -> dateFrequencyOfLessonsList
            .add(new DateFrequencyOfLessons(frequencyLessons.getDateOfLesson(), frequencyLessons.getFrequencyOfLesson())));

    return dateFrequencyOfLessonsList;
  }
}
