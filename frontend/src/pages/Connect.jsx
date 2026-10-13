import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../api";
import { useAuth } from "../AuthContext";
import Avatar from "../components/Avatar";

export default function Connect() {
  const { user } = useAuth();
  const [q, setQ] = useState("");
  const [people, setPeople] = useState([]);
  const [followed, setFollowed] = useState({});

  // debounce: wait 300ms after the last keystroke before calling the API
  useEffect(() => {
    const timer = setTimeout(() => {
      const query = q.trim();
      const req =
        query.length >= 2
          ? api.get(`/auth/users/search?q=${encodeURIComponent(query)}`)
          : api.get("/auth/users/recent");
      req.then((r) => setPeople(r.data));
    }, 300);
    return () => clearTimeout(timer);
  }, [q]);

  const follow = async (id) => {
    await api.post(`/follows/${id}`);
    setFollowed((f) => ({ ...f, [id]: true }));
  };

  return (
    <>
      <div className="card">
        <input placeholder="Search people..." value={q} onChange={(e) => setQ(e.target.value)} />
      </div>
      {people.filter((u) => u.id !== user.id).map((u) => (
        <div className="card row" key={u.id}>
          <Link to={`/profile/${u.id}`} className="author">
            <Avatar name={u.username} />
            <b>{u.username}</b>
          </Link>
          <button disabled={followed[u.id]} onClick={() => follow(u.id)}>
            {followed[u.id] ? "Following ✓" : "👋 Follow"}
          </button>
        </div>
      ))}
      {people.length === 0 && <p className="muted">No users found.</p>}
    </>
  );
}