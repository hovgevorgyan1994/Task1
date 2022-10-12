package com.polixis.task1.controller;

import com.polixis.task1.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hovhannes Gevorgyan on 12.10.2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {

  private final EmployeeService employeeService;

  @PostMapping
  public ResponseEntity<Void> importCsvToDB() {
    employeeService.importCsvToDB();
    return ResponseEntity.ok().build();
  }
}
