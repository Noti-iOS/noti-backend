package com.noti.noti.studenthomework.adapter.out.persistence;

import com.noti.noti.studenthomework.adapter.out.persistence.jpa.StudentHomeworkJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentHomeworkAdapter {

  private final StudentHomeworkJpaRepository studentHomeworkJpaRepository;
}
