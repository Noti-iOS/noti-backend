package com.noti.noti.student.adapter.out.persistence.jpa;

import com.noti.noti.student.adapter.out.persistence.jpa.model.StudentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentJpaRepository extends JpaRepository<StudentJpaEntity, Long> {

}
