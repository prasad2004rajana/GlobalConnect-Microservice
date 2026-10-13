import { useEffect, useState } from "react";
import { getUser } from "./api";

export default function useUsername(id) {
  const [name, setName] = useState(`user ${id}`);
  useEffect(() => {
    let active = true;
    getUser(id).then((u) => active && setName(u.username));
    return () => {
      active = false;
    };
  }, [id]);
  return name;
}