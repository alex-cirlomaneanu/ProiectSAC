import React from 'react';

const Pagination = (props) => {
    const { currentPage, totalPosts, paginate, nextPage, prevPage } = props;

    const pageNumbers = [];

    for (let i = 1; i <= totalPosts; i++) {
        pageNumbers.push(i);
    }

    const scrollToTop = () => {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };



    return (
        <nav>
            <ul className="pagination justify-content-center">
                <li className="page-item">
                    <a className="page-link" onClick={() => {prevPage(); scrollToTop(); }}>Previous</a>
                </li>
                {pageNumbers.map((num, index) => {
                    if (pageNumbers.length > 2) {
                        if (
                            index === 0 || // prima pagină
                            index === pageNumbers.length - 1 || // ultima pagină
                            (index >= currentPage - 2 && index <= currentPage + 2) // paginile adiacente paginii curente
                        ) {
                            return (
                                <li className="page-item" key={num}>
                                    <a onClick={() => {paginate(num); scrollToTop();}} className="page-link">{num}</a>
                                </li>
                            );
                        } else if (
                            index === currentPage - 3 || // pagina în spate
                            index === currentPage + 3 // pagina în față
                        ) {
                            return (
                                <li className="page-item" key={num}>
                                    <span className="page-link">...</span>
                                </li>
                            );
                        }
                    } else {
                        return (
                            <li className="page-item" key={num}>
                                <a onClick={() => {paginate(num); scrollToTop();}} className="page-link">{num}</a>
                            </li>
                        );
                    }
                })}
                <li className="page-item">
                    <a className="page-link" onClick={() => {nextPage(); scrollToTop();}}>Next</a>
                </li>
            </ul>
        </nav>

    );
};

export default Pagination;