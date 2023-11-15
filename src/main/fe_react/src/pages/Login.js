import { useState } from "react";
import axios from 'axios';
import { Link, Navigate } from 'react-router-dom'

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/api/v1/auth/authenticate', {
                email: email,
                password: password
            });
            if (response.data.error === 'Email does not exist')
                setErrorMessage('Email does not exist');
            else if (response.data.error === 'Email is not enabled')
                setErrorMessage('Email is not enabled');
            else if (response.data.error ==='Email or password does not match')
                setErrorMessage('Email or password does not match');
            else {
                localStorage.setItem('jwtToken', response.data.token);
                setIsLoggedIn(true);
            }
        } catch (error) {
            console.error(error);
            setErrorMessage('An error occurred during registration. Please try again.');
        }
    }


    return (
        <div className='auth'>
            {isLoggedIn && <Navigate to="/" />}
            <div className='login-wrapper py-2 home-wrapper-2'>
                <div className='container-xxl'>
                    <div className='row'>
                        <div className='col-12'>
                            <div className='auth-card'>
                                <h3 className='text-center'>Login</h3>
                                <form action='' className='d-flex flex-column gap-15' onSubmit={handleSubmit}>
                                    <div>
                                        <input
                                            type="email"
                                            id="email"
                                            value={email}
                                            placeholder="Email"
                                            onChange={(event) => setEmail(event.target.value)}
                                            required
                                            className='form-control'
                                        />
                                    </div>
                                    <div>
                                        <input
                                            type="password"
                                            id="password"
                                            value={password}
                                            placeholder="Password"
                                            onChange={(event) => setPassword(event.target.value)}
                                            required
                                            className='form-control'
                                        />
                                    </div>
                                    {errorMessage && <p>{errorMessage}</p>}
                                    <div>
                                        <Link to='/forgot-password'> Forgot Password ?</Link>
                                        <div className='d-flex justify-content-center gap-15 align-items-center'>
                                            <button className='button'>Login</button>
                                            <Link to='/singup' className='button singup'>Singup</Link>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Login