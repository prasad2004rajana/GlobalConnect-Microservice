import { useNavigate } from "react-router-dom";

export default function LoginPrompt({ open, onClose }) {
  const navigate = useNavigate();
  if (!open) return null;

  const go = (path) => {
    onClose();
    navigate(path);
  };

  return (
    <div className="overlay" onClick={onClose}>
      <div className="modal" onClick={(e) => e.stopPropagation()}>
        <h3>Join GlobalConnect</h3>
        <p className="muted-text">Login or create an account to like, comment, follow and post.</p>
        <div className="row">
          <button onClick={() => go("/login")}>Login</button>
          <button className="secondary" onClick={() => go("/register")}>Register</button>
        </div>
      </div>
    </div>
  );
}