package com.noti.noti.homework.adapter.out.persistence;

import com.noti.noti.homework.adapter.out.persistence.jpa.HomeworkJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HomeworkPersistenceAdapter {

  private final HomeworkMapper homeworkMapper;
  private final HomeworkJpaRepository homeworkJpaRepository;

}
