package com.noti.noti.lesson.application.port.in;

import com.noti.noti.lesson.domain.model.Lesson;
import java.util.function.Function;

public interface AddLessonUsecase extends Function<AddLessonCommand, Lesson> {

}
