import React, {useEffect} from 'react';
import './App.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import Layout from './components/Layout';
import Home from './pages/Home/Home';
import Singup from "./pages/Singup";
import Login from "./pages/Login";
import Store from "./pages/Store/Store";
import axios from "axios";
import {getAllVehicles} from "./pages/constants/Endpoints";

function App() {
    useEffect(() => {
        axios.get(getAllVehicles).then(response => {
            localStorage.setItem('products', JSON.stringify(response.data));
        })
        .catch(error => {
            console.log(error);
        });
    }, []);

  return (
      <>
        <BrowserRouter>
          <Routes>
            <Route path='/' element={<Layout/>}>
                <Route path="login" element={<Login/>} />
                <Route path="singup" element={<Singup/>} />
                <Route path="store" element={<Store/>} />
            </Route>
            <Route index element={<Home/>}/>
          </Routes>
        </BrowserRouter>
      </>
  );
}

export default App;
