package com.noti.noti.studenthomework.adapter.out.persistence;

import com.noti.noti.studenthomework.adapter.out.persistence.jpa.StudentHomeworkJpaRepository;
import com.noti.noti.studenthomework.application.port.out.FindHomeworksOfCalendarPort;
import com.noti.noti.studenthomework.application.port.out.OutHomeworkOfGivenDate;
import com.noti.noti.studenthomework.application.port.out.SaveStudentHomeworkPort;
import com.noti.noti.studenthomework.domain.model.StudentHomework;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentHomeworkAdapter implements FindHomeworksOfCalendarPort,
    SaveStudentHomeworkPort {

  private final StudentHomeworkJpaRepository studentHomeworkJpaRepository;
  private final StudentHomeworkQueryRepository studentHomeworkQueryRepository;
  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<OutHomeworkOfGivenDate> findHomeworksOfCalendar(LocalDate date, Long teacherId) {
    return studentHomeworkQueryRepository.findHomeworkOfCalendar(date.atStartOfDay(), teacherId);
  }

  @Override
  public void saveAllStudentHomeworks(List<StudentHomework> studentHomeworks) {
    final String QUERY =
        "insert into student_homework "
            + "(created_at, modified_at, homework_status, homework_id, student_id) "
            + "values (now(), now(), false, ?, ?)";

    jdbcTemplate.batchUpdate(QUERY, studentHomeworks, 50,
        (PreparedStatement ps, StudentHomework studentHomework) -> {
          ps.setLong(1, studentHomework.getHomework().getId());
          ps.setLong(2, studentHomework.getStudent().getId());
        }
    );
  }
}
