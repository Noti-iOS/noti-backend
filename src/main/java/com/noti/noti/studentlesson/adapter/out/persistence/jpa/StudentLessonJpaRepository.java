package com.noti.noti.studentlesson.adapter.out.persistence.jpa;

import com.noti.noti.studentlesson.adapter.out.persistence.jpa.model.StudentLessonJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentLessonJpaRepository extends JpaRepository<StudentLessonJpaEntity, Long> {

}
