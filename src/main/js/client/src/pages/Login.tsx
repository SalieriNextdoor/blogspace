import Navbar from "../layout/Navbar";

export default function Login() {
  return (
    <div className="Login">
      <Navbar />
      <form action="">
        <div className="w-10/12 sm:w-8/12 md:w-6/12 lg:w-4/12 xl:w-3/12 mx-auto mt-32 p-7 py-11 rounded-lg bg-gray-800 flex flex-col items-center gap-7 text-white">
          <h1 className="text-3xl font-semibold mb-14">Login</h1>
          <input
            className="w-10/12 rounded-md bg-gray-900 focus:border-gray-100"
            placeholder="Email..."
            type="email"
          />
          <input
            className="w-10/12 rounded-md bg-gray-900 focus:border-gray-100"
            placeholder="Password..."
            type="password"
          />
          <button
            className="p-3 px-9 mt-14 bg-gray-600 hover:bg-gray-500 rounded-md"
            type="submit"
          >
            Login 
          </button>
        </div>
      </form>
    </div>
  );
}
