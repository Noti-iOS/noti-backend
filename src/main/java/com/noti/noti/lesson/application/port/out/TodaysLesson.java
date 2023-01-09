package com.noti.noti.lesson.application.port.out;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodaysLesson {
  private Long lessonId;
  private String lessonName;
  private LocalTime startTime;
  private LocalTime endTime;

  private String days;
  private List<LessonOfStudent> students = new ArrayList<>();

  public TodaysLesson(Long lessonId, String lessonName, LocalTime startTime, LocalTime endTime,
      String days, List<LessonOfStudent> students) {
    this.lessonId = lessonId;
    this.lessonName = lessonName;
    this.startTime = startTime;
    this.endTime = endTime;
    this.days = days;
    this.students = students;
  }

  @Override
  public String toString() {
    return "TodaysLesson{" +
        "lessonId=" + lessonId +
        ", lessonName='" + lessonName + '\'' +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", days='" + days + '\'' +
        ", students=" + students +
        '}';
  }

  @Getter
  @NoArgsConstructor
  public static class LessonOfStudent {
    private Long studentId;
    private String studentNickname;
    private String profileImage;
    private boolean focusStatus;

    @Override
    public String toString() {
      return "LessonOfStudent{" +
          "studentId=" + studentId +
          ", studentNickname='" + studentNickname + '\'' +
          ", focusStatus=" + focusStatus +
          '}';
    }

    public LessonOfStudent(Long studentId, String studentNickname, String studentProfileImage,
        boolean focusStatus) {
      this.studentId = studentId;
      this.studentNickname = studentNickname;
      this.profileImage = studentProfileImage;
      this.focusStatus = focusStatus;
    }
  }
}
