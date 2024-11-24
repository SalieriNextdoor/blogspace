import { useEffect, useContext } from "react";
import { Link } from "react-router-dom";

import ProjectContext from "../context/project/projectContext";

import Navbar from "../layout/Navbar";
import Spinner from "../layout/Spinner"

export default function Projects() {
  const { projects, loading, loadProjects, preSetProjectInfo } =
    useContext(ProjectContext)!;

  useEffect(() => {
    loadProjects();

    // eslint-disable-next-line
  }, []);

  function truncateStr(str: string, maxLength: number) {
    if (str.length > maxLength) return str.slice(0, maxLength) + "...";
    return str;
  }

  const onClickProject = (id: number) => {
    const project = projects!.find(project => project.id === id);
    preSetProjectInfo(project!);
  }

  return (
    <div className="Projects">
      <Navbar />
      <div className="mt-16">
        <div className="flex flex-col align-center justify-center">
          <h1 className="text-3xl font-bold underline text-center">Projects</h1>
          {!loading && projects ? (
            <div className="flex justify-center mt-8">
              <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-12">
                {projects
                  .sort((x, y) => y.id - x.id)
                  .map((project, idx) => {
                    return (
                      <div
                        key={idx}
                        className="max-w-sm border bg-gray-800 border-gray-700 rounded-lg"
                        style={{ maxWidth: 250 }}
                        onClick={() => onClickProject(project.id)}
                      >
                        <Link
                          to={`${project.id}`}
                        >
                          <img
                            src={project.image}
        alt=""
          style={{ height: 150, width: 250, minWidth: 250, minHeight: 250 }}
                            className="w-full rounded-lg"
                          />
                        </Link>
                        <div className="p-5">
                          <Link
                                                        to={`${project.id}`}
                          >
                            <h5 className="text-white font-semibold text-2xl mb-2 tracking-tight hover:font-bold">
                              {project.title}
                            </h5>
                          </Link>
                          <p className="mb-3 font-normal text-gray-400">
                            {truncateStr(project.description, 97)}
                          </p>
                        </div>
                      </div>
                    );
                  })}
              </div>
            </div>
          ) : <Spinner />}
        </div>
      </div>
    </div>
  );
}
