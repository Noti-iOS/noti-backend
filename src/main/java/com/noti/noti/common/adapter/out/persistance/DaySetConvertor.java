package com.noti.noti.common.adapter.out.persistance;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DaySetConvertor {

  public String convertToDatabaseColumn(Set<DayOfWeek> attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.stream()
        .map(day -> day.toString())
        .collect(Collectors.joining(","));
  }

  public Set<DayOfWeek> convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    String[] days = dbData.split(",");
    return Arrays.stream(days)
        .map(value -> DayOfWeek.valueOf(value))
        .collect(Collectors.toSet());
  }
}
