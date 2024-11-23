import React, { useState, useEffect, useContext } from "react";
import AuthContext from "../context/auth/authContext";
import Navbar from "../layout/Navbar";

export default function SignUp() {
  const { signup, error } = useContext(AuthContext)!;
  const [formData, setFormData] = useState({
    email: "",
    username: "",
    password: "",
    confirmPassword: "",
  });

  const { email, username, password, confirmPassword } = formData;

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (password !== confirmPassword) {
      console.error("Passwords do not match.");
      return;
    }
    await signup({ email, username, password });
  };

  useEffect(() => {
    if (error)
      console.error('Sign up error: ', error);
  }, [error]);

  return (
    <div className="SignUp">
      <Navbar />
      <form onSubmit={onSubmit}>
        <div className="w-10/12 sm:w-8/12 md:w-6/12 lg:w-4/12 xl:w-3/12 mx-auto mt-32 p-7 py-11 rounded-lg bg-gray-800 flex flex-col items-center gap-7 text-white">
          <h1 className="text-3xl font-semibold mb-8">Sign up</h1>
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
            placeholder="Username..."
            type="text"
            name="username"
            value={username}
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
          <input
            className="w-10/12 rounded-md bg-gray-900 focus:border-gray-100"
            placeholder="Confirm Password..."
            type="password"
            name="confirmPassword"
            value={confirmPassword}
            onChange={onChange}
            required
          />
          <button
            className="p-3 px-9 mt-8 bg-gray-600 hover:bg-gray-500 rounded-md"
            type="submit"
          >
            Sign up
          </button>
        </div>
      </form>
    </div>
  );
}
