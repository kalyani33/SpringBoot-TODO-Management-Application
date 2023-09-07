package com.example.todo.utils;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;

public class TodoMapper {
	
	//convert TodoDto into Todo
	public static Todo maptoTodo(TodoDto todoDto) {
		Todo todo = new Todo();
		todo.setTitle(todoDto.getTitle());
		todo.setDescription(todoDto.getDescription());
		todo.setCompleted(todoDto.isCompleted());
		return  todo;
	}
	
	//convert Todo into TodoDto
	public static TodoDto maptoTodoDto(Todo todo) {
		TodoDto todoDto = new TodoDto();
		todoDto.setId(todo.getId());
		todoDto.setTitle(todo.getTitle());
		todoDto.setDescription(todo.getDescription());
		todoDto.setCompleted(todo.isCompleted());
		return todoDto;
	}

}
