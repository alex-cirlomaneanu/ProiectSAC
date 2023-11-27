import { NavLink, Link } from 'react-router-dom';
import React from 'react';


export const Header = () => {
    const jwtToken = localStorage.getItem('jwtToken');

    const handleLogout = () => {
        localStorage.removeItem('jwtToken');
        window.location.reload();
    };

    return (
        <>
            <header className='header-bottom py-2'>
                <div className='container'>
                    <nav className="navbar navbar-expand-lg navbar-light bg-light">
                        <div className="container-fluid">
                            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                                <span className="navbar-toggler-icon"></span>
                            </button>
                            <div className="collapse navbar-collapse" id="navbarNav">
                                <ul className="navbar-nav ">
                                    <li className="nav-item mx-5">
                                        <Link to='/' className='d-flex align-items-center gap-15 text-black'>
                                            <p className='mb-0'>Home</p>
                                        </Link>
                                    </li>
                                    <li className="nav-item mx-5">
                                        <Link to='/login' className='d-flex align-items-center gap-10 text-black'>
                                            <p className='mb-0'>LogIn</p>
                                        </Link>
                                    </li>
                                    <li className="nav-item mx-5">
                                        <Link to='/store' className='d-flex align-items-center gap-10 text-black'>
                                            <p className='mb-0'>Store</p>
                                        </Link>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </nav>
                </div>
            </header>
        </>
    )
}

export default Header