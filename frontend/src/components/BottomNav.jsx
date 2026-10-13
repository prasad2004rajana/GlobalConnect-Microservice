import { NavLink } from "react-router-dom";
import { useAuth } from "../AuthContext";

export default function BottomNav() {
  const { user, requireLogin } = useAuth();
  const gate = (e) => {
    if (!user) {
      e.preventDefault();
      requireLogin();
    }
  };

  return (
    <nav className="bottomnav">
      <NavLink to="/" end>🪐<span>Moments</span></NavLink>
      <NavLink to="/connect" onClick={gate}>👥<span>Connect</span></NavLink>
      <NavLink to={user ? `/profile/${user.id}` : "/login"} onClick={gate}>👤<span>Me</span></NavLink>
    </nav>
  );
}