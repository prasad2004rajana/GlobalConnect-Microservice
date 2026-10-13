import axios from "axios";

const api = axios.create({ baseURL: "http://localhost:8080/api" });

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

// expired or invalid token -> back to login (but not for login/register calls themselves)
api.interceptors.response.use(
  (res) => res,
  (err) => {
    const isAuthCall = err.config?.url?.startsWith("/auth/login") || err.config?.url?.startsWith("/auth/register");
    if (err.response?.status === 401 && !isAuthCall) {
      localStorage.removeItem("token");
      localStorage.removeItem("user");
      window.location.href = "/login";
    }
    return Promise.reject(err);
  }
);

// posts only carry userId, so we look up usernames and cache them
const userCache = {};
export function getUser(id) {
  if (!userCache[id]) {
    userCache[id] = api
      .get(`/auth/users/batch?ids=${id}`)
      .then((r) => r.data[0] || { id, username: `user ${id}` })
      .catch(() => ({ id, username: `user ${id}` }));
  }
  return userCache[id];
}
export default api;