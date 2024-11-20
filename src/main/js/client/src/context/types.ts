import { ReactNode } from "react";

export const USER_LOADED = "USER_LOADED";
export const AUTH_ERROR = "AUTH_ERROR";
export const LOGIN_SUCCESS = "LOGIN_SUCCESS";
export const LOGIN_FAIL = "LOGIN_FAIL";
export const SIGNUP_SUCCESS = "SIGNUP_SUCESS";
export const SIGNUP_FAIL = "SIGNUP_FAIL";
export const LOGOUT = "LOGOUT";

export interface AuthStateType {
  token: string | null;
  isAuthenticated: boolean;
  user: Record<string, any> | null;
  error: any;
  loading: boolean;
}

export interface AuthActionType {
  type: string;
  payload?: any;
}

export interface AuthContextType {
  token: string | null;
  isAuthenticated: boolean;
  loading: boolean;
  user: Record<string, any> | null;
  error: any;
  // functions
  loadUser: () => Promise<void>;
  login: (formData: Record<string, string>) => Promise<void>;
  signup: (formData: Record<string, string>) => Promise<void>;
  logout: () => void;
}

export interface AuthStateProps {
  children: ReactNode;
}
