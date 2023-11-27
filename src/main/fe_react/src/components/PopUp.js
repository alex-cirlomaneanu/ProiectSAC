import React from 'react'
import { Link } from 'react-router-dom';

const PopUP = ({ isOpen, onClose, product, text, name}) => {
    if (!isOpen) {
        return null;
    }

    return (
        <div className="popup-overlay">
            <div className="popup-content">
                <button className="close-button" onClick={onClose}>X</button>
                <h2 className='text-center text-black'>{name}</h2>
                <img src={product.path.concat(product.images[0])} alt={product.name} width={300} height={150} style={{objectFit: 'cover', alignItems: "center"}} />
                <h2 className='text-black' style={{fontSize: '20px'}}>{product.title} </h2>
                <p className='text-center' style={{color: "red"}}>{product.price} {product.currency}</p>
                { name === "Wishlist" ?
                    <Link to={`/wishlist`} className='btn btn-primary go-to-wish'>{text}</Link>
                    : name === "Comapre Products" ?
                        <Link to={`/compare`} className='btn btn-primary go-to-wish'>{text}</Link>
                        :
                        <Link to={`/${product.id}`} className='btn btn-primary go-to-wish'>{text}</Link>
                }
            </div>
        </div>
    );
};

export default PopUP;