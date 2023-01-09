package com.noti.noti.lesson.application.port.in;

import com.noti.noti.homework.application.port.out.TodaysHomework;
import com.noti.noti.homework.application.port.out.TodaysHomework.HomeworkOfStudent;
import com.noti.noti.lesson.application.port.out.TodaysLesson;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import lombok.Getter;

@Getter
public class TodaysLessonHomework {

  private final LocalDateTime now;
  private String teacherNickName;
  private boolean isLessonCreated;
  private HashMap<Long, TeachersLesson> lessons = new LinkedHashMap<>();

  public TodaysLessonHomework(String teacherNickName, LocalDateTime now) {
    this.now = now;
    this.teacherNickName = teacherNickName;
  }

  public void changeLessonCreatedStatus() {
    isLessonCreated = true;
  }

  public void addLesson(TodaysLesson todayslesson) {
    this.lessons.put(todayslesson.getLessonId(), new TeachersLesson(todayslesson));
  }

  public void addHomework(TodaysHomework todaysHomework) {
    TeachersLesson teachersLesson = this.lessons.get(todaysHomework.getLessonId());
    teachersLesson.homeworks
        .put(todaysHomework.getHomeworkId(), new HomeworkInLesson(todaysHomework));

    teachersLesson.calculateHomeworkCompletion(todaysHomework.getStudents());
  }

  @Getter
  public class TeachersLesson {

    private Long lessonId;
    private String lessonName;
    private LocalTime startTime;
    private LocalTime endTime;
    private HashMap<Long, StudentInLesson> students = new LinkedHashMap<>();
    private HashMap<Long, HomeworkInLesson> homeworks = new LinkedHashMap<>();

    private TeachersLesson(TodaysLesson todaysLesson) {
      this.lessonId = todaysLesson.getLessonId();
      this.lessonName = todaysLesson.getLessonName();
      this.startTime = todaysLesson.getStartTime();
      this.endTime = todaysLesson.getEndTime();

      todaysLesson.getStudents()
          .forEach(s -> this.students.put(s.getStudentId(),
              new StudentInLesson(s.getStudentId(), s.getStudentNickname(), s.getProfileImage(),
                  s.isFocusStatus())));
    }

    private void calculateHomeworkCompletion(List<HomeworkOfStudent> students) {
      students.forEach(s -> {
        if (s.isHomeworkStatus()) {
          this.students.get(s.getStudentId()).increaseHomeworkProgressCount();
        }
      });
    }
  }

  @Getter
  public class StudentInLesson {

    private Long studentId;
    private String studentNickname;
    private String profileImage;
    private boolean focusStatus;

    private int homeworkProgressCount = 0;

    private void increaseHomeworkProgressCount() {
      this.homeworkProgressCount++;
    }

    public StudentInLesson(Long studentId, String studentNickname, String profileImage,
        boolean focusStatus) {
      this.studentId = studentId;
      this.studentNickname = studentNickname;
      this.profileImage = profileImage;
      this.focusStatus = focusStatus;
    }
  }

  @Getter
  public class HomeworkInLesson {

    private Long homeworkId;
    private String homeworkName;
    private int numberOfStudents;
    private int numberOfCompletions = 0;

    private HomeworkInLesson(TodaysHomework todaysHomework) {
      this.homeworkId = todaysHomework.getHomeworkId();
      this.homeworkName = todaysHomework.getHomeworkName();
      this.numberOfStudents = todaysHomework.getStudents().size();
      todaysHomework.getStudents().forEach(this::calNumberOfCompletions);
    }

    private void calNumberOfCompletions(HomeworkOfStudent homeworkOfStudent) {
      if (homeworkOfStudent.isHomeworkStatus()) {
        increaseNumberOfCompletions();
      }
    }

    private void increaseNumberOfCompletions() {
      this.numberOfCompletions++;
    }
  }
}
