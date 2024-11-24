import Navbar from "../layout/Navbar";

export default function Contact() {
  return (
    <div className="Contact">
      <Navbar />
      <form>
        <div className="w-10/12 sm:w-8/12 md:w-6/12 lg:w-4/12 xl:w-3/12 mx-auto mt-32 p-7 py-11 rounded-lg bg-gray-800 flex flex-col items-center gap-7 text-white">
          <h1 className="text-3xl font-semibold mb-14">Contact Us</h1>
          <input
            className="w-10/12 rounded-md bg-gray-900 focus:border-gray-100"
            placeholder="Name..."
            type="text"
            name="name"
            required
          />

          <input
            className="w-10/12 rounded-md bg-gray-900 focus:border-gray-100"
            placeholder="Email..."
            type="email"
            name="email"
            required
          />
          <textarea
            className="w-10/12 rounded-md bg-gray-900 focus:border-gray-100"
            placeholder="Message..."
            rows={6}
            name="message"
            required
          />
          <button
            className="p-3 px-9 mt-14 bg-gray-600 hover:bg-gray-500 transition rounded-md"
            type="submit"
          >
          Submit
          </button>
        </div>
      </form>
    </div>
  );
}
