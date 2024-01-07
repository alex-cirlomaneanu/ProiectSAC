import React, {useEffect, useState} from 'react';
import { Carousel, Card, Row, Col } from 'react-bootstrap';
import './CarCarousel.css';
import {Link} from "react-router-dom";
import axios from "axios";
import {getAllVehicles, getWishVehicles} from "../../pages/constants/Endpoints";

/**
 * Componenta pentru afisarea recomandarilor
 * @param
 * @returns {JSX.Element}
 * @constructor
 */
const CarCarousel = () => {
    const [wishVehicle, setWishVehicle] = useState(localStorage.getItem('wishlist'));
    const uuidArray = wishVehicle.split(',');
    const transformedArray = [`${uuidArray[0]},${uuidArray[1]}`, ...uuidArray.slice(2)];

    useEffect(() => {
        axios.post(getWishVehicles, {
            data: transformedArray
        }).then(response => {
            console.log(response.data);

        })
        .catch(error => {
            console.log(error);
        });
    }, []);

    return (
        <div className="car-carousel">
            <Carousel interval={null} indicators={true} variant="dark">
                {/*{carChunks.map((chunk, index) => (*/}
                {/*    <Carousel.Item key={index}>*/}
                {/*        <Col>*/}
                {/*            {chunk.map((car, innerIndex) => (*/}
                {/*                    <Link className="link-to-car" to={`/cars/${car.carId}`}>*/}
                {/*                        <Card className="car-card-carousel">*/}
                {/*                            <Card.Title className="car-card-carousel-title">{car.name}</Card.Title>*/}
                {/*                            <Card.Subtitle>{car.model}</Card.Subtitle>*/}
                {/*                            <Card.Body className="car-card-carousel-body">*/}
                {/*                                {car.carImage === null ? (*/}
                {/*                                    <Card.Img variant="top" src="" alt={car.name}/>*/}
                {/*                                ) : (*/}
                {/*                                    <Card.Img variant="top" src={car.carImage} alt={car.name}/>*/}
                {/*                                )}*/}
                {/*                                <Card.Text className="car-card-carousel-text">*/}
                {/*                                    <p>TODO informatii despre masina</p>*/}
                {/*                                </Card.Text>*/}
                {/*                            </Card.Body>*/}
                {/*                        </Card>*/}
                {/*                    </Link>*/}
                {/*            ))}*/}
                {/*        </Col>*/}
                {/*    </Carousel.Item>*/}
                {/*))}*/}
            </Carousel>
        </div>
    );
}

// Helper function to split array into chunks
function chunkArray(array, chunkSize) {
    const chunks = [];
    for (let i = 0; i < array.length; i += chunkSize) {
        chunks.push(array.slice(i, i + chunkSize));
    }
    return chunks;
}

export default CarCarousel;