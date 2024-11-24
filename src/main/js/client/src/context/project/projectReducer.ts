import {
  ProjectStateType,
  ActionType,
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

const projectReducer = (
  state: ProjectStateType,
  action: ActionType,
): ProjectStateType => {
  switch (action.type) {
    case PROJECTS_LOADED:
      return {
        ...state,
        loading: false,
        projects: action.payload,
      };
    case PROJECT_EDITED:
    case PROJECT_POST_SUCCESSFUL:
      return {
        ...state,
        loading: false,
      };
    case PROJECT_FOUND:
      return {
        ...state,
        loading: false,
        project_info: action.payload,
      };
    case PROJECT_DELETED:
      return {
        ...state,
        loading: false,
        project_info: null,
      };
    case RESET_ERROR_PROJECT:
      return {
        ...state,
        error: null,
      };
    case PROJECT_PRESET:
      return {
        ...state,
        project_info: action.payload,
      };
    case PROJECT_LOAD_FAIL:
    case PROJECT_POST_FAIL:
    case PROJECT_EDIT_FAIL:
    case PROJECT_NOT_FOUND:
    case PROJECT_DELETE_FAIL:
      return {
        ...state,
        loading: false,
        projects: null,
        project_info: null,
        error: action.payload,
      };
    default:
      return state;
  }
};

export default projectReducer;
