package com.example.ems.service;

import java.util.List;

import com.example.ems.dto.EmployeeDto;

public interface EmployeeService {
	EmployeeDto createEmployee(EmployeeDto employeedto);
	EmployeeDto getEmployeeById(Long employeeId);
	List<EmployeeDto> AllEmployees();
	EmployeeDto updateEmployee(Long employeeId,EmployeeDto updateEmployeeDto);
	void deleteEmployee(Long employeeId);

}
