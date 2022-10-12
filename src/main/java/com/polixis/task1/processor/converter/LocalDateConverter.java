package com.polixis.task1.processor.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

/**
 * @author Hovhannes Gevorgyan on 12.10.2022
 */
public class LocalDateConverter implements Converter<String, LocalDate> {

  @Override
  public LocalDate convert(@NonNull String source) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return LocalDate.parse(source, formatter);
  }
}
