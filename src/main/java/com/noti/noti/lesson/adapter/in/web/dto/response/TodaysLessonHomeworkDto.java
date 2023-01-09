package com.noti.noti.lesson.adapter.in.web.dto.response;

import com.noti.noti.lesson.application.port.in.TodaysLessonHomework;
import com.noti.noti.lesson.application.port.in.TodaysLessonHomework.HomeworkInLesson;
import com.noti.noti.lesson.application.port.in.TodaysLessonHomework.StudentInLesson;
import com.noti.noti.lesson.application.port.in.TodaysLessonHomework.TeachersLesson;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TodaysLessonHomeworkDto {

  @Schema(description = "선생님 닉네임", example = "Teacher A")
  private String teacherNickName;
  @Schema(description = "지금까지 수업 생성 여부", example = "true")
  private Boolean isLessonCreated;
  @Schema(description = "오늘의 수업 목록")
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


    @Schema(description = "수업 ID", example = "1")
    private Long lessonId;
    @Schema(description = "수업 이름", example = "수학 A")
    private String lessonName;
    @Schema(description = "수업시작 시간", example = "13:00")
    private LocalTime startTime;
    @Schema(description = "수업종료 시간", example = "15:00")
    private LocalTime endTime;
    @Schema(description = "숙제 완료 비율", example = "33")
    private int homeworkCompletionRate;
    @Schema(description = "수업의 학생 목록")
    private List<StudentInLessonDto> students = new ArrayList<>();
    @Schema(description = "수업의 숙제 목록")
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

    @Schema(description = "학생 ID", example = "1")
    private Long studentId;
    @Schema(description = "학생 닉네임", example = "학생1")
    private String studentNickname;
    @Schema(description = "집중관리 상태 여부", example = "false")
    private boolean focusStatus;
    @Schema(description = "학생 프로필 이미지 url", example = "image.png")
    private String profileImage;

    // "NONE", "IN_PROGRESS", "COMPLETION"
    @Schema(description = "학생 숙제 진행 상태", example = "NONE, IN_PROGRESS, COMPLETION")
    private String homeworkProgressStatus;

    private StudentInLessonDto(StudentInLesson studentInLesson, int totalHomeworkCount) {
      this.studentId = studentInLesson.getStudentId();
      this.studentNickname = studentInLesson.getStudentNickname();
      this.focusStatus = studentInLesson.isFocusStatus();
      this.profileImage = studentInLesson.getProfileImage();
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

    @Schema(description = "숙제 ID", example = "1")
    private Long homeworkId;
    @Schema(description = "숙제 명", example = "수학의 정석")
    private String homeworkName;

    @Schema(description = "숙제 내용", example = "p.1")
    private String content;
    @Schema(description = "숙제의 해당하는 학생 수", example = "10")
    private int numberOfStudents;
    @Schema(description = "숙제를 완료한 학생 수", example = "3")
    private int numberOfCompletions;

    private HomeworkInLessonDto(HomeworkInLesson homework) {
      this.homeworkId = homework.getHomeworkId();
      this.homeworkName = homework.getHomeworkName();
      this.content = homework.getContent();
      this.numberOfStudents = homework.getNumberOfStudents();
      this.numberOfCompletions = homework.getNumberOfCompletions();
    }
  }

}
