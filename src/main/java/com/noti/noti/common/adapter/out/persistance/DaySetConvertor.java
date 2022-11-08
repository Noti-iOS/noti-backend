package com.noti.noti.common.adapter.out.persistance;

import com.noti.noti.common.domain.model.Day;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DaySetConvertor implements AttributeConverter<Set<Day>, String> {

  @Override
  public String convertToDatabaseColumn(Set<Day> attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.stream()
        .map(day -> day.toString())
        .collect(Collectors.joining(","));
  }

  @Override
  public Set<Day> convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    String[] days = dbData.split(",");
    return Arrays.stream(days)
        .map(value -> Day.valueOf(value))
        .collect(Collectors.toSet());
  }
}
