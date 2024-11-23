import { useEffect, useContext } from "react";
import { NavLink } from "react-router-dom";
import AuthContext from "../context/auth/authContext";

export default function Navbar() {
  const { logout, loadUser, loading, isAuthenticated, error } =
    useContext(AuthContext)!;

  useEffect(() => {
    if (!isAuthenticated) loadUser();
    // eslint-disable-next-line
  }, []);

  useEffect(() => {
    if (error) console.error("Error: ", error);
  }, [error]);

  const onClickLogout = () => logout();

  const getLinkClass = (isActive: boolean) =>
    isActive ? "text-white font-semibold" : "text-gray-400";

  return (
    <div className="Navbar">
      <nav className="bg-gray-800 h-20 w-screen">
        <div className="h-full flex w-9/12 justify-between mx-auto">
          <div className="h-full flex items-center">
            <NavLink to="/">
              <div className="font-logo text-white text-5xl">B</div>
            </NavLink>
          </div>
          <div className="h-full items-center hidden md:flex">
            <ul className="flex align-middle gap-7 text-xl">
              <NavLink
                to="/"
                className={({ isActive }) => getLinkClass(isActive)}
              >
                <li className="hover:text-white">Home</li>
              </NavLink>
              <NavLink
                to="/projects"
                className={({ isActive }) => getLinkClass(isActive)}
              >
                <li className="hover:text-white">Projects</li>
              </NavLink>
              <NavLink
                to="/contact"
                className={({ isActive }) => getLinkClass(isActive)}
              >
                <li className="hover:text-white">Contact</li>
              </NavLink>
            </ul>
          </div>
          <div className="h-full flex items-center hidden md:flex">
            <ul className="flex inline align-middle columns-2 gap-7 text-xl">
              {!loading && isAuthenticated ? (
                <>
                  <button
                    onClick={onClickLogout}
                    className="text-gray-400"
                  >
                    <li className="hover:text-white">Logout</li>
                  </button>
                </>
              ) : (
                <>
                  <NavLink
                    to="/login"
                    className={({ isActive }) => getLinkClass(isActive)}
                  >
                    <li className="hover:text-white">Login</li>
                  </NavLink>
                  <NavLink
                    to="/signup"
                    className={({ isActive }) => getLinkClass(isActive)}
                  >
                    <li className="hover:text-white">Sign Up</li>
                  </NavLink>
                </>
              )}
            </ul>
          </div>
        </div>
      </nav>
    </div>
  );
}
