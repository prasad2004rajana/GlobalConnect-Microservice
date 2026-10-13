import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../api";
import { useAuth } from "../AuthContext";
import useUsername from "../useUsername";

function Comment({ c, canDelete, onDelete }) {
  const name = useUsername(c.userId);
  return (
    <div className="comment">
      <div>
        <Link to={`/profile/${c.userId}`}><b>{name}</b></Link> {c.content}
      </div>
      {canDelete && <button className="link" onClick={() => onDelete(c.id)}>delete</button>}
    </div>
  );
}

export default function Comments({ postId, postOwnerId, onCountChange }) {
  const { user, requireLogin } = useAuth();
  const [comments, setComments] = useState([]);
  const [text, setText] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    api.get(`/posts/${postId}/comments?page=0&size=50`).then((r) => setComments(r.data.content));
  }, [postId]);

  const add = async (e) => {
    e.preventDefault();
    if (!text.trim()) return;
    setError("");
    try {
      const res = await api.post(`/posts/${postId}/comments`, { content: text });
      setComments((prev) => [...prev, res.data]);
      setText("");
      onCountChange(1);
    } catch (err) {
      setError(err.response?.data?.message || "Could not add comment");
    }
  };

  const remove = async (commentId) => {
    try {
      await api.delete(`/posts/${postId}/comments/${commentId}`);
      setComments((prev) => prev.filter((c) => c.id !== commentId));
      onCountChange(-1);
    } catch (err) {
      setError(err.response?.data?.message || "Could not delete comment");
    }
  };

  return (
    <div className="comments">
      {comments.map((c) => (
        <Comment
          key={c.id}
          c={c}
          canDelete={!!user && (c.userId === user.id || postOwnerId === user.id)}
          onDelete={remove}
        />
      ))}
      {user ? (
        <form className="row" onSubmit={add}>
          <input
            maxLength={500}
            placeholder="Write a comment..."
            value={text}
            onChange={(e) => setText(e.target.value)}
          />
          <button type="submit">Send</button>
        </form>
      ) : (
        <button className="link" onClick={requireLogin}>Login to comment</button>
      )}
      {error && <p className="error">{error}</p>}
    </div>
  );
}