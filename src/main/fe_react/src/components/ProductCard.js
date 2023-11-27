import React, {useState} from 'react';
import PopUP from './PopUp';

const ProductCard = ({ product, id, fetch}) => {
    const [wishVehicle, setWishVehicle] = useState(localStorage.getItem(id + 'wish') ? 'images/svg/wish-black.svg' : 'images/svg/wish.svg');
    const [compareVehicle, setCompareVehicle] = useState(localStorage.getItem(id + 'compare') ? 'images/svg/comp.svg' : 'images/svg/prodcompare.svg');
    const urlParams = window.location.href.split('/')[3].includes('store');
    const [isPopupOpen, setIsPopupOpen] = useState(false);
    const [text, setText] = useState("");
    const [namePopup, setNamePopup] = useState("");

    const openPopup = () => {
        setIsPopupOpen(true);
    };

    const closePopup = (event) => {
        event.preventDefault()
        setIsPopupOpen(false);
    };


    const addVehicle = (key) => {
        let string = localStorage.getItem(key)

        if (string !== null) {
            string = string + id + ','
        } else {
            string = id + ','
        }

        localStorage.setItem(key, string)
    };


    const removeVehicle = (key) => {
        let string = localStorage.getItem(key)

        if (string !== null) {
            string = string.replace(id + ',', '')
            localStorage.setItem(key, string)
        }
        if (fetch) {
            fetch()
        }
    };

    const handleWishClick = (event) => {
        event.preventDefault()

        if (localStorage.getItem(id + 'wish')) {
            localStorage.removeItem(id + 'wish');
            removeVehicle("wishlist");
        } else {
            localStorage.setItem(id + 'wish', true);
            addVehicle("wishlist");
            setText("Go to wishlists");
            setNamePopup("Wishlist");
            openPopup();
        }

        setWishVehicle(localStorage.getItem(id + 'wish') ? 'images/svg/wish-black.svg' : 'images/svg/wish.svg')
    }

    const handleCompareClick = (event) => {
        event.preventDefault()

        if (localStorage.getItem(id + 'compare')) {
            localStorage.removeItem(id + 'compare')
            removeVehicle("compareList")
        } else {
            localStorage.setItem(id + 'compare', true)
            addVehicle("compareList")
            setText("Go to compare products")
            setNamePopup("Comapre Products")
            openPopup()
        }

        setCompareVehicle(localStorage.getItem(id + 'compare') ? 'images/svg/comp.svg' : 'images/svg/prodcompare.svg')
    }


    return (
        <>
            <PopUP isOpen={isPopupOpen} onClose={closePopup} product={product} text={text} name={namePopup}/>
            <div className="gr-12  py-2">
                <div className='product-card container'>
                    <div className='row'>
                        <div className='col-6 row product-image'>
                            <img src={"/home/alex/IdeaProjects/ProiectSAC/src/main/fe_react/public/images/users/f268a0e7-b683-45d1-a130-46a80ffad560/Audi A1 2011 1 197 cm3 Gasoline/img1.png"} className='img-fluid d-block' alt="product image"/>
                        </div>

                        <div className='col-4 product-title-info'>
                            <div className='row'>
                                <h5 className='product-title'>{product.title}</h5>
                            </div>
                            <div className='row'>
                                <h5 className='product-title'>{product.km}</h5>
                            </div>
                        </div>
                        <div className='col-2 product-price-info d-flex flex-column'>
                            <div className='row price flex-grow-1'>
                                <h5 className='price text-end'>{product.price.concat(product.currency)}</h5>
                            </div>
                            <div className='row icons text-end'>
                                <img
                                    className='img-fluid ms-auto'
                                    src={compareVehicle}
                                    alt='compare'
                                    onClick={handleCompareClick}
                                />
                            </div>
                            <div className='row icons text-end'>

                                <img
                                    className='img-fluid ms-auto'
                                    src={!urlParams ?  "images/svg/cross.svg": wishVehicle}
                                    alt='like icon'
                                    onClick={handleWishClick}
                                />
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </>
    )
}

export default ProductCard