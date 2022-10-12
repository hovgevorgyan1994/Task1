package com.polixis.task1.config.properties;

import lombok.experimental.UtilityClass;

/**
 * @author Hovhannes Gevorgyan on 12.10.2022
 */
@UtilityClass
public class SpringBatchConfigProperties {

  public final String[] HEADERS = {"firstName", "lastName", "date"};
  public final String REPOSITORY_METHOD_NAME = "save";
  public final String PROCESS_DATA = "process-data";
  public final String JOB = "write-employees";
  public final int CONCURRENCY_LIMIT = 20;
  public final int CHUNK_SIZE = 250;
}
