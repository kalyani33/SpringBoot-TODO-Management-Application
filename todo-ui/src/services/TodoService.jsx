import axios from "axios"
import { getToken } from "./AuthService";

const TODO_REST_BASE_URL="http://localhost:8080/api/todos"

// Add a request interceptor
axios.interceptors.request.use(function (config) {
    config.headers['Authorization'] = getToken();
    return config;
  }, function (error) {
    // Do something with request error
    return Promise.reject(error);
  });


export const listTodos = () => axios.get(TODO_REST_BASE_URL);
export const addTodo = (todo) => axios.post(TODO_REST_BASE_URL,todo);
export const updateTodo = (id,todo) => axios.put(TODO_REST_BASE_URL+'/'+id,todo)
export const getTodoById = (id) => axios.get(TODO_REST_BASE_URL+'/'+id)
export const deleteTodoById = (id) => axios.delete(TODO_REST_BASE_URL+'/'+id)
export const completeTodoById = (id)=>axios.patch(TODO_REST_BASE_URL+'/'+id+'/complete')
export const inCompleteTodoById = (id)=>axios.patch(TODO_REST_BASE_URL+'/'+id+'/in-complete')