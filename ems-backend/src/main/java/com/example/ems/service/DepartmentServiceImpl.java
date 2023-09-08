package com.example.ems.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ems.dto.DepartmentDto;
import com.example.ems.entity.Department;
import com.example.ems.exception.ResourceNotFoundException;
import com.example.ems.mapper.DepartmentMapper;
import com.example.ems.repository.DepartmentRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService{
	
	private DepartmentRepository departmentRepository;

	@Override
	public DepartmentDto createDepartment(DepartmentDto departmentDto) {
		Department department = DepartmentMapper.mapToDepartment(departmentDto);
		Department savedDepartment = departmentRepository.save(department);
		return DepartmentMapper.mapToDepartmentDto(savedDepartment);
	}

	@Override
	public DepartmentDto getDepartmentById(Long departmentId) {
		Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new ResourceNotFoundException("Department doesn't exist with the given id : "+departmentId));
		
		return DepartmentMapper.mapToDepartmentDto(department);
	}
	
	public List<DepartmentDto> getAllDepartments(){
		List<Department> allDepartments = departmentRepository.findAll();
		return allDepartments.stream().map(department -> DepartmentMapper.mapToDepartmentDto(department)).collect(Collectors.toList());
	}

	@Override
	public DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto) {
		Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new ResourceNotFoundException("Department doesn't exist with the given id : "+departmentId));
		//department.setId(departmentDto.getId());
		department.setDepartmentName(departmentDto.getDepartmentName());
		department.setDepartmentDescription(departmentDto.getDepartmentDescription());
		Department updatedDepartment = departmentRepository.save(department);
		return DepartmentMapper.mapToDepartmentDto(updatedDepartment);
	}

	@Override
	public void deleteDepartment(Long departmentId) {
		Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new ResourceNotFoundException("Department doesn't exist with the given id : "+departmentId));
		departmentRepository.deleteById(departmentId);
		
	}
	
	
	
	

	

}
