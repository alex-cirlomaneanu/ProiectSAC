import { useState } from "react";
import axios from "axios";
import { Link, Navigate } from 'react-router-dom'

const Singup = () => {
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [isValidPassword, setIsValid] = useState(false)
    const [confirmPassword, setConfirmPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [isRegistered, setIsRegistered] = useState(false);

    const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%*]).{8,24}$/;
    const ERROR_PWD = "The password must contain between 8 and 24 characters,must include uppercase and lowercase letters, a number and a special character."

    const handlePasswordChange = (event) => {
        const isValidPassword = PWD_REGEX.test(event.target.value);
        setPassword(event.target.value);
        setIsValid(isValidPassword);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!isValidPassword) {
            alert(ERROR_PWD);
            return;
        }
        if (password !== confirmPassword) {
            setErrorMessage('Passwords do not match.');
            return;
        }

        try {
            const response = await axios.post('http://localhost:8080/api/v1/auth/register', {
                firstname: firstname,
                lastname: lastname,
                email: email,
                password: password
            });
            if (response.data.error !== "")
                alert('Email already exist')
            else
                setIsRegistered(true);
            setErrorMessage('');
        } catch (error) {
            console.error(error);
            setErrorMessage('An error occurred during registration. Please try again.');
        }
    }


    return (
        <div className='auth register'>
            {isRegistered && <Navigate to="/login" />}
            <div className='register-wrapper py-2 home-wrapper-2'>
                <div className='container-xxl'>
                    <div className='row'>
                        <div className='col-12'>
                            <div className='auth-card'>
                                <h3 className='text-center'>Register</h3>
                                <form action='' className='d-flex flex-column gap-15' onSubmit={handleSubmit}>
                                    <div>
                                        <input
                                            type='text'
                                            name='first name'
                                            placeholder='First Name'
                                            className='form-control'
                                            onChange={(event) => setFirstname(event.target.value)}
                                            required
                                        />
                                    </div>
                                    <div>
                                        <input
                                            type='text'
                                            name='last name'
                                            placeholder='Last Name'
                                            className='form-control'
                                            onChange={(event) => setLastname(event.target.value)}
                                            required
                                        />
                                    </div>
                                    <div>
                                        <input
                                            type='email'
                                            name='email'
                                            placeholder='Email'
                                            className='form-control'
                                            onChange={(event) => setEmail(event.target.value)}
                                            required
                                        />
                                    </div>
                                    <div>
                                        <input
                                            type='password'
                                            name='password'
                                            placeholder='Password'
                                            className='form-control'
                                            onChange={handlePasswordChange}
                                            required
                                        />
                                    </div>
                                    <div>
                                        <input
                                            type='password'
                                            name='confirm password'
                                            placeholder='Confirm Password'
                                            className='form-control'
                                            onChange={(event) => setConfirmPassword(event.target.value)}
                                            required
                                        />
                                    </div>
                                    <div>
                                        {errorMessage && <p>{errorMessage}</p>}
                                        <Link to='/forgot-password'> Forgot Password ?</Link>
                                        <div className='d-flex justify-content-center gap-15 align-items-center'>
                                            <button className='button'>Singup</button>
                                            <Link to='/login' className='button singup'>Login</Link>
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

export default Singup;