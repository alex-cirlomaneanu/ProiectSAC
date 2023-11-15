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
            <header className='header-upper py-2'>
                <div className='container-xxl'>
                    <div className='row'>
                        <div className='col-7'>
                            <h2>
                                <Link className='text-white'>AutoTrade</Link>
                            </h2>
                        </div>
                        <div className='col-5'>
                            <div className='header-upper-links d-flex justify-content-between'>
                                <div>
                                    <Link to='/compare' className='d-flex align-items-center gap-10 text-white'>
                                        <img src='images/svg/compare.svg' alt='compare'/>
                                        <p className='mb-0'>Compare <br /> Products </p>
                                    </Link>
                                </div>
                                <div>
                                    <Link to='/wishlist' className='d-flex align-items-center gap-10 text-white'>
                                        <img src='images/svg/wishlist.svg' alt='wishlist'/>
                                        <p className='mb-0'> Favourite <br/> Wishlist</p>
                                    </Link>
                                </div>
                                <div>

                                    {jwtToken === null ?
                                        <Link to='/login' className='d-flex align-items-center gap-10 text-white'>
                                            <img src='images/svg/user.svg' alt='user'/>
                                            <p className='mb-0'>Log In <br /> My account</p>
                                        </Link>
                                        :
                                        <div className='d-flex align-items-center gap-10 text-white'  >
                                            <img src='images/svg/user.svg' alt='user'/>
                                            <div className="dropdown">
                                                <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                                    My account
                                                </button>
                                                <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                                    <li>
                                                        <Link to="/dashboard" className="dropdown-item text-white">
                                                            <a className="text-white" href="#">User details</a>
                                                        </Link>
                                                    </li>
                                                    <li>
                                                        <Link to="/add-product" className="dropdown-item text-white">
                                                            <a className="text-white" href="#">Sell A Car</a>
                                                        </Link>
                                                    </li>
                                                    <li>
                                                        <Link to="/search-history" className="dropdown-item text-white">
                                                            <a className="text-white" href="#">Search History</a>
                                                        </Link>
                                                    </li>
                                                    <li>
                                                        <Link onClick={handleLogout} to="/" className="dropdown-item text-white">
                                                            <a className="text-white" href="#">Log out</a>
                                                        </Link>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    }
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
            <header className='header-bottom py-2'>
                <div className='container'>
                    <div className='row'>
                        <div className='col-12'>
                            <div className='gap-40 d-flex justify-content-end'>
                                <NavLink className='text-white' to="/">Home</NavLink>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
        </>
    )
}

export default Header