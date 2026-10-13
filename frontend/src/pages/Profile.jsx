import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import api from "../api";
import { useAuth } from "../AuthContext";
import PostCard from "../components/PostCard";
import Avatar from "../components/Avatar";
import useUsername from "../useUsername";

export default function Profile() {
  const { id } = useParams();
  const userId = Number(id);
  const { user } = useAuth();
  const name = useUsername(userId);

  const [stats, setStats] = useState(null);
  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(0);
  const [last, setLast] = useState(true);
  const [list, setList] = useState(null); // "followers" | "following" | null
  const [people, setPeople] = useState([]);

  const loadPosts = async (pageNum) => {
    const res = await api.get(`/posts/user/${userId}?page=${pageNum}&size=10`);
    setPosts((prev) => (pageNum === 0 ? res.data.content : [...prev, ...res.data.content]));
    setPage(pageNum);
    setLast(res.data.last);
  };

  useEffect(() => {
    setList(null);
    api.get(`/follows/${userId}/stats`).then((r) => setStats(r.data));
    loadPosts(0);
  }, [userId]);

  const toggleFollow = async () => {
    if (stats.followedByMe) {
      await api.delete(`/follows/${userId}`);
      setStats({ ...stats, followedByMe: false, followerCount: stats.followerCount - 1 });
    } else {
      await api.post(`/follows/${userId}`);
      setStats({ ...stats, followedByMe: true, followerCount: stats.followerCount + 1 });
    }
  };

  const showList = async (type) => {
    if (list === type) {
      setList(null);
      return;
    }
    const res = await api.get(`/follows/${userId}/${type}?page=0&size=50`);
    setPeople(res.data.content);
    setList(type);
  };

  return (
    <>
      <div className="card">
        <div className="row">
            <div className="author">
                <Avatar name={name} size={64} />
                <h2>{name}</h2>
            </div>
          {userId !== user.id && stats && (
            <button onClick={toggleFollow}>{stats.followedByMe ? "Unfollow" : "Follow"}</button>
          )}
        </div>
        {stats && (
          <div className="row stats">
            <button className="link" onClick={() => showList("followers")}>
              <b>{stats.followerCount}</b> followers
            </button>
            <button className="link" onClick={() => showList("following")}>
              <b>{stats.followingCount}</b> following
            </button>
          </div>
        )}
        {list && (
          <div className="people">
            {people.length === 0 && <small className="muted">Nobody yet</small>}
            {people.map((p) => (
              <div key={p.id}>
                <Link to={`/profile/${p.id}`}>{p.username}</Link>
              </div>
            ))}
          </div>
        )}
      </div>

      {posts.map((p) => (
        <PostCard
          key={p.id}
          post={p}
          onDeleted={(pid) => setPosts((prev) => prev.filter((x) => x.id !== pid))}
        />
      ))}
      {posts.length === 0 && <p className="muted">No posts yet.</p>}
      {!last && <button className="more" onClick={() => loadPosts(page + 1)}>Load more</button>}
    </>
  );
}