package com.noti.noti.homework.adapter.out.persistence;

import com.noti.noti.homework.adapter.out.persistence.jpa.HomeworkJpaRepository;
import com.noti.noti.homework.adapter.out.persistence.jpa.model.HomeworkJpaEntity;
import com.noti.noti.homework.application.port.out.FindFilteredHomeworkPort;
import com.noti.noti.homework.application.port.out.FindHomeworkContentPort;
import com.noti.noti.homework.application.port.out.FindSearchedHomeworkPort;
import com.noti.noti.homework.application.port.out.FindTodaysHomeworkPort;
import com.noti.noti.homework.application.port.out.SaveDeadlineAlarmPort;
import com.noti.noti.homework.application.port.out.SaveHomeworkPort;
import com.noti.noti.homework.application.port.out.OutFilteredHomeworkFrequency;
import com.noti.noti.homework.application.port.out.OutHomeworkContent;
import com.noti.noti.homework.application.port.out.SearchedHomework;
import com.noti.noti.homework.application.port.out.TodayHomeworkCondition;
import com.noti.noti.homework.application.port.out.TodaysHomework;
import com.noti.noti.homework.domain.model.Homework;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeworkPersistenceAdapter implements FindTodaysHomeworkPort, SaveHomeworkPort,
    SaveDeadlineAlarmPort, FindFilteredHomeworkPort, FindHomeworkContentPort,
    FindSearchedHomeworkPort {

  private final HomeworkMapper homeworkMapper;
  private final HomeworkJpaRepository homeworkJpaRepository;
  private final HomeworkQueryRepository homeworkQueryRepository;
  private final StringRedisTemplate redisTemplate;


  @Override
  public List<TodaysHomework> findTodaysHomeworks(TodayHomeworkCondition condition) {
    return homeworkQueryRepository.findTodayHomeworks(condition);
  }

  @Transactional
  @Override
  public List<Long> saveAllHomeworks(List<Homework> homeworks) {
    List<HomeworkJpaEntity> homeworkJpaEntities = homeworks.stream()
        .map(homeworkMapper::mapToJpaEntity)
        .collect(Collectors.toList());

    return homeworkJpaRepository.saveAll(homeworkJpaEntities)
        .stream()
        .map(HomeworkJpaEntity::getId)
        .collect(Collectors.toList());
  }

  @Override
  public void saveDeadlineAlarm(Long homeworkId, LocalDateTime deadlineAlarmTime) {
    final String homeworkDeadlineAlarmKey = "homeworkDeadlineAlarm:" + homeworkId;
    ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
    valueOperations.set(homeworkDeadlineAlarmKey, homeworkId.toString());
    redisTemplate.expireAt(homeworkDeadlineAlarmKey, Timestamp.valueOf(deadlineAlarmTime));
  }

//  public void tempMethod(final List<Homework> homeworks) {
//    final String QUERY =
//        "insert into teacher "
//            + "(email, nickname, profile, role, social_id, social_type) "
//            + "values (1, 1, 1, ?, 123, ?)";
//
//    jdbcTemplate.batchUpdate(QUERY, homeworks, 50,
//        (PreparedStatement ps, Homework homework) -> {
//          ps.setString(1, "ROLE_TEACHER");
//          ps.setString(2, "KAKAO");
//        }
//    );
//  }
  @Override
  public List<OutFilteredHomeworkFrequency> findFilteredHomeworks(LocalDateTime startOfMonth,
      Long lessonId, Long teacherId) {
    return homeworkQueryRepository.findFilteredHomeworkFrequency(
        teacherId, lessonId, startOfMonth, startOfMonth.plusMonths(1).minusSeconds(1));
  }

  @Override
  public List<OutHomeworkContent> findHomeworkContents(Long lessonId, LocalDateTime startOfMonth) {

    return homeworkQueryRepository.findHomeworkContents(lessonId, startOfMonth);
  }

  @Override
  public List<SearchedHomework> findSearchedHomeworks(Long teacherId, String keyword, int size, String cursorId) {
    return homeworkQueryRepository.findSearchedHomework(teacherId, keyword, size, cursorId);
  }
}
