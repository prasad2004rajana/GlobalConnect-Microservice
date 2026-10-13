import { useEffect, useState } from "react";
import api from "../api";
import { useAuth } from "../AuthContext";
import CreatePost from "../components/CreatePost";
import PostCard from "../components/PostCard";

export default function Home() {
  const { user, requireLogin } = useAuth();
  const [tab, setTab] = useState("recent");
  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(0);
  const [last, setLast] = useState(true);
  const [loading, setLoading] = useState(false);

  const endpoint = tab === "recent" ? "/posts/recent" : "/posts/feed";

  const switchTab = (t) => {
    if (t === "following" && !requireLogin()) return;
    setTab(t);
  };

  const load = async (pageNum) => {
    setLoading(true);
    try {
      const res = await api.get(`${endpoint}?page=${pageNum}&size=10`);
      setPosts((prev) => (pageNum === 0 ? res.data.content : [...prev, ...res.data.content]));
      setPage(pageNum);
      setLast(res.data.last);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    setPosts([]);
    load(0);
  }, [tab]);

  return (
    <>
      <div className="tabs">
        <button className={`tab ${tab === "recent" ? "active" : ""}`} onClick={() => switchTab("recent")}>
          Recent
        </button>
        <button className={`tab ${tab === "following" ? "active" : ""}`} onClick={() => switchTab("following")}>
          Following
        </button>
      </div>

      {user ? (
        <CreatePost onCreated={(p) => setPosts((prev) => [p, ...prev])} />
      ) : (
        <div className="card row">
          <span className="muted-text">Join the conversation</span>
          <button onClick={requireLogin}>Login to post</button>
        </div>
      )}

      {posts.map((p) => (
        <PostCard
          key={p.id}
          post={p}
          onDeleted={(id) => setPosts((prev) => prev.filter((x) => x.id !== id))}
        />
      ))}

      {!loading && posts.length === 0 && (
        <p className="muted">
          {tab === "following"
            ? "Follow people from the Connect tab to fill this feed."
            : "No posts yet."}
        </p>
      )}

      {!last && (
        <button className="more" disabled={loading} onClick={() => load(page + 1)}>
          {loading ? "Loading..." : "Load more"}
        </button>
      )}
    </>
  );
}