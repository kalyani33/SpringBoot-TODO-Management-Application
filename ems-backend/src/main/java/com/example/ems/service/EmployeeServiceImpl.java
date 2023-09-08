package com.example.ems.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.ems.dto.EmployeeDto;
import com.example.ems.entity.Department;
import com.example.ems.entity.Employee;
import com.example.ems.exception.ResourceNotFoundException;
import com.example.ems.mapper.EmployeeMapper;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.repository.EmployeeRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	private EmployeeRepository employeeRepository;
	
	private DepartmentRepository departmentRepository;

	@Override
	public EmployeeDto createEmployee(EmployeeDto employeedto) {
		Employee employee = EmployeeMapper.mapToEmployee(employeedto);
		Department department = departmentRepository.findById(employeedto.getDepartmentId()).orElseThrow(()->
			new ResourceNotFoundException("Department ID doesn't exist with  given id: "+employeedto.getId()));
		employee.setDepartment(department);
		Employee savedEmployee = employeeRepository.save(employee);
		return EmployeeMapper.mapToEmployeeDto(savedEmployee);
	}

	@Override
	public EmployeeDto getEmployeeById(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> 
				new ResourceNotFoundException("Employee is not exist with the given id : "+employeeId));
		return EmployeeMapper.mapToEmployeeDto(employee);
	}

	@Override
	public List<EmployeeDto> AllEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		return employees.stream().map(employee -> EmployeeMapper.mapToEmployeeDto(employee))
				.collect(Collectors.toList());
	}

	@Override
	public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updateEmployeeDto) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee doesn't exist with the given id: "+employeeId));
		employee.setFirstName(updateEmployeeDto.getFirstName());
		employee.setLastName(updateEmployeeDto.getLastName());
		employee.setEmail(updateEmployeeDto.getEmail());
		Department department = departmentRepository.findById(updateEmployeeDto.getDepartmentId()).orElseThrow(()->
		new ResourceNotFoundException("Department ID doesn't exist with  given id: "+updateEmployeeDto.getId()));
		employee.setDepartment(department);
		Employee updatedEmployeeObj = employeeRepository.save(employee);
		
		return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
	}

	@Override
	public void deleteEmployee(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee doesn't exist with the given id: "+employeeId));
		
		employeeRepository.delete(employee);
		
	}

}
