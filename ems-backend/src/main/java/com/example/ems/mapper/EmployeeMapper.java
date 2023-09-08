package com.example.ems.mapper;

import com.example.ems.dto.EmployeeDto;
import com.example.ems.entity.Employee;

public class EmployeeMapper {
	public static Employee mapToEmployee(EmployeeDto employeedto) {
		Employee employee = new Employee();
		employee.setId(employeedto.getId());
		employee.setFirstName(employeedto.getFirstName());
		employee.setLastName(employeedto.getLastName());
		employee.setEmail(employeedto.getEmail());
		return employee;
				
	}

public static EmployeeDto mapToEmployeeDto(Employee employee) {
	
	return new EmployeeDto(
			employee.getId(),
			employee.getFirstName(),
			employee.getLastName(),
			employee.getEmail(),
			employee.getDepartment().getId()
			);
}

}
