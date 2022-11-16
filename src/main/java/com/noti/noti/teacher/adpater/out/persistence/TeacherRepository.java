package com.noti.noti.teacher.adpater.out.persistence;

import com.noti.noti.teacher.domain.SocialType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherJpaEntity, Long> {


  Optional<TeacherJpaEntity> findById(Long aLong);

  Optional<TeacherJpaEntity> findBySocialId(Long social);

  Optional<TeacherJpaEntity> findBySocialTypeAndSocialId(SocialType socialType, Long social);

  @Override
  <S extends TeacherJpaEntity> S save(S entity);
}
