package com.noti.noti.homework.adapter.out.persistence.jpa;

import com.noti.noti.homework.adapter.out.persistence.jpa.model.HomeworkJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkJpaRepository extends JpaRepository<HomeworkJpaEntity, Long> {

}
