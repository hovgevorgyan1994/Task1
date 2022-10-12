package com.polixis.task1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hovhannes Gevorgyan on 12.10.2022
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeDto {

  private String firstName;
  private String lastName;
  private String date;
}
