import { createContext, useContext, useState } from "react";
import LoginPrompt from "./components/LoginPrompt";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const saved = localStorage.getItem("user");
    return saved ? JSON.parse(saved) : null;
  });
  const [promptOpen, setPromptOpen] = useState(false);

  const login = (data) => {
    localStorage.setItem("token", data.token);
    const u = { id: data.id, username: data.username, email: data.email };
    localStorage.setItem("user", JSON.stringify(u));
    setUser(u);
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    setUser(null);
  };

  const requireLogin = () => {
    if (user) return true;
    setPromptOpen(true);
    return false;
  };

  return (
    <AuthContext.Provider value={{ user, login, logout, requireLogin }}>
      {children}
      <LoginPrompt open={promptOpen} onClose={() => setPromptOpen(false)} />
    </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);