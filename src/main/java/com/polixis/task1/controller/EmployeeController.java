package com.polixis.task1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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

  private final JobLauncher jobLauncher;
  private final Job job;

  @PostMapping
  public ResponseEntity<Void> importCsvToDB() {
    JobParameters jobParameter =
        new JobParametersBuilder().addLong("start", System.currentTimeMillis()).toJobParameters();
    try {
      jobLauncher.run(job, jobParameter);
    } catch (JobExecutionAlreadyRunningException
        | JobRestartException
        | JobInstanceAlreadyCompleteException
        | JobParametersInvalidException e) {
      throw new IllegalStateException(e);
    }
    return ResponseEntity.ok().build();
  }
}
