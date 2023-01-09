package com.noti.noti.teacher.adpater.out.persistence;

import static com.noti.noti.teacher.adpater.out.persistence.QTeacherJpaEntity.teacherJpaEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Slf4j
public class TeacherQueryRepository {

  private final JPAQueryFactory queryFactory;

  public String findTeacherNickname(Long teacherId) {
    return queryFactory
        .select(teacherJpaEntity.nickname)
        .from(teacherJpaEntity)
        .where(eqId(teacherId))
        .fetchOne();
  }

  private BooleanExpression eqId(Long teacherId) {
    log.info("teacher Id: {} ", teacherId);
    return teacherId != null ? teacherJpaEntity.id.eq(teacherId) : null;
  }
}
