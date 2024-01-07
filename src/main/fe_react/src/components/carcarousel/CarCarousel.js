import React, {useEffect, useState} from 'react';
import { Carousel, Card, Row, Col } from 'react-bootstrap';
import './CarCarousel.css';
import {Link} from "react-router-dom";
import axios from "axios";
import {getAllVehicles, getVehicle, getVehicles, getWishVehicles} from "../../pages/constants/Endpoints";

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
    const [recommendedVehicles, setRecommendedVehicles] = useState([]);
    const [carIds, setCarIds] = useState([]);

    useEffect(() => {
        axios.post(getWishVehicles, {
            data: transformedArray
        })
            .then(response => {
                // console.log(response.data);
                setRecommendedVehicles(response.data);
            })
        .catch(error => {
            console.log(error);
        });
    }, []);

    useEffect(() => {
        if (recommendedVehicles.length > 0) {
            for (let i = 0; i < recommendedVehicles.length; i++) {
                axios.get(getVehicle + recommendedVehicles[i])
                    .then(response => {
                        // console.log(response.data);
                        setCarIds(prevState => [...prevState, response.data]);
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }
        }
    }, [recommendedVehicles]);
    const carChunks = chunkArray(carIds, 5); // Split books into chunks of 10

    return (
        <div className="car-carousel">
            <Carousel interval={null} indicators={true} variant="dark">
                {carChunks.map((chunk, index) => (
                    <Carousel.Item key={index}>
                        <Col>
                            {chunk.map((car, innerIndex) => (
                                    <Link className="link-to-car" to={`/cars/${car.carId}`}>
                                        <Card className="car-card-carousel">
                                            <Card.Title className="car-card-carousel-title">{car.title}</Card.Title>
                                            <Card.Subtitle>{car.model}</Card.Subtitle>
                                            <Card.Body className="car-card-carousel-body">
                                                {car.pathImages === null ? (
                                                    <Card.Img variant="top" src="" alt={car.name}/>
                                                ) : (
                                                    <Card.Img variant="top" src={car.pathImages} alt={car.name}/>
                                                )}
                                                <Card.Text className="car-card-carousel-text">
                                                    <p>An {car.year}</p>
                                                    <p>Kilometraj {car.km}</p>
                                                    <p>Pret {car.price} €</p>
                                                </Card.Text>
                                            </Card.Body>
                                        </Card>
                                    </Link>
                            ))}
                        </Col>
                    </Carousel.Item>
                ))}
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