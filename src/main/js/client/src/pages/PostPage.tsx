import { useEffect, useContext } from "react";
import { useParams } from "react-router";
import { Link } from "react-router-dom";

import PostContext from "../context/posts/postContext";

import Navbar from "../layout/Navbar";
import Spinner from "../layout/Spinner";

export default function PostPage() {
  const { post_info, loading, loadPost } = useContext(PostContext)!;

  const params = useParams();

  useEffect(() => {
    if (!post_info) loadPost(params.project_id!, params.post_id!);

    // eslint-disable-next-line
  }, []);

  return (
    <div className="PostPage">
      <Navbar />
      {!loading && post_info ? (
        <>
          <div className="bg-white min-h-screen py-8 px-4">
            <div className="max-w-3xl mx-auto">
              <Link
                to={`/projects/${params.project_id}`}
                className="inline-block text-gray-800 bg-gray-200 px-4 py-2 rounded-lg shadow hover:bg-gray-300 transition"
              >
                ← Back to Project
              </Link>

              <div className="mt-8">
                <h1 className="text-gray-800 text-4xl font-bold mb-6">
                  {post_info.title} 
                </h1>

                <img
                  src={post_info.image}
                  style={{ height: 351, width: 585 }}
                  alt=""
                  className="w-full rounded-lg shadow-md object-cover mb-6 mx-auto"
                />

                <p className="text-gray-700 text-lg leading-relaxed">
                  {post_info.text} 
                </p>
              </div>
            </div>
          </div>
        </>
      ) : (
        <Spinner />
      )}
    </div>
  );
}
