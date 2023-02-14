package com.noti.noti.book.adapter.out.persistence.jpa;

import com.noti.noti.book.adapter.out.persistence.jpa.model.BookJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<BookJpaEntity, Long> {

  Optional<BookJpaEntity> findByTitle(String title);
}
