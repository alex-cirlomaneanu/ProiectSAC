import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { getAllBrands, getModelsByBrand} from '../constants/Endpoints';
import {styles, fuels, countryOfOrigin, years, Km, price} from '../constants/Constants';

const FilterBar = (data) => {
    const [brands, setBrands] = useState([]);
    const [models, setModels] = useState([]);
    const [brand, setBrand] = useState(null);
    const [model, setModel] = useState(null);
    const [style, setStyle] = useState(null);
    const [gearbox, setGearbox] = useState(null);
    const [fuel, setFuel] = useState(null);
    const [countryOrigin, setCountryOfOrigin] = useState(null);
    const [priceFrom, setPriceFrom] = useState(null);
    const [priceTo, setPriceTo] = useState(null);
    const [kmFrom, setKmFrom] = useState(null);
    const [kmTo, setKmTo] = useState(null);
    const [yearFrom, setYearFrom] = useState(null);
    const [yearTo, setYearTo] = useState(null);

    const [products, setProducts] = useState([]);
    const [order, setOrder] = useState([]);

    const addToOrder = (value) => {
        const lastElem = order[order.length - 1];

        if (lastElem && lastElem.key === value.key) {
            if (value.value === 'clear') {
                setOrder([...order.slice(0, order.length - 1)]);
                return;
            }
            setOrder([...order.slice(0, order.length - 1), value]);
        } else {
            removeItemByKey(value);
        }
    };

    const removeItemByKey = (value) => {
        if (value.value === 'clear') {
            setOrder(prevOrder => prevOrder.filter(item => item.key !== value.key));
            return;
        }
        setOrder(prevOrder =>[...prevOrder.filter(item => item.key !== value.key), value]);
        if (value.key === 'brand') {
            setOrder(prevOrder =>[...prevOrder.filter(item => item.key !== 'model')]);
        }
    };

    const clearFIlter = () => {
        const inputStates = document.getElementsByClassName('inputState');
        for (let i = 0; i < inputStates.length; i++) {
            inputStates[i].selectedIndex = 0;
        }

        setProducts(JSON.parse(localStorage.getItem('products')));
        setBrand(null);
        setModel(null);
        setStyle(null);
        setGearbox(null);
        setFuel(null);
        setCountryOfOrigin(null);
        setPriceFrom(null);
        setPriceTo(null);
        setKmFrom(null);
        setKmTo(null);
        setYearFrom(null);
        setYearTo(null);
        setOrder([]);
    };

    useEffect(() => {
        doFilter(products)
    }, [order]);

    useEffect(() => {
        let sortedProducts = null;
        switch (data.optionFilter) {


            case 'priceAsc':
                sortedProducts = [...products].sort((a, b) => parseInt(a.price.replace(/\s/g, ""), 10) - parseInt(b.price.replace(/\s/g, ""), 10));
                setProducts(sortedProducts);
                break;
            case 'priceDesc':
                sortedProducts = [...products].sort((a, b) => parseInt(b.price.replace(/\s/g, ""), 10) - parseInt(a.price.replace(/\s/g, ""), 10));
                setProducts(sortedProducts);
                break;
            case 'kmAsc':
                sortedProducts = [...products].sort((a, b) => parseInt(a.km.replace(/\s|,+/g, ""), 10) - parseInt(b.km.replace(/\s|,+/g, ""), 10));
                setProducts(sortedProducts);
                break;
            case 'kmDesc':
                sortedProducts = [...products].sort((a, b) => parseInt(b.km.replace(/\s|,+/g, ""), 10) - parseInt(a.km.replace(/\s|,+/g, ""), 10));
                setProducts(sortedProducts);
                break;
            case 'newest':
                sortedProducts = [...products].sort((a, b) => new Date(b.dateAdded) - new Date(a.dateAdded));
                setProducts(sortedProducts);
                break;

        }
        doFilter(sortedProducts)

    }, [data.optionFilter]);


    useEffect(() => {
        data.setProducts(JSON.parse(localStorage.getItem('products')));
        setProducts(JSON.parse(localStorage.getItem('products')));

        axios.get(getAllBrands).then(response => {
            setBrands(response.data.allBrands);
        })
            .catch(error => {
                console.log(error);
            });
    } , []);


    const doFilter = (value) => {
        let filteredProducts = value;

        order.map((item) => {
            switch (item.key) {
                case 'brand':
                    filteredProducts = filteredProducts.filter(product => product.brand === item.value);
                    break;
                case 'model':
                    filteredProducts = filteredProducts.filter(product => product.model === item.value);
                    break;
                case 'bodyType':
                    filteredProducts = filteredProducts.filter(product => product.bodyType === item.value);
                    break;
                case 'gearbox':
                    filteredProducts = filteredProducts.filter(product => product.gearbox === item.value);
                    break;
                case 'fuelType':
                    filteredProducts = filteredProducts.filter(product => product.fuelType === item.value);
                    break;
                case 'countryOfOrigin':
                    filteredProducts = filteredProducts.filter(product => product.countryOfOrigin === item.value);
                    break;
                case 'priceFrom':
                    filteredProducts = filteredProducts.filter(product => parseInt(product.price.replace(/\s/g, ""), 10) >= parseInt(item.value.replace(/\s/g, ""), 10));
                    break;
                case 'priceTo':
                    filteredProducts = filteredProducts.filter(product => parseInt(product.price.replace(/\s/g, ""), 10) <= parseInt(item.value.replace(/\s/g, ""), 10));
                    break;
                case 'kmFrom':
                    filteredProducts = filteredProducts.filter(product => parseInt(product.km.replace(/\s|,+/g, ""), 10) >= parseInt(item.value.replace(/\s|,+/g, ""), 10));
                    break;
                case 'kmTo':
                    filteredProducts = filteredProducts.filter(product => parseInt(product.km.replace(/\s|,+/g, ""), 10) <= parseInt(item.value.replace(/\s|,+/g, ""), 10));
                    break;
                case 'yearFrom':
                    filteredProducts = filteredProducts.filter(product => product.year >= item.value);
                    break;
                case 'yearTo':
                    filteredProducts = filteredProducts.filter(product => product.year <= item.value);
                    break;
            }
        });
        data.setProducts(filteredProducts);
    };

    const handleSelectBrand = (event) => {
        setBrand(event.target.value);
        addToOrder({ key: "brand", value: event.target.value });

        setModel(null);
        const selectElement = document.getElementById('inputStateModel');
        selectElement.selectedIndex = 0;

        if (event.target.value === 'clear') {
            setBrand(null);
            return null;
        }

        console.log(event.target.value);
        const url = getModelsByBrand + event.target.value;
        axios.get(url).then(response => {
            setModels(response.data.models);
        })
            .catch(error => {
                console.log(error);
            });
    };

    const handleSelectModel = (event) => {
        setModel(event.target.value);
        addToOrder({ key: "model", value: event.target.value });

        if (event.target.value === 'clear') {
            setModel(null);
            return null;
        }
    };

    const handleSelectType = (option) => {
        setStyle(option.split(".")[0]);
        addToOrder({ key: "bodyType", value: option.split(".")[0]});
        if (option === 'clear') {
            setStyle(null);
            return null;
        }
    };


    const handleSelectGearbox = (event) => {
        setGearbox(event.target.value);
        addToOrder({ key: "gearbox", value: event.target.value});

        if (event.target.value === 'clear') {
            setGearbox(null);
            return null;
        }
    };

    const handleSelectFuel = (event) => {
        setFuel(event.target.value);
        addToOrder({ key: "fuelType", value: event.target.value});

        if (event.target.value === 'clear') {
            setFuel(null);
            return null;
        }
    };

    const handleSelectCountryOfOrigin = (event) => {
        setCountryOfOrigin(event.target.value);
        addToOrder({ key: "countryOfOrigin", value: event.target.value});

        if (event.target.value === 'clear') {
            setCountryOfOrigin(null);
            return null;
        }
    };

    const handleSelectPriceFrom = (event) => {
        setPriceFrom(event.target.value);
        addToOrder({ key: "priceFrom", value: event.target.value});

        if (event.target.value === 'clear') {
            setPriceFrom(null);
            return null;
        }
    };

    const handleSelectPriceTo = (event) => {
        setPriceTo(event.target.value);
        addToOrder({ key: "priceTo", value: event.target.value});

        if (event.target.value === 'clear') {
            setPriceTo(null);
            return null;
        }
    };

    const handleSelectKmFrom = (event) => {
        setKmFrom(event.target.value);
        addToOrder({ key: "kmFrom", value: event.target.value});

        if (event.target.value === 'clear') {
            setKmFrom(null);
            return null;
        }
    };

    const handleSelectKmTo = (event) => {
        setKmTo(event.target.value);
        addToOrder({ key: "kmTo", value: event.target.value});

        if (event.target.value === 'clear') {
            setKmTo(null);
            return null;
        }
    };


    const handleSelectYearFrom = (event) => {
        setYearFrom(event.target.value);
        addToOrder({ key: "yearFrom", value: event.target.value});

        if (event.target.value === 'clear') {
            setYearFrom(null);
            return null;
        }
    };

    const handleSelectYearTo = (event) => {
        setYearTo(event.target.value);
        addToOrder({ key: "yearTo", value: event.target.value});

        if (event.target.value === 'clear') {
            setYearTo(null);
            return null;
        }
    };

    return (
        <div className='filter-bar'>
            <div className="row">
                <div className="col-md-2 py-3">
                    <select id="inputStateBrand" className="form-select inputState " onChange={handleSelectBrand}>
                        <option className='text-center' value="clear" >{brand == null ? "Brand" : "Clear Option"}</option>
                        {brands.map((brand, index) => (
                            <option key={index} value={brand}>{brand}</option>
                        ))}
                    </select>
                </div>
                <div className="col-md-2 py-3">
                    <select id="inputStateModel" className="form-select inputState" onChange={handleSelectModel}>
                        <option className='text-center' value='clear'>{model === null ? "Model" : "Clear Option"}</option>
                        {models.map((option, index) => (
                            <option key={index} value={option}>{option}</option>
                        ))}
                    </select>
                </div>

                <div className="col-md-2 py-3 dropdown-type">
                    <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        {style ? style.split(".")[0] : "Body Type"}
                    </button>
                    <ul className="dropdown-menu scrollable-menu text-center" aria-labelledby="dropdownMenuButton1">
                        {styles.map((option, index) => (
                            <li key={index} onClick={() => handleSelectType(option)}>
                                <div className="image-wrapper">
                                    { option !== 'clear' ?
                                        <>
                                            <img src={'images/body_style/' + option} alt={option} />
                                            <p>{option.split(".")[0]}</p>
                                        </>: <p>Clear Option</p>
                                    }
                                </div>
                            </li>
                        ))}
                    </ul>
                </div>


                <div className="col-md-2 py-3">
                    <select id="inputStateGearbox" className="form-select inputState " onChange={handleSelectGearbox}>
                        <option className='text-center' value='clear' >{gearbox == null ? "Gearbox" : "Clear Option"}</option>
                        <option  value="Manual">Manual</option>
                        <option  value="Automatic">Automatic</option>
                    </select>
                </div>

                <div className="col-md-2 py-3">
                    <select id="inputStateFuel" className="form-select inputState" onChange={handleSelectFuel}>
                        <option className='text-center' value='clear'>{fuel == null ? "Fuel" : "Clear Option"}</option>
                        {fuels.map((option, index) => (
                            <option key={index} value={option}>{option}</option>
                        ))}
                    </select>
                </div>

                <div className="col-md-2 py-3">
                    <select id="inputStateOrigin" className="form-select inputState" onChange={handleSelectCountryOfOrigin}>
                        <option  value='clear'>{countryOrigin == null ? "Country of origin" : "Clear Option"}</option>
                        {countryOfOrigin.map((option, index) => (
                            <option key={index} value={option}>{option}</option>
                        ))}
                    </select>
                </div>
            </div>

            <div className="row">
                <div className="col-md-2 py-3">
                    <select id="inputStateYearFrom" className="form-select inputState" onChange={handleSelectYearFrom}>
                        <option className='text-center' value='clear'>{yearFrom == null ? "Year From" : "Clear Option"}</option>
                        {years.map((option, index) => (
                            <option key={index} value={option}>{option}</option>
                        ))}
                    </select>
                </div>

                <div className="col-md-2 py-3">
                    <select id="inputStateYearTo" className="form-select inputState" onChange={handleSelectYearTo}>
                        <option className='text-center' value='clear'>{yearTo == null ? "Year To" : "Clear Option"}</option>
                        {years.map((option, index) => (
                            <option key={index} value={option}>{option}</option>
                        ))}
                    </select>
                </div>

                <div className="col-md-2 py-3">
                    <select id="inputStateKmFrom" className="form-select inputState" onChange={handleSelectKmFrom}>
                        <option className='text-center' value='clear'>{kmFrom == null ? "Km From" : "Clear Option"}</option>
                        {Km.map((option, index) => (
                            <option  key={index} value={option}>{option}</option>
                        ))}
                    </select>
                </div>

                <div className="col-md-2 py-3">
                    <select id="inputStateKmTo" className="form-select inputState" onChange={handleSelectKmTo}>
                        <option className='text-center' value='clear'>{kmTo == null ? "Km To" : "Clear Option"}</option>
                        {Km.map((option, index) => (
                            <option key={index} value={option}>{option}</option>
                        ))}
                    </select>
                </div>


                <div className="col-md-2 py-3">
                    <select id="inputStatePriceFrom" className="form-select inputState" onChange={handleSelectPriceFrom}>
                        <option className='text-center' value='clear'>{priceFrom == null ? "Price From" : "Clear Option"}</option>
                        {price.map((option, index) => (
                            <option key={index} value={option}>{option}</option>
                        ))}
                    </select>
                </div>

                <div className="col-md-2 py-3">
                    <select id="inputStatePriceTo" className="form-select inputState" onChange={handleSelectPriceTo}>
                        <option className='text-center' value='clear'>{priceTo == null ? "Price To" : "Clear Option"}</option>
                        {price.map((option, index) => (
                            <option key={index} value={option}>{option}</option>
                        ))}
                    </select>
                </div>
            </div>
            <div  className='close-filter'>
                <button onClick={clearFIlter}>
                    Clear Filter
                </button>
            </div>
        </div>
    )
}

export default FilterBar;