import { useEffect, useContext } from "react";
import { useParams } from "react-router";
import { Link } from "react-router-dom";
import PostContext from "../context/posts/postContext";

import ProjectContext from "../context/project/projectContext";

import Navbar from "../layout/Navbar";
import Spinner from "../layout/Spinner";

export default function ProjectPage() {
  const { project_info, loading, loadProject } = useContext(ProjectContext)!;
  const { preSetPostInfo } = useContext(PostContext)!;

  const params = useParams();

  useEffect(() => {
    if (!project_info) loadProject(params.project_id!);

    // eslint-disable-next-line
  }, []);

  function truncateStr(str: string, maxLength: number) {
    if (str.length > maxLength) return str.slice(0, maxLength) + "...";
    return str;
  }

  const onClickPost = (id: number) => {
    const post = project_info!.posts.find((post: any) => post.id === id);
    preSetPostInfo(post!);
  };

  return (
    <div className="ProjectPage">
      <Navbar />
      {!loading && project_info ? (
        <div className="bg-white min-h-screen py-8 px-4">
          <div className="max-w-4xl mx-auto">
            <div className="mb-8">
              <img
                src={project_info.image}
                alt=""
                style={{ height: 234, width: 390 }}
                className="w-full rounded-lg shadow-md object-cover mx-auto"
              />
            </div>

            <h1 className="text-gray-800 text-4xl font-bold mb-4">
              {project_info.title}
            </h1>

            <p className="text-gray-600 text-lg mb-20">
              {project_info.description}
            </p>

            <div>
              <h2 className="text-gray-800 text-2xl font-semibold mb-4 underline">
                Posts
              </h2>

              <div className="flex flex-col gap-6">
                {project_info.posts
                  .sort((x: any, y: any) => y.id - x.id)
                  .map((post: any, idx: number) => {
                    return (
                      <div
                        key={idx}
                        className="bg-gray-800 rounded-lg shadow-md p-4 flex gap-9"
                        onClick={() => onClickPost(post.id)}
                      >
                        <Link to={`posts/${post.id}`}>
                          <img
                            src={post.image}
                            alt=""
                            style={{
                              height: 150,
                              width: 250,
                              minWidth: 250,
                              minHeight: 150,
                            }}
                            className="w-full h-40 object-cover rounded-md mb-4"
                          />
                        </Link>
                        <div className="flex flex-col gap-2">
                          <Link to={`posts/${post.id}`}>
                            <h3 className="text-white text-3xl font-semibold hover:font-bold">
                              {post.title}
                            </h3>
                          </Link>
                          <p className="font-normal text-gray-300">
                            {truncateStr(post.text, 247)}
                          </p>
                        </div>
                      </div>
                    );
                  })}
              </div>
            </div>
          </div>
        </div>
      ) : (
        <Spinner />
      )}
    </div>
  );
}
