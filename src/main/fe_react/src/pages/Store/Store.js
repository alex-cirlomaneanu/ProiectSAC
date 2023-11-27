import React, {useState} from 'react';
import ProductCard from '../../components/ProductCard';
import {Link} from 'react-router-dom';
import Pagination from '../../components/Pagination';
import {itemsPerPage} from '../constants/Constants';
import FilterBar from './FilterBar';

const Store = () => {
    const [products, setProducts] = useState([]);
    const [optionFilter, setOptionFilter] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const [option, setOption] = useState("");

    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    const currentData = products.slice(startIndex, endIndex);

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    const handlePriceAsc = () => {
        setOptionFilter("priceAsc");
        setOption("Price ascending");
    };

    const handlePriceDesc = () => {
        setOptionFilter("priceDesc");
        setOption("Price descending");
    };

    const handleKmAsc = () => {
        setOptionFilter("kmAsc");
        setOption("Km ascending");
    };

    const handleKmDesc = () => {
        setOptionFilter("kmDesc");
        setOption("Km descending");
    };

    const handleNewest = () => {
        setOptionFilter("newest");
        setOption("Newest");
    };


    return (
        <div className='store-wrapper py-2 home-wrapper-2 py-5'>
            <div className='container'>
                <FilterBar products={products} setProducts={setProducts} optionFilter={optionFilter}/>
                <div className='row prod'>
                    <div className='col-6'>
                        <h2 className=' py-3'>{products.length} Results</h2>
                    </div>
                    <div className='col-6'>
                        <div className='d-flex justify-content-end'>
                            <div className='dropdown filter-button'>
                                <button className='btn btn-secondary dropdown-toggle' type='button' id='dropdownMenuButton1' data-bs-toggle='dropdown' aria-expanded='false'>
                                    { option === "" ? "Sort By" : option }
                                </button>
                                <ul className='dropdown-menu' aria-labelledby='dropdownMenuButton1'>
                                    <li><a className='dropdown-item' onClick={handleNewest} >Newest</a></li>
                                    <li><a className='dropdown-item' onClick={handlePriceAsc}>Price ascending</a></li>
                                    <li><a className='dropdown-item' onClick={handlePriceDesc} >Price descending</a></li>
                                    <li><a className='dropdown-item' onClick={handleKmAsc} >Km ascending</a></li>
                                    <li><a className='dropdown-item' onClick={handleKmDesc} >Km descending</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div className='d-flex flex-wrap'>
                    {currentData.map((product, index) => (
                        <Link to={`/${product.id}`} key={index} className='container'>
                            <ProductCard key={index} id={product.id} product={product}/>
                        </Link>
                    ))}
                </div>
            </div>

            <Pagination
                currentPage={currentPage}
                totalPosts={Math.ceil(products.length / itemsPerPage)}
                paginate={handlePageChange}
                nextPage={() => handlePageChange(currentPage + 1)}
                prevPage={() => handlePageChange(currentPage - 1)} />
        </div>
    )
}

export default Store