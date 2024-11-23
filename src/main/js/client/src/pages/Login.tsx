import React, { useState, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "../context/auth/authContext";
import Navbar from "../layout/Navbar";

export default function Login() {
  const { loading, isAuthenticated, login, error } = useContext(AuthContext)!;
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const navigate = useNavigate();

  useEffect(() => {
    if (!loading && isAuthenticated) navigate("/", { replace: true });
  }, [loading, isAuthenticated, navigate]);

  const { email, password } = formData;

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await login({ email, password });
    navigate("/", { replace: true });
  };

  useEffect(() => {
    if (error) console.error("Error: ", error);
  }, [error]);

  return (
    <div className="Login">
      <Navbar />
      <form onSubmit={onSubmit}>
        <div className="w-10/12 sm:w-8/12 md:w-6/12 lg:w-4/12 xl:w-3/12 mx-auto mt-32 p-7 py-11 rounded-lg bg-gray-800 flex flex-col items-center gap-7 text-white">
          <h1 className="text-3xl font-semibold mb-14">Login</h1>
          <input
            className="w-10/12 rounded-md bg-gray-900 focus:border-gray-100"
            placeholder="Email..."
            type="email"
            name="email"
            value={email}
            onChange={onChange}
            required
          />
          <input
            className="w-10/12 rounded-md bg-gray-900 focus:border-gray-100"
            placeholder="Password..."
            type="password"
            name="password"
            value={password}
            onChange={onChange}
            required
          />
          <button
            className="p-3 px-9 mt-14 bg-gray-600 hover:bg-gray-500 rounded-md"
            type="submit"
          >
            Login
          </button>
        </div>
      </form>
    </div>
  );
}
