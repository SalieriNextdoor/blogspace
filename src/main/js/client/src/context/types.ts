import { ReactNode } from "react";

export const USER_LOADED = "USER_LOADED";
export const AUTH_ERROR = "AUTH_ERROR";
export const LOGIN_SUCCESS = "LOGIN_SUCCESS";
export const LOGIN_FAIL = "LOGIN_FAIL";
export const SIGNUP_SUCCESS = "SIGNUP_SUCESS";
export const SIGNUP_FAIL = "SIGNUP_FAIL";
export const LOGOUT = "LOGOUT";

export const PROJECTS_LOADED = "PROJECTS_LOADED";
export const PROJECT_LOAD_FAIL = "PROJECT_LOAD_FAIL";
export const PROJECT_FOUND = "PROJECT_FOUND";
export const PROJECT_NOT_FOUND = "PROJECT_NOT_FOUND";
export const PROJECT_EDITED = "PROJECT_EDITED";
export const PROJECT_EDIT_FAIL = "PROJECT_EDIT_FAIL";
export const PROJECT_DELETED = "PROJECT_DELETED";
export const PROJECT_DELETE_FAIL = "PROJECT_DELETE_FAIL";
export const RESET_ERROR_PROJECT = "RESET_ERROR_PROJECT";
export const PROJECT_POST_FAIL = "PROJECT_POST_FAIL";
export const PROJECT_POST_SUCCESSFUL = "PROJECT_POST_SUCCESSFUL";
export const PROJECT_PRESET = "PROJECT_PRESET";

export const POSTS_LOADED = "POSTS_LOADED";
export const POST_LOAD_FAIL = "POST_LOAD_FAIL";
export const POST_FOUND = "POST_FOUND";
export const POST_NOT_FOUND = "POST_NOT_FOUND";
export const POST_EDITED = "POST_EDITED";
export const POST_EDIT_FAIL = "POST_EDIT_FAIL";
export const POST_DELETED = "POST_DELETED";
export const POST_DELETE_FAIL = "POST_DELETE_FAIL";
export const RESET_ERROR_POST = "RESET_ERROR_POST";
export const POST_FAIL = "POST_FAIL";
export const POST_SUCCESSFUL = "POST_SUCCESSFUL";
export const POST_PRESET = "POST_PRESET";
export const SET_LOADING = "SET_LOADING";

export interface AuthStateType {
  token: string | null;
  isAuthenticated: boolean;
  user: Record<string, any> | null;
  error: any;
  loading: boolean;
}

export interface ProjectStateType {
  projects: Array<Record<string, any>> | null;
  project_info: Record<string, any> | null;
  error: any;
  loading: boolean;
}

export interface PostStateType {
  posts: Array<Record<string, any>> | null;
  post_info: Record<string, any> | null;
  error: any;
  loading: boolean;
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
  logout: () => Promise<void>;
}

export interface ProjectContextType {
  projects: Array<Record<string, any>> | null;
  project_info: Record<string, any> | null;
  error: any;
  loading: boolean;
  // functions
  loadProjects: () => Promise<void>;
  loadProject: (project_id: string) => Promise<void>;
  postProject: (projectData: Record<string, string>) => Promise<void>;
  editProject: (
    projectData: Record<string, string>,
    project_id: string,
  ) => Promise<void>;
  deleteProject: (project_id: string) => Promise<void>;
  resetError: () => Promise<void>;
  preSetProjectInfo: (project_data: Record<string, string>) => Promise<void>;
}

export interface PostContextType {
  posts: Array<Record<string, any>> | null;
  post_info: Record<string, any> | null;
  error: any;
  loading: boolean;
  // functions
  loadPostsFromProject: (project_id: string) => Promise<void>;
  loadPost: (project_id: string, post_id: string) => Promise<void>;
  postPost: (
    project_id: string,
    postData: Record<string, string>,
  ) => Promise<void>;
  editPost: (
    project_id: string,
    post_id: string,
    postData: Record<string, string>,
  ) => Promise<void>;
  deletePost: (project_id: string, post_id: string) => Promise<void>;
  preSetPostInfo: (post_data: Record<string, string>) => Promise<void>;
  setLoading: () => Promise<void>;
  resetError: () => Promise<void>;
}

export interface ActionType {
  type: string;
  payload?: any;
}

export interface StateProps {
  children: ReactNode;
}
