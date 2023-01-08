package com.noti.noti.lesson.adapter.in.web.dto.response;

import com.noti.noti.lesson.application.port.in.TodaysLessonHomework;
import com.noti.noti.lesson.application.port.in.TodaysLessonHomework.HomeworkInLesson;
import com.noti.noti.lesson.application.port.in.TodaysLessonHomework.StudentInLesson;
import com.noti.noti.lesson.application.port.in.TodaysLessonHomework.TeachersLesson;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TodaysLessonHomeworkDto {

  private String teacherNickName;
  private Boolean isLessonCreated;
  private List<TeachersLessonDto> lessons = new ArrayList<>();

  public static TodaysLessonHomeworkDto from(TodaysLessonHomework todaysLessonHomework) {
    return new TodaysLessonHomeworkDto(todaysLessonHomework);
  }

  private TodaysLessonHomeworkDto(TodaysLessonHomework todaysLessonHomework) {
    this.teacherNickName = todaysLessonHomework.getTeacherNickName();
    this.isLessonCreated = todaysLessonHomework.isLessonCreated();
    todaysLessonHomework.getLessons().forEach((lessonId, teachersLesson) ->
        this.lessons.add(new TeachersLessonDto(teachersLesson)));
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class TeachersLessonDto {

    private Long lessonId;
    private String lessonName;
    private LocalTime startTime;
    private LocalTime endTime;
    private int homeworkCompletionRate;
    private List<StudentInLessonDto> students = new ArrayList<>();
    private List<HomeworkInLessonDto> homeworks = new ArrayList<>();

    private TeachersLessonDto(TeachersLesson teachersLesson) {
      this.lessonId = teachersLesson.getLessonId();
      this.lessonName = teachersLesson.getLessonName();
      this.startTime = teachersLesson.getStartTime();
      this.endTime = teachersLesson.getEndTime();
      teachersLesson.getHomeworks().forEach(((homeworkId, homeworkInLesson) ->
          this.homeworks.add(new HomeworkInLessonDto(homeworkInLesson))));
      teachersLesson.getStudents().forEach((studendId, studentInLesson) ->
          this.students.add(new StudentInLessonDto(studentInLesson, this.homeworks.size())));
      this.homeworkCompletionRate = calculateHomeworkCompletionRate();
    }

    private int calculateHomeworkCompletionRate() {
      if (this.homeworks.isEmpty()) {
        return 100;
      }
      int totalCount = this.homeworks.size() * this.students.size();

      int completedCount = 0;
      for (HomeworkInLessonDto homeworkInLessonDto : this.homeworks) {
        completedCount += homeworkInLessonDto.numberOfCompletions;
      }
      return completedCount * 100 / totalCount;
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class StudentInLessonDto {

    private Long studentId;
    private String studentNickname;
    private boolean focusStatus;

    // "NONE", "IN_PROGRESS", "COMPLETION"
    private String homeworkProgressStatus;

    private StudentInLessonDto(StudentInLesson studentInLesson, int totalHomeworkCount) {
      this.studentId = studentInLesson.getStudentId();
      this.studentNickname = studentInLesson.getStudentNickname();
      this.focusStatus = studentInLesson.isFocusStatus();
      this.homeworkProgressStatus = chooseStatus(studentInLesson.getHomeworkProgressCount(),
          totalHomeworkCount);
    }

    private String chooseStatus(int completedHomework, int totalHomworkCount) {
      if (completedHomework == 0) {
        return "NONE";
      }

      if (completedHomework > 0 && completedHomework < totalHomworkCount) {
        return "IN_PROGRESS";
      } else {
        return "COMPLETION";
      }
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class HomeworkInLessonDto {

    private Long homeworkId;
    private String homeworkName;
    private int numberOfStudents;
    private int numberOfCompletions;

    private HomeworkInLessonDto(HomeworkInLesson homework) {
      this.homeworkId = homework.getHomeworkId();
      this.homeworkName = homework.getHomeworkName();
      this.numberOfStudents = homework.getNumberOfStudents();
      this.numberOfCompletions = homework.getNumberOfCompletions();
    }
  }

}
