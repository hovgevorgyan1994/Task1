package com.polixis.task1.repository;

import com.polixis.task1.entity.Employee;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Hovhannes Gevorgyan on 12.10.2022
 */
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {}
