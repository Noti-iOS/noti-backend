package com.noti.noti.teacher.adpater.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherJpaEntity, Long> {


  Optional<TeacherJpaEntity> findByUsername(Long aLong);

  Optional<TeacherJpaEntity> findBySocialId(Long social);

  @Override
  <S extends TeacherJpaEntity> S save(S entity);
}
