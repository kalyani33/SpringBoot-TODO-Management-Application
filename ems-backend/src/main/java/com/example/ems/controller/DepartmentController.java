package com.example.ems.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ems.dto.DepartmentDto;
import com.example.ems.service.DepartmentService;

import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
	
	private DepartmentService departmentService;
	
	@PostMapping
	public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto){
		DepartmentDto savedDepartment = departmentService.createDepartment(departmentDto);
		return new ResponseEntity<DepartmentDto>(savedDepartment, HttpStatus.CREATED);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("id") Long departmentId){
		DepartmentDto department = departmentService.getDepartmentById(departmentId);
		return ResponseEntity.ok(department);
	}
	
	@GetMapping
	public ResponseEntity<List<DepartmentDto>> getAllEmployees(){
		List<DepartmentDto> allDepartments = departmentService.getAllDepartments();
		return ResponseEntity.ok(allDepartments);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable("id") Long departmentId,@RequestBody DepartmentDto departmentDto){
		DepartmentDto updatedDepartment = departmentService.updateDepartment(departmentId, departmentDto);
		return ResponseEntity.ok(updatedDepartment);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteDepartment(@PathVariable("id") Long departmentId){
		departmentService.deleteDepartment(departmentId);
		return ResponseEntity.ok("Department Deleted Successfully");
	}

}
