import SpinnerGif from "../img/spinner.gif";

export default function Spinner() {
  return (
    <div className="absolute inset-0 flex items-center justify-center">
      <img src={SpinnerGif} alt="" />
    </div>
  );
}
