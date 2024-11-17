import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import Projects from "./pages/Projects";
import Contact from './pages/Contact';
import Login from './pages/Login';
import SignUp from './pages/SignUp';

export default function App() {
  return <Routes>
    <Route path="/" element={<Home />} ></Route>
    <Route path="/projects" element={<Projects />} ></Route>
    <Route path="/contact" element={<Contact />} ></Route>
    <Route path="/login" element={<Login />} ></Route>
    <Route path="/signup" element={<SignUp />} ></Route>
  </Routes>;
}
