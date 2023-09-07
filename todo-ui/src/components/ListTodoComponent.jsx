import React from 'react'
import { useEffect } from 'react';
import { useState } from 'react'
import { completeTodoById, deleteTodoById, inCompleteTodoById, listTodos } from '../services/TodoService';
import { useNavigate } from 'react-router-dom';
import { isAdminUser } from '../services/AuthService';

const ListTodoComponent = () => {
    const [todos,setTodos] = useState([]);
    useEffect(()=>{
        displayAllTodos()
    },[])
    function displayAllTodos(){
        listTodos().then(response => {
            setTodos(response.data);
            console.log(response.data)
        }).catch(error=>console.error(error))
    }
    const navigator = useNavigate()
    function handleAddTodo(){
        navigator('/add-todo')
    }
    function updateTodo(id){
        navigator(`/edit-todo/${id}`)
    }
    function deleteTodo(id){
        deleteTodoById(id).then(res=>displayAllTodos()).catch(err=>console.error(err))
    }
    function completeTodo(id){
        completeTodoById(id).then(res =>{
            console.log(res);
            displayAllTodos();
        }).catch(err=>console.error(err))
    }
    function InCompleteTodo(id){
        inCompleteTodoById(id).then(res =>{
            console.log(res);
            displayAllTodos();
        }).catch(err=>console.error(err))
    }
    const isAdmin = isAdminUser();
  return (
    <div className='container'>
        <h2 className='text-center'>List Of Todos</h2>
        <div>
            {
                isAdmin && <button className='btn btn-primary mb-2' onClick={handleAddTodo}>Add Todo</button>
            }
            <table className='table table-striped table-bordered'>
                <thead>
                    <tr>
                        <th>Todo Title</th>
                        <th>Todo Description</th>
                        <th>Todo Completed</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {todos.map(todo =>
                        <tr key={todo.id}>
                            <td>{todo.title}</td>
                            <td>{todo.description}</td>
                            <td>{todo.completed ? 'YES':'NO'}</td>
                            <td>
                                {
                                    isAdmin && <button className='btn btn-info md-2' onClick={()=>updateTodo(todo.id)}>Update</button>
                                }
                                {
                                    isAdmin && <button className='btn btn-danger' onClick={()=>deleteTodo(todo.id)} style={{marginLeft:'10px'}}>Delete</button>
                                }
                                <button className='btn btn-success' onClick={()=>completeTodo(todo.id)}style={{marginLeft:'10px'}}>Complete</button>
                                <button className='btn btn-info' onClick={()=>InCompleteTodo(todo.id)}style={{marginLeft:'10px'}}>In Complete</button>
                            </td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    </div>
  )
}

export default ListTodoComponent