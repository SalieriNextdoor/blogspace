import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import Projects from "./pages/Projects";
import Contact from "./pages/Contact";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";

import AuthState from "./context/auth/authState";
import ProjectState from "./context/project/projectState";
import PostState from "./context/posts/postState";
import ProjectPage from "./pages/ProjectPage";
import PostPage from "./pages/PostPage"

export default function App() {
  return (
    <PostState>
      <ProjectState>
        <AuthState>
          <Routes>
            <Route path="/" element={<Home />}></Route>
            <Route path="/projects" element={<Projects />}></Route>
            <Route path="/projects/:project_id" element={<ProjectPage />}></Route>
            <Route path="/projects/:project_id/posts/:post_id" element={<PostPage />}></Route>
            <Route path="/contact" element={<Contact />}></Route>
            <Route path="/login" element={<Login />}></Route>
            <Route path="/signup" element={<SignUp />}></Route>
          </Routes>
        </AuthState>
      </ProjectState>
    </PostState>
  );
}
