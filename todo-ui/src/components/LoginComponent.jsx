import React, { useState } from 'react'
import { loginAPICall, saveLoggedInUser, storeToken } from '../services/AuthService';
import { useNavigate } from 'react-router-dom';

const LoginComponent = () => {
    const [usernameOrEmail,setUsernameOrEmail] = useState('')
    const [password,setPassword] = useState('')
    const navigator = useNavigate();

    async function handleOnLogin(e){
        e.preventDefault();
        //const loginObj = {usernameOrEmail,password}
        console.log(usernameOrEmail + ' ' + password)
        await loginAPICall(usernameOrEmail,password).then(res=>{
            console.log(res.data);
            //const token = 'Basic '+window.btoa(usernameOrEmail + ':' + password);
            const token = 'Bearer ' + res.data.accessToken;
            const role = res.data.role;
            storeToken(token);
            saveLoggedInUser(usernameOrEmail,role);
            navigator('/todos')
            window.location.reload(false);
        }).catch(err=>console.error(err));
    }
  return (
    <div className='container'>
        <br/><br/>
        <div className='row'>
            <div className='col-md-6 offset-md-3'>
                <div className='card'>
                    <div className='card-header'>
                        <h2 className='text-center'>User Login Form</h2>
                    </div>
                    <div className='card-body'>
                        <form>
                            <div className='row mb-3'>
                                <label className='col-md-3 control-label'>Username or Email </label>
                                <div className='col-md-9'>
                                    <input type='text'
                                    placeholder='Enter username or email'
                                    name='usernameOrEmail'
                                    className='form-control'
                                    onChange={e=>setUsernameOrEmail(e.target.value)}>
                                    </input>
                                </div>
                            </div>
                            <div className='row mb-3'>
                                <label className='col-md-3 control-label'>Password </label>
                                <div className='col-md-9'>
                                    <input type='password'
                                    placeholder='Enter password'
                                    name='password'
                                    className='form-control'
                                    onChange={e=>setPassword(e.target.value)}>
                                    </input>
                                </div>
                            </div>
                            <div className='form-group mb-3'>
                                <button className='btn btn-primary' onClick={ e => handleOnLogin(e)}>Login</button>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>
        
    </div>
  )
}

export default LoginComponent