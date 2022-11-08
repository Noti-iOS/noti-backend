package com.noti.noti.lessonbook.adapter.out.persistence.jpa;

import com.noti.noti.lessonbook.adapter.out.persistence.jpa.model.LessonBookJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonBookJpaRepository extends JpaRepository<LessonBookJpaEntity, Long> {

}
