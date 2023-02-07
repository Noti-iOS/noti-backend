package com.noti.noti.lessonbook.adapter.out.persistence;

import com.noti.noti.lessonbook.adapter.out.persistence.jpa.LessonBookJpaRepository;
import com.noti.noti.lessonbook.adapter.out.persistence.jpa.model.LessonBookJpaEntity;
import com.noti.noti.lessonbook.application.port.out.SaveLessonBookPort;
import com.noti.noti.lessonbook.domain.model.LessonBook;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LessonBookPersistenceAdapter implements SaveLessonBookPort {

  private final LessonBookJpaRepository lessonBookJpaRepository;
  private final LessonBookMapper lessonBookMapper;


  @Override
  public List<LessonBook> saveAllLessonBooks(List<LessonBook> lessonBooks) {
    List<LessonBookJpaEntity> lessonBookJpaEntities =
        lessonBooks.stream()
            .map(lessonBookMapper::mapToJpaEntity)
            .collect(Collectors.toList());

    return lessonBookJpaRepository.saveAll(lessonBookJpaEntities).stream()
        .map(lessonBookMapper::mapToDomainEntity)
        .collect(Collectors.toList());
  }

  @Override
  public LessonBook saveLessonBook(LessonBook lessonBook) {
    LessonBookJpaEntity savedLessonBook = lessonBookJpaRepository
        .save(lessonBookMapper.mapToJpaEntity(lessonBook));

    return lessonBookMapper.mapToDomainEntity(savedLessonBook);
  }

}
