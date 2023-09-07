import React, { useEffect, useState } from 'react'
import { addTodo, getTodoById, updateTodo } from '../services/TodoService'
import {useNavigate, useParams} from 'react-router-dom'

const TodoComponent = () => {
    const[title,setTitle] = useState('')
    const[description,setDescription] = useState('')
    const[completed,setCompleted] = useState('')
    const {id} = useParams()
    const navigator = useNavigate()
    useEffect(()=>{
        getTodoById(id).then((response)=>{
            setTitle(response.data.title);
            setDescription(response.data.description);
            setCompleted(response.data.completed)
        }).catch(err=>console.error(err))
    },[id])
    function onFormSubmit(e){
        e.preventDefault();
        const todo = {title,description,completed}
        if(id){
            updateTodo(id,todo).then(console.log(todo)).catch(err=>console.error(err))
            navigator('/todos')
        }
        else{
        addTodo(todo).then(console.log(todo)).catch(err=>console.error(err))
        navigator('/todos')
        }
    }
    function titleChange(){
        if(id){
            <h2 className='text-center'>Update Todo</h2>
        }
        else{
            <h2 className='text-center'>Add Todo</h2>
        }
    }
  return (
    <div className='container'>
        <br/><br/>
        <div className='row'>
            <div className='card col-md-6 offset-md-3 offset-md-3'>
                {titleChange()}
                <div className='card-body'>
                    <form>
                        <div className='form-group mb-2'>
                            <label className='form-label'>Todo Title:</label>
                            <input type="text"
                            placeholder='Enter Todo Title'
                            className='form-control'
                            name='title'
                            value={title}
                            onChange={(e)=>setTitle(e.target.value)}></input>
                        </div>
                        <div className='form-group mb-2'>
                            <label className='form-label'>Todo Description:</label>
                            <input type="text"
                            placeholder='Enter Todo Description'
                            className='form-control'
                            name='description'
                            value={description}
                            onChange={(e)=>setDescription(e.target.value)}></input>
                        </div>
                        <div className='form-group mb-2'>
                            <label className='form-label'>Todo Completed:</label>
                            <select
                            type="select"
                            className='form-control'
                            value={completed}
                            name='completed'
                            onChange={(e)=>setCompleted(e.target.value)}>
                                <option value="false">NO</option>
                                <option value="true">YES</option>
                            </select>
                        </div>
                        <button className='btn btn-success' onClick={onFormSubmit}>Submit</button>
                    </form>
                    
                </div>
            </div>

        </div>
    </div>
  )
}

export default TodoComponent