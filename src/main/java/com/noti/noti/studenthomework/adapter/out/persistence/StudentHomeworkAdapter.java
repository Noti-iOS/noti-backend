package com.noti.noti.studenthomework.adapter.out.persistence;

import com.noti.noti.studenthomework.adapter.out.persistence.jpa.StudentHomeworkJpaRepository;
import com.noti.noti.studenthomework.application.port.out.FindHomeworksOfCalendarPort;
import com.noti.noti.studenthomework.application.port.out.OutHomeworkOfGivenDate;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentHomeworkAdapter implements FindHomeworksOfCalendarPort {

  private final StudentHomeworkJpaRepository studentHomeworkJpaRepository;
  private final StudentHomeworkQueryRepository studentHomeworkQueryRepository;

  @Override
  public List<OutHomeworkOfGivenDate> findHomeworksOfCalendar(LocalDate date, Long teacherId) {
    return studentHomeworkQueryRepository.findHomeworkOfCalendar(date.atStartOfDay(), teacherId);
  }
}
