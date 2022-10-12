package com.polixis.task1.service.impl;

import com.polixis.task1.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

/**
 * @author Hovhannes Gevorgyan on 12.10.2022
 */
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final JobLauncher jobLauncher;
  private final Job job;

  @Override
  public void importCsvToDB() {
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
  }
}
