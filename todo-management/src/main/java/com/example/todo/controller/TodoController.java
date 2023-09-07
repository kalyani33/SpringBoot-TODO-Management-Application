package com.example.todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.TodoDto;
import com.example.todo.service.TodoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/api/todos")
public class TodoController {
	
	private TodoService todoService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<TodoDto> CreateTodo(@RequestBody TodoDto todoDto){
		TodoDto savedTodo = todoService.addTodo(todoDto);
		return new ResponseEntity<TodoDto>(savedTodo,HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("{id}")
	public ResponseEntity<TodoDto> getTodobyId(@PathVariable("id") Long todoId){
		TodoDto todo = todoService.getTodoById(todoId);
		return ResponseEntity.ok(todo);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping
	public ResponseEntity<List<TodoDto>> getAllTodos(){
		List<TodoDto> todos = todoService.getAllTodos();
		return ResponseEntity.ok(todos);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("{id}")
	public ResponseEntity<TodoDto> updateTodoById(@PathVariable("id") Long todoId,@RequestBody TodoDto todoDto){
		TodoDto updatedTodo = todoService.updateTodoById(todoId, todoDto);
		return ResponseEntity.ok(updatedTodo);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteTodoById(@PathVariable("id") Long todoId){
		todoService.deleteById(todoId);
		return ResponseEntity.ok("Todo deleted successfully...");
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PatchMapping("{id}/complete")
	public ResponseEntity<TodoDto> completeTodo(@PathVariable Long id) {
		TodoDto completedTodo = todoService.completeTodo(id);
		return ResponseEntity.ok(completedTodo);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PatchMapping("{id}/in-complete")
	public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable Long id) {
		TodoDto inCompletedTodo = todoService.inCompleteTodo(id);
		return ResponseEntity.ok(inCompletedTodo);
	}

}
