package com.noti.noti.lessonbook.adapter.out.persistence.jpa.model;

import com.noti.noti.book.adapter.out.persistence.jpa.model.BookJpaEntity;
import com.noti.noti.common.adapter.out.persistance.jpa.model.BaseTimeEntity;
import com.noti.noti.lesson.adapter.out.persistence.jpa.model.LessonJpaEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "lesson_book")
public class LessonBookJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lesson_book_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_id")
  private LessonJpaEntity lessonJpaEnity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id")
  private BookJpaEntity bookJpaEntity;

  @Builder
  public LessonBookJpaEntity(Long id, LessonJpaEntity lessonJpaEnity, BookJpaEntity bookJpaEntity) {
    this.id = id;
    this.lessonJpaEnity = lessonJpaEnity;
    this.bookJpaEntity = bookJpaEntity;
  }
}
