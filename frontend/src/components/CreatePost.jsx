import { useState } from "react";
import api from "../api";

export default function CreatePost({ onCreated }) {
  const [content, setContent] = useState("");
  const [error, setError] = useState("");
  const [busy, setBusy] = useState(false);

  const submit = async (e) => {
    e.preventDefault();
    if (!content.trim()) return;
    setBusy(true);
    setError("");
    try {
      const res = await api.post("/posts/create", { content });
      onCreated(res.data);
      setContent("");
    } catch (err) {
      setError(err.response?.data?.message || "Could not create post");
    } finally {
      setBusy(false);
    }
  };

  return (
    <form className="card" onSubmit={submit}>
      <textarea
        rows={3}
        maxLength={1000}
        placeholder="What's on your mind?"
        value={content}
        onChange={(e) => setContent(e.target.value)}
      />
      <div className="row">
        <small>{content.length}/1000</small>
        <button type="submit" disabled={busy || !content.trim()}>Post</button>
      </div>
      {error && <p className="error">{error}</p>}
    </form>
  );
}