package com.noti.noti.book.adapter.out.persistence.jpa.model;

import com.noti.noti.common.adapter.out.persistance.jpa.model.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "book_id")
  private Long id;

  @Column
  private String title;

  @Builder
  public BookJpaEntity(Long id, String title) {
    this.id = id;
    this.title = title;
  }
}
