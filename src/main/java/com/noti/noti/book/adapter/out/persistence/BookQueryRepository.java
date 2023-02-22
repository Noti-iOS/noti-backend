package com.noti.noti.book.adapter.out.persistence;

import static com.noti.noti.book.adapter.out.persistence.jpa.model.QBookJpaEntity.bookJpaEntity;

import com.noti.noti.book.application.port.out.BookDto;
import com.noti.noti.book.application.port.out.FindBookCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class BookQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  boolean isExistedBook(FindBookCondition condition) {
    Long result = jpaQueryFactory
        .select(bookJpaEntity.id)
        .from(bookJpaEntity)
        .where(eqTitle(condition.getTitle()), eqTeacherId(condition.getTeacherId()))
        .fetchOne();

    return result != null;
  }

  /**
   * teacerId에 해당하는 모든 교재 조회
   *
   * @param teacherId
   * @return
   */
  List<BookDto> findAllBooksByTeacherId(Long teacherId) {
    return jpaQueryFactory
        .select(Projections.fields(BookDto.class, bookJpaEntity.id, bookJpaEntity.title))
        .from(bookJpaEntity)
        .where(eqTeacherId(teacherId))
        .fetch();
  }

  private BooleanExpression eqTitle(String title) {
    return StringUtils.hasText(title) ? bookJpaEntity.title.eq(title) : null;
  }

  private BooleanExpression eqTeacherId(Long id) {
    return Objects.isNull(id) ? null : bookJpaEntity.teacherJpaEntity.id.eq(id);
  }
}
