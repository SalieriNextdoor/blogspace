import React, { useReducer } from "react";
import axios from "axios";
import AuthContext from "./authContext";
import authReducer from "./authReducer";
import setAuthToken from "../../util/setAuthToken";
import {
  AuthStateType,
  AuthActionType,
  AuthStateProps,
  USER_LOADED,
  AUTH_ERROR,
  LOGIN_SUCCESS,
  LOGIN_FAIL,
  LOGOUT,
  SIGNUP_SUCCESS,
  SIGNUP_FAIL,
} from "../types";

const AuthState: React.FC<AuthStateProps> = (props) => {
  const initialState: AuthStateType = {
    token: localStorage.getItem("token"),
    isAuthenticated: false,
    user: null,
    error: null,
    loading: true,
  };

  const [state, dispatch] = useReducer<
    React.Reducer<AuthStateType, AuthActionType>
  >(authReducer, initialState);

  // Load user
  const loadUser = async () => {
    if (localStorage.token) setAuthToken(localStorage.token);

    try {
      const res = await axios.get("/api/user/load");
      dispatch({ type: USER_LOADED, payload: res.data });
    } catch (err: any) {
      dispatch({ type: AUTH_ERROR, payload: err.data });
    }
  };

  // Log in user
  const login = async (formData: Record<string, string>) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };

    try {
      const res = await axios.post('/api/user/auth', formData, config);
      console.log(LOGIN_SUCCESS);
      dispatch({ type: LOGIN_SUCCESS, payload: res.data });
    } catch (err: any) {
      console.error(LOGIN_FAIL);
      dispatch({ type: LOGIN_FAIL, payload: err.response?.data });
    }
  };

  // Sign up user
  const signup = async (formData: Record<string, string>) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };

    try {
      const res = await axios.post("/api/user/", formData, config);
      dispatch({ type: SIGNUP_SUCCESS, payload: res.data });
    } catch (err: any) {
      dispatch({ type: SIGNUP_FAIL, payload: err.response?.data });
    }
  };

  const logout = () => dispatch({ type: LOGOUT });

  return (
    <AuthContext.Provider
      value={{
        token: state.token,
        isAuthenticated: state.isAuthenticated,
        loading: state.loading,
        user: state.user,
        error: state.error,
        // functions
        loadUser,
        login,
        signup,
        logout,
      }}
    >
      {props.children}
    </AuthContext.Provider>
  );
};

export default AuthState;
