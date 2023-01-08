package com.noti.noti.homework.application.port.out;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodaysHomework {

  private Long lessonId;
  private Long homeworkId;
  private String homeworkName;
  private String content;
  private List<HomeworkOfStudent> students = new ArrayList<>();

  public TodaysHomework(Long lessonId, Long homeworkId, String homeworkName, String content,
      List<HomeworkOfStudent> students) {
    this.lessonId = lessonId;
    this.homeworkId = homeworkId;
    this.homeworkName = homeworkName;
    this.content = content;
    this.students = students;
  }

  @Override
  public String toString() {
    return "TodaysHomework{" +
        "lessonId=" + lessonId +
        ", homeworkId=" + homeworkId +
        ", homeworkName='" + homeworkName + '\'' +
        ", content='" + content + '\'' +
        ", students=" + students +
        '}';
  }

  @Getter
  @NoArgsConstructor
  public static class HomeworkOfStudent {

    private Long studentId;
    private boolean homeworkStatus;

    @Override
    public String toString() {
      return "HomeworkOfStudent{" +
          "studentId=" + studentId +
          ", homeworkStatus=" + homeworkStatus +
          '}';
    }

    public HomeworkOfStudent(Long studentId, boolean homeworkStatus) {
      this.studentId = studentId;
      this.homeworkStatus = homeworkStatus;
    }
  }
}
