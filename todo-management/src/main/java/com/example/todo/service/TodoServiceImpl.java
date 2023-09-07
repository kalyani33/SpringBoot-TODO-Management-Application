package com.example.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.repository.TodoRepository;
import com.example.todo.utils.TodoMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {
	
	private TodoRepository todoRepository;
	
	private ModelMapper modelMapper;

	@Override
	public TodoDto addTodo(TodoDto todoDto) {
		// TODO Auto-generated method stub
		//Todo todo = TodoMapper.maptoTodo(todoDto);
		Todo todo = modelMapper.map(todoDto, Todo.class);
		Todo savedTodo = todoRepository.save(todo);
		//return TodoMapper.maptoTodoDto(savedTodo)
		return modelMapper.map(savedTodo, TodoDto.class);
	}

	@Override
	public TodoDto getTodoById(Long todoId) {
		Todo todo = todoRepository.findById(todoId).orElseThrow(()->
		new ResourceNotFoundException("Todo doesn't exist with the given id: "+todoId));
		return modelMapper.map(todo, TodoDto.class);
	}

	@Override
	public List<TodoDto> getAllTodos() {
		List<Todo> allTodos = todoRepository.findAll();
		return allTodos.stream().map(todo -> modelMapper.map(todo, TodoDto.class)).collect(Collectors.toList());
	}

	@Override
	public TodoDto updateTodoById(Long todoId, TodoDto todoDto) {
		Todo todo = todoRepository.findById(todoId).orElseThrow(()->
		new ResourceNotFoundException("Todo doesn't exist with the given id: "+todoId));
		todoDto.setId(todoId);
		todo = modelMapper.map(todoDto, Todo.class);
		Todo savedTodo = todoRepository.save(todo);
		return modelMapper.map(savedTodo, TodoDto.class);
	}

	@Override
	public void deleteById(Long todoId) {
		Todo todo = todoRepository.findById(todoId).orElseThrow(()->
		new ResourceNotFoundException("Todo doesn't exist with the given id: "+todoId));
		todoRepository.deleteById(todoId);
	}
	
	@Override
	public TodoDto completeTodo(Long id) {
		Todo todo = todoRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Todo doesn't exist with the given id: "+id));
		todo.setCompleted(Boolean.TRUE);
		Todo updatedTodo = todoRepository.save(todo);
		return modelMapper.map(updatedTodo, TodoDto.class);
	}

	@Override
	public TodoDto inCompleteTodo(Long id) {
		Todo todo = todoRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Todo doesn't exist with the given id: "+id));
		todo.setCompleted(Boolean.FALSE);
		Todo updatedTodo = todoRepository.save(todo);
		return modelMapper.map(updatedTodo, TodoDto.class);
	}

}
