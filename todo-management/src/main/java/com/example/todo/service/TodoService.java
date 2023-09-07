package com.example.todo.service;

import java.util.List;

import com.example.todo.dto.TodoDto;

public interface TodoService {
	TodoDto addTodo(TodoDto todoDto);
	TodoDto getTodoById(Long todoId);
	List<TodoDto> getAllTodos();
	TodoDto updateTodoById(Long todoId,TodoDto todoDto);
	void deleteById(Long todoId);
	TodoDto completeTodo(Long id);
	TodoDto inCompleteTodo(Long id);
	

}
