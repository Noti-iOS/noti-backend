package com.noti.noti.studenthomework.adapter.out.persistence.jpa;

import com.noti.noti.studenthomework.adapter.out.persistence.jpa.model.StudentHomeworkJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentHomeworkJpaRepository extends JpaRepository<StudentHomeworkJpaEntity, Long> {

}
