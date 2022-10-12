package com.polixis.task1.config;

import static com.polixis.task1.config.properties.SpringBatchConfigProperties.*;
import static org.springframework.batch.item.file.transform.DelimitedLineTokenizer.DELIMITER_COMMA;

import com.polixis.task1.dto.CreateEmployeeDto;
import com.polixis.task1.entity.Employee;
import com.polixis.task1.processor.EmployeeProcessor;
import com.polixis.task1.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

/**
 * @author Hovhannes Gevorgyan on 12.10.2022
 */
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class SpringBatchConfig {

  @Value("${files.path}")
  private Resource[] resources;
  private final EmployeeRepository infoRepository;
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean
  public MultiResourceItemReader<CreateEmployeeDto> multiResourceItemReader() {
    MultiResourceItemReader<CreateEmployeeDto> resourceItemReader = new MultiResourceItemReader<>();
    resourceItemReader.setDelegate(reader());
    resourceItemReader.setResources(resources);
    return resourceItemReader;
  }

  @Bean
  public FlatFileItemReader<CreateEmployeeDto> reader() {
    FlatFileItemReader<CreateEmployeeDto> reader = new FlatFileItemReader<>();
    reader.setLineMapper(lineMapper());
    return reader;
  }

  @Bean
  public RepositoryItemWriter<Employee> writer() {
    RepositoryItemWriter<Employee> writer = new RepositoryItemWriter<>();
    writer.setRepository(infoRepository);
    writer.setMethodName(REPOSITORY_METHOD_NAME);
    return writer;
  }

  @Bean
  public Job job() {
    return jobBuilderFactory.get(JOB).flow(processDataFromCsv()).end().build();
  }

  @Bean
  public Step processDataFromCsv() {
    return stepBuilderFactory
        .get(PROCESS_DATA)
        .<CreateEmployeeDto, Employee>chunk(CHUNK_SIZE)
        .reader(multiResourceItemReader())
        .processor(processor())
        .writer(writer())
        .taskExecutor(taskExecutor())
        .build();
  }

  @Bean
  public EmployeeProcessor processor() {
    return new EmployeeProcessor();
  }

  @Bean
  public TaskExecutor taskExecutor() {
    SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
    asyncTaskExecutor.setConcurrencyLimit(CONCURRENCY_LIMIT);
    return asyncTaskExecutor;
  }

  private LineMapper<CreateEmployeeDto> lineMapper() {
    DefaultLineMapper<CreateEmployeeDto> lineMapper = new DefaultLineMapper<>();

    DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
    delimitedLineTokenizer.setStrict(false);
    delimitedLineTokenizer.setNames(HEADERS);
    delimitedLineTokenizer.setDelimiter(DELIMITER_COMMA);

    BeanWrapperFieldSetMapper<CreateEmployeeDto> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
    fieldSetMapper.setTargetType(CreateEmployeeDto.class);

    lineMapper.setLineTokenizer(delimitedLineTokenizer);
    lineMapper.setFieldSetMapper(fieldSetMapper);
    return lineMapper;
  }
}
