import { useState } from "react";
import { Link } from "react-router-dom";
import api from "../api";
import Avatar from "../components/Avatar";


export default function Search() {
  const [q, setQ] = useState("");
  const [results, setResults] = useState(null);

  const search = async (e) => {
    e.preventDefault();
    if (q.trim().length < 2) return;
    const res = await api.get(`/auth/users/search?q=${encodeURIComponent(q.trim())}`);
    setResults(res.data);
  };

  return (
    <>
      <form className="card row" onSubmit={search}>
        <input placeholder="Search people by username (min 2 letters)" value={q}
               onChange={(e) => setQ(e.target.value)} />
        <button type="submit">Search</button>
      </form>
      {results && results.length === 0 && <p className="muted">No users found.</p>}
      {results && results.map((u) => (
        <div className="card" key={u.id}>
<Link to={`/profile/${u.id}`} className="author">
  <Avatar name={u.username} />
  <b>{u.username}</b>
</Link>        </div>
      ))}
    </>
  );
}