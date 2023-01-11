package com.noti.noti.lesson.adapter.in.web.controller;

import com.noti.noti.homework.adapter.in.web.dto.FrequencyOfLessonsDto;
import com.noti.noti.homework.adapter.out.persistence.HomeworkPersistenceAdapter;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class GetFrequencyOfLessonsController {

  private HomeworkPersistenceAdapter homeworkPersistenceAdapter;

  @GetMapping("/api/teacher/calendar")
  public ResponseEntity<List<FrequencyOfLessonsDto>> getFrequencyOfLessons(String monthYear) {
    List<FrequencyOfLessonsDto> frequencyOfLessons = homeworkPersistenceAdapter.findFrequencyOfLessons(monthYear);
    return ResponseEntity.ok(frequencyOfLessons);
  }





}
