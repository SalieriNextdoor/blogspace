import {Link} from 'react-router-dom';

import Navbar from "../layout/Navbar";

export default function Home() {
  return (
    <div className="Home">
      <Navbar />
      <div className="bg-white min-h-screen">
        <div className="bg-gray-800 text-white text-center py-20">
          <h1 className="text-4xl font-bold mb-4">
            Share Your Scientific Project with the World
          </h1>
          <p className="text-xl mb-8">
            Create, post, and collaborate on groundbreaking scientific projects.
            Join a community of innovators.
          </p>
          <Link
            to="/projects"
            className="inline-block bg-gray-600 text-white px-6 py-3 rounded-lg text-lg font-semibold hover:bg-gray-500 transition"
          >
            Explore Projects
          </Link>
        </div>

        <div className="max-w-4xl mx-auto py-16 px-4 text-center">
          <h2 className="text-3xl font-semibold text-gray-800 mb-6">
            What is This Platform?
          </h2>
          <p className="text-lg text-gray-600 mb-3">
            Our platform is dedicated to helping researchers and scientists
            share their projects, collaborate with peers, and showcase their
            work to the world. Whether you're working on a new experiment, a
            groundbreaking theory, or a collaborative research paper, our
            platform is the place to make your work visible and connect with
            others.
          </p>
        </div>

        <div className="bg-gray-100 py-16 px-4">
          <div className="max-w-6xl mx-auto text-center">
            <h2 className="text-3xl font-semibold text-gray-800 mb-12">
              Why Choose Our Platform?
            </h2>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-12">
              <div className="bg-white p-8 rounded-lg shadow-lg">
                <h3 className="text-xl font-semibold text-gray-800 mb-4">
                  Collaborate with Experts
                </h3>
                <p className="text-gray-600">
                  Connect with like-minded individuals, researchers, and experts
                  in your field to collaborate on your project.
                </p>
              </div>
              <div className="bg-white p-8 rounded-lg shadow-lg">
                <h3 className="text-xl font-semibold text-gray-800 mb-4">
                  Share Your Discoveries
                </h3>
                <p className="text-gray-600">
                  Showcase your project’s progress, share results, and receive
                  feedback from the global scientific community.
                </p>
              </div>
              <div className="bg-white p-8 rounded-lg shadow-lg">
                <h3 className="text-xl font-semibold text-gray-800 mb-4">
                  Track Progress
                </h3>
                <p className="text-gray-600">
                  Keep track of milestones, document your research journey, and
                  organize your work in one easy-to-use platform.
                </p>
              </div>
            </div>
          </div>
        </div>

        <div className="bg-gray-800 text-white text-center py-16">
          <h2 className="text-3xl font-semibold mb-4">
            Ready to Share Your Project?
          </h2>
          <p className="text-lg mb-8">
            Start creating your scientific project today and join a community of
            innovators pushing the boundaries of knowledge.
          </p>
          <Link
            to="/projects"
            className="inline-block bg-gray-600 text-white px-6 py-3 rounded-lg text-lg font-semibold hover:bg-gray-500 transition"
          >
            Get Started
          </Link>
        </div>
      </div>
    </div>
  );
}
