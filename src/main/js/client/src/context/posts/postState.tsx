import React, { useReducer } from "react";
import axios from "axios";
import PostContext from "./postContext";
import postReducer from "./postReducer";
import {
  PostStateType,
  ActionType,
  StateProps,
  POSTS_LOADED,
  POST_LOAD_FAIL,
  POST_FOUND,
  POST_NOT_FOUND,
  POST_EDITED,
  POST_EDIT_FAIL,
  POST_DELETED,
  POST_DELETE_FAIL,
  RESET_ERROR_POST,
  POST_FAIL,
  POST_SUCCESSFUL,
  POST_PRESET,
  SET_LOADING,
} from "../types";

const PostState: React.FC<StateProps> = (props) => {
  const initialState: PostStateType = {
    posts: null,
    post_info: null,
    error: null,
    loading: true,
  };

  const [state, dispatch] = useReducer<
    React.Reducer<PostStateType, ActionType>
  >(postReducer, initialState);

  const loadPostsFromProject = async (project_id: string) => {
    try {
      const res = await axios.get(`/api/project/${project_id}/posts`);
      dispatch({ type: POSTS_LOADED, payload: res.data });
    } catch (err: any) {
      console.error(err);
      dispatch({ type: POST_LOAD_FAIL, payload: err.data });
    }
  };

  const loadPost = async (project_id: string, post_id: string) => {
    try {
      const res = await axios.get(
        `/api/project/${project_id}/posts/${post_id}`,
      );
      dispatch({ type: POST_FOUND, payload: res.data });
    } catch (err: any) {
      console.error(err);
      dispatch({ type: POST_NOT_FOUND, payload: err.data });
    }
  };

  const postPost = async (
    project_id: string,
    postData: Record<string, string>,
  ) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };

    try {
      const res = await axios.post(
        `/api/project/${project_id}/posts`,
        postData,
        config,
      );
      dispatch({ type: POST_SUCCESSFUL, payload: res.data });
    } catch (err: any) {
      console.error(err);
      dispatch({ type: POST_FAIL, payload: err.data });
    }
  };

  const editPost = async (
    project_id: string,
    post_id: string,
    postData: Record<string, string>,
  ) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };

    try {
      const res = await axios.put(
        `/api/project/${project_id}/posts/${post_id}`,
        postData,
        config,
      );
      dispatch({ type: POST_EDITED, payload: res.data });
    } catch (err: any) {
      console.error(err);
      dispatch({ type: POST_EDIT_FAIL, payload: err.data });
    }
  };

  const deletePost = async (project_id: string, post_id: string) => {
    try {
      const res = await axios.delete(
        `/api/project/${project_id}/posts/${post_id}`,
      );
      dispatch({ type: POST_DELETED, payload: res.data });
    } catch (err: any) {
      console.error(err);
      dispatch({ type: POST_DELETE_FAIL, payload: err.data });
    }
  };

  const preSetPostInfo = async (post_data: Record<string, string>) =>
    dispatch({ type: POST_PRESET, payload: post_data });

  const setLoading = async () => dispatch({ type: SET_LOADING, payload: null });

  const resetError = async () =>
    dispatch({ type: RESET_ERROR_POST, payload: null });

  return (
    <PostContext.Provider
      value={{
        posts: state.posts,
        post_info: state.post_info,
        error: state.error,
        loading: state.loading,
        // functions
        loadPostsFromProject,
        loadPost,
        postPost,
        setLoading,
        resetError,
        preSetPostInfo,
        editPost,
        deletePost,
      }}
    >
      {props.children}
    </PostContext.Provider>
  );
};

export default PostState;
