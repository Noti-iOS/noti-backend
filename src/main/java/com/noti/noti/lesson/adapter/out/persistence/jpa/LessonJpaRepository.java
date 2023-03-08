package com.noti.noti.lesson.adapter.out.persistence.jpa;

import com.noti.noti.lesson.adapter.out.persistence.jpa.model.LessonJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonJpaRepository extends JpaRepository<LessonJpaEntity, Long> {

  @Override
  boolean existsById(Long lessonId);
}
