import React from 'react';
import { Carousel, Card, Row, Col } from 'react-bootstrap';
import './CarCarousel.css';
import {Link} from "react-router-dom";

/**
 * Componenta pentru afisarea recomandarilor
 * @param cars
 * @returns {JSX.Element}
 * @constructor
 */
const CarCarousel = ({ cars }) => {
    const carChunks = chunkArray(cars, 3); // Split books into chunks of 3

    return (
        <Carousel interval={null} indicators={false} variant="dark">
            {carChunks.map((chunk, index) => (
                <Carousel.Item key={index}>
                    <Row>
                        {chunk.map((car, innerIndex) => (
                            <Col key={innerIndex} sm={4}>
                                <Link className="link-to-car" to={`/cars/${car.carId}`}>
                                    <Card className="car-card-carousel bg-transparent">
                                        {car.carImage === null ? (
                                            <Card.Img variant="top" src="" alt={car.name}/>
                                        ) : (
                                            <Card.Img variant="top" src={car.carImage} alt={car.name}/>
                                        )}
                                        <Card.Body className="car-card-carousel-body">
                                            <Card.Title className="car-card-carousel-title">{book.title}</Card.Title>
                                            <Card.Text className="car-card-carousel-text">
                                                <p>TODO informatii despre masina</p>
                                            </Card.Text>
                                        </Card.Body>
                                    </Card>
                                </Link>
                            </Col>
                        ))}
                    </Row>
                </Carousel.Item>
            ))}
        </Carousel>
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