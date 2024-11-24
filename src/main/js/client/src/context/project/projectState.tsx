import React, { useReducer } from "react";
import axios from "axios";
import ProjectContext from "./projectContext";
import projectReducer from "./projectReducer";
import {
  ProjectStateType,
  ActionType,
  StateProps,
  PROJECTS_LOADED,
  PROJECT_LOAD_FAIL,
  PROJECT_FOUND,
  PROJECT_NOT_FOUND,
  PROJECT_EDITED,
  PROJECT_EDIT_FAIL,
  PROJECT_DELETED,
  PROJECT_DELETE_FAIL,
  RESET_ERROR_PROJECT,
  PROJECT_POST_FAIL,
  PROJECT_POST_SUCCESSFUL,
  PROJECT_PRESET,
} from "../types";

const ProjectState: React.FC<StateProps> = (props) => {
  const initialState: ProjectStateType = {
    projects: null,
    project_info: null,
    error: null,
    loading: true,
  };

  const [state, dispatch] = useReducer<
    React.Reducer<ProjectStateType, ActionType>
  >(projectReducer, initialState);

  const loadProjects = async () => {
    try {
      const res = await axios.get("/api/project/");
      dispatch({ type: PROJECTS_LOADED, payload: res.data });
    } catch (err: any) {
      console.error(err);
      dispatch({ type: PROJECT_LOAD_FAIL, payload: err.data });
    }
  };

  const loadProject = async (project_id: string) => {
    try {
      const res = await axios.get(`/api/project/${project_id}`);
      dispatch({ type: PROJECT_FOUND, payload: res.data });
    } catch (err: any) {
      console.error(err);
      dispatch({ type: PROJECT_NOT_FOUND, payload: err.data });
    }
  };

  const postProject = async (projectData: Record<string, string>) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };

    try {
      const res = await axios.post("/api/project", projectData, config);
      dispatch({ type: PROJECT_POST_SUCCESSFUL, payload: res.data });
    } catch (err: any) {
      console.error(err);
      dispatch({ type: PROJECT_POST_FAIL, payload: err.data });
    }
  };

  const editProject = async (
    projectData: Record<string, string>,
    project_id: string,
  ) => {
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };

    try {
      const res = await axios.put(
        `/api/project/${project_id}`,
        projectData,
        config,
      );
      dispatch({ type: PROJECT_EDITED, payload: res.data });
    } catch (err: any) {
      console.error(err);
      dispatch({ type: PROJECT_EDIT_FAIL, payload: err.data });
    }
  };

  const deleteProject = async (project_id: string) => {
    try {
      const res = await axios.delete(`/api/project/${project_id}`);
      dispatch({ type: PROJECT_DELETED, payload: res.data });
    } catch (err: any) {
      console.error(err);
      dispatch({ type: PROJECT_DELETE_FAIL, payload: err.data });
    }
  };

  const resetError = async () =>
    dispatch({ type: RESET_ERROR_PROJECT, payload: null });

  const preSetProjectInfo = async (project_data: Record<string, string>) =>
    dispatch({ type: PROJECT_PRESET, payload: project_data });

  return (
    <ProjectContext.Provider
      value={{
        projects: state.projects,
        project_info: state.project_info,
        error: state.error,
        loading: state.loading,
        // functions
        loadProjects,
        loadProject,
        postProject,
        editProject,
        deleteProject,
        resetError,
        preSetProjectInfo,
      }}
    >
      {props.children}
    </ProjectContext.Provider>
  );
};

export default ProjectState;
