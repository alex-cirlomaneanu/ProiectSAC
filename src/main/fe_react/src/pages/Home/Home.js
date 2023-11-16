import React from 'react'
import './Home.css';
import Header from '../../components/Header';
import CarCarousel from "../../components/carcarousel/CarCarousel";
import {Image} from "react-bootstrap";

const Home = () => {
    return (
        <div>
            <div className='home-wrapper home-page'>
                <Header />
                <div className="bg-image">
                    <Image src="/images/car-bg.jpeg" className="image-fluid" fluid alt="Sample"/>
                    <div className='mask' style={{ backgroundColor: 'rgba(0, 0, 0, 0.6)' }}>
                        <div className="text-over-image">
                            Welcome To RecoAuto
                        </div>
                    </div>
                </div>
                <CarCarousel/>
            </div>
        </div>
    )

}

export default Home;