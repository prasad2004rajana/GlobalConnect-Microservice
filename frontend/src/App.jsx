import { Link, Navigate, Route, Routes, useNavigate } from "react-router-dom";
import { useAuth } from "./AuthContext";
import BottomNav from "./components/BottomNav";
import Connect from "./pages/Connect";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Profile from "./pages/Profile";
import Register from "./pages/Register";

function Layout({ children }) {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  return (
    <>
      <div className="topbar">
        <span>GlobalConnect</span>
        {user ? (
          <button className="link" onClick={() => { logout(); navigate("/"); }}>Logout</button>
        ) : (
          <div className="row">
            <Link to="/login">Login</Link>
            <Link to="/register" className="btn">Register</Link>
          </div>
        )}
      </div>
      <div className="container">{children}</div>
      <BottomNav />
    </>
  );
}

function Protected({ children }) {
  const { user } = useAuth();
  if (!user) return <Navigate to="/login" replace />;
  return <Layout>{children}</Layout>;
}

export default function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/" element={<Layout><Home /></Layout>} />
      <Route path="/connect" element={<Protected><Connect /></Protected>} />
      <Route path="/profile/:id" element={<Protected><Profile /></Protected>} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}