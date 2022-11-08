package com.noti.noti.lessonbook.adapter.out.persistence;

import com.noti.noti.lessonbook.adapter.out.persistence.jpa.LessonBookJpaRepository;
import com.noti.noti.lessonbook.adapter.out.persistence.jpa.model.LessonBookJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LessonBookPersistenceAdapter {

  private final LessonBookJpaRepository lessonBookJpaRepository;
  private final LessonBookMapper lessonBookMapper;


}
