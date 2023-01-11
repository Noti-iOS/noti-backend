package com.noti.noti.homework.adapter.out.persistence.jpa;

import com.noti.noti.homework.adapter.in.web.dto.FrequencyOfLessonsDto;
import com.noti.noti.homework.adapter.out.persistence.jpa.model.HomeworkJpaEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HomeworkJpaRepository extends JpaRepository<HomeworkJpaEntity, Long> {

}
