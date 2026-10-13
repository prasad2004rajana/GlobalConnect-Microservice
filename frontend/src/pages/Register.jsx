import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api";

export default function Register() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      await api.post("/auth/register", { username, email, password });
      navigate("/login");
    } catch (err) {
      setError(err.response?.data?.message || "Registration failed");
    }
  };

  return (
    <div style={{ maxWidth: 360, margin: "60px auto" }}>
      <h2>Register</h2>
      <form onSubmit={handleSubmit}>
        <input placeholder="Username" value={username}
               onChange={(e) => setUsername(e.target.value)} /><br /><br />
        <input placeholder="Email" value={email}
               onChange={(e) => setEmail(e.target.value)} /><br /><br />
        <input type="password" placeholder="Password" value={password}
               onChange={(e) => setPassword(e.target.value)} /><br /><br />
        <button type="submit">Register</button>
      </form>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <p>Have an account? <Link to="/login">Login</Link></p>
    </div>
  );
}