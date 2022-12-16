package com.noti.noti.homework.adapter.out.persistence.jpa.model;

import com.noti.noti.book.adapter.out.persistence.jpa.model.BookJpaEntity;
import com.noti.noti.common.adapter.out.persistance.jpa.model.BaseTimeEntity;
import com.noti.noti.lesson.adapter.out.persistence.jpa.model.LessonJpaEntity;
import java.time.LocalDateTime;
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
@Table(name = "homework")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class HomeworkJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "homework_id")
  private Long id;

  @Column
  private String homeworkName;

  @Column
  private String content;

  @Column
  private LocalDateTime startTime;

  @Column
  private LocalDateTime endTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_id")
  private LessonJpaEntity lessonJpaEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id")
  private BookJpaEntity bookJpaEntity;

  @Builder
  public HomeworkJpaEntity(Long id, String homeworkName, String content, LocalDateTime startTime,
      LocalDateTime endTime, LessonJpaEntity lessonJpaEntity, BookJpaEntity bookJpaEntity) {
    this.id = id;
    this.homeworkName = homeworkName;
    this.content = content;
    this.startTime = startTime;
    this.endTime = endTime;
    this.lessonJpaEntity = lessonJpaEntity;
    this.bookJpaEntity = bookJpaEntity;
  }
}
