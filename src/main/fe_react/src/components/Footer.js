import React from 'react'
import { NavLink, Link } from 'react-router-dom';
import {BsInstagram, BsFacebook } from "react-icons/bs";
const Footer = () => {
    return (
        <>
            <footer className='py-4'>
                <div className='container-xxl'>
                    <div className='row align-item-center'>
                        <div className='col-5'>
                            <div className='footer-top-data d-flex gap-30 align-items-center'>
                                <img src='images/svg/newsletter.png' alt='newsletter'/>
                                <h2 className='mb-0 text-white'>Sing Up for Newsletter</h2>
                            </div>
                        </div>
                        <div className='col-7'>
                            <div className="input-group">
                                <input
                                    type="text"
                                    className="form-control py-2"
                                    placeholder="Your Email Address"
                                    aria-label="Your Email Address"
                                    aria-describedby="basic-addon2"
                                />
                                <span className="input-group-text p-2" id="basic-addon2">
                  Subscribe
                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </footer>
            <footer className='py-3'>
                <div className='container-xxl'>
                    <div className='row'>
                        <div className='col-4'>
                            <h4 className='mb-4 text-white'>Contact Us</h4>
                            <div>
                                <address className='text-white fs-6'>
                                    Bucuresti
                                </address>
                                <a className='text-white' href='tel:+40 787 607 239'>+40 787 607 239</a>
                                <a className='mt-4 d-block mb-2 text-white' href='support@autotrade.ro'>support@autotrade.ro</a>
                                <div className='social_icons d-flex align-items-center gap-30'>
                                    <a className='text-white' href=''>
                                        <BsInstagram className='fs-4'/>
                                    </a>
                                    <a className='text-white' href=''>
                                        <BsFacebook className='fs-4'/>
                                    </a>
                                </div>
                            </div>

                        </div>
                        <div className='col-3'>
                            <h4 className='mb-4 text-white'>Information</h4>
                            <div></div>
                        </div>
                        <div className='col-3'>
                            <h4 className='mb-4 text-white'>Account</h4>

                            <div className='footer-links d-flex flex-column'>
                                <Link className='text-white py-1 mb-1'>About Us</Link>
                                <Link className='text-white py-1 mb-1'>Faq</Link>
                                <Link className='text-white py-1 mb-1'>Contact</Link>
                            </div>
                        </div>
                        <div className='col-2'>
                            <h4 className='mb-4 text-white'>Quick links</h4>
                            <div></div>
                        </div>
                    </div>
                </div>
            </footer>
            <footer className='py-4'>
                <div className='container-xxl'>
                    <div className='row'>
                        <div className='col-12'>
                            <p className='text-center mb-0 text-white'>&copy; {new Date().getFullYear()} Powered by Razvan Coman</p>
                        </div>
                    </div>
                </div>
            </footer>
        </>
    )
}

export default Footer