package com.polixis.task1.processor;

import com.polixis.task1.dto.CreateEmployeeDto;
import com.polixis.task1.entity.Employee;
import com.polixis.task1.processor.converter.LocalDateConverter;
import lombok.NonNull;
import org.springframework.batch.item.ItemProcessor;

/**
 * @author Hovhannes Gevorgyan on 12.10.2022
 */
public class EmployeeProcessor implements ItemProcessor<CreateEmployeeDto, Employee> {

  @Override
  public Employee process(@NonNull CreateEmployeeDto dto) {
    return Employee.builder()
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .date(new LocalDateConverter().convert(dto.getDate()))
        .build();
  }
}
