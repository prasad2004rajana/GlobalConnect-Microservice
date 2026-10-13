import { useState } from "react";
import { Link } from "react-router-dom";
import api from "../api";
import { useAuth } from "../AuthContext";
import useUsername from "../useUsername";
import { timeAgo } from "../utils";
import Avatar from "./Avatar";
import Comments from "./Comments";

export default function PostCard({ post, onDeleted }) {
  const { user, requireLogin } = useAuth();
  const [p, setP] = useState(post);
  const [showComments, setShowComments] = useState(false);
  const name = useUsername(p.userId);

  const toggleLike = async () => {
    if (!requireLogin()) return;
    const before = p;
    const liked = p.likedByMe;
    setP({ ...p, likedByMe: !liked, likeCount: p.likeCount + (liked ? -1 : 1) });
    try {
      if (liked) await api.delete(`/posts/${p.id}/like`);
      else await api.post(`/posts/${p.id}/like`);
    } catch {
      setP(before);
    }
  };

  const remove = async () => {
    if (!window.confirm("Delete this post?")) return;
    try {
      await api.delete(`/posts/${p.id}`);
      onDeleted(p.id);
    } catch {
      alert("Could not delete post");
    }
  };

  return (
    <div className="card">
      <div className="row">
        <Link
          to={`/profile/${p.userId}`}
          className="author"
          onClick={(e) => { if (!requireLogin()) e.preventDefault(); }}
        >
          <Avatar name={name} />
          <b>{name}</b>
        </Link>
        <small className="muted-text">{timeAgo(p.createdAt)}</small>
      </div>
      <p className="content">{p.content}</p>
      <div className="row actions">
        <button className="link" onClick={toggleLike}>
          {p.likedByMe ? "❤️" : "🤍"} {p.likeCount}
        </button>
        <button className="link" onClick={() => setShowComments(!showComments)}>
          💬 {p.commentCount}
        </button>
        {user && p.userId === user.id && (
          <button className="link danger" onClick={remove}>Delete</button>
        )}
      </div>
      {showComments && (
        <Comments
          postId={p.id}
          postOwnerId={p.userId}
          onCountChange={(d) => setP((prev) => ({ ...prev, commentCount: prev.commentCount + d }))}
        />
      )}
    </div>
  );
}