package com.polixis.task1.processor;

import com.polixis.task1.entity.Employee;
import lombok.NonNull;
import org.springframework.batch.item.ItemProcessor;

/**
 * @author Hovhannes Gevorgyan on 12.10.2022
 */
public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {

  @Override
  public Employee process(@NonNull Employee employee) {
    return employee;
  }
}
