import React from 'react';
import './App.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import Layout from './components/Layout';
import Home from './pages/Home';
import Singup from "./pages/Singup";
import Login from "./pages/Login";

function App() {
  return (
      <>
        <BrowserRouter>
          <Routes>
            <Route path='/' element={<Layout/>}>
                <Route path="login" element={<Login/>} />
                <Route path="singup" element={<Singup/>} />
            </Route>
            <Route index element={<Home/>}/>
          </Routes>
        </BrowserRouter>
      </>
  );
}

export default App;
