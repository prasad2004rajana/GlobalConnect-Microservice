export default function Avatar({ name, size = 40 }) {
  const initial = (name || "?").charAt(0).toUpperCase();
  const hue = [...(name || "")].reduce((h, c) => h * 31 + c.charCodeAt(0), 0) % 360;
  return (
    <div
      style={{
        width: size, height: size, borderRadius: "50%", flexShrink: 0,
        background: `hsl(${hue} 60% 45%)`, color: "#fff", fontWeight: 700,
        fontSize: size * 0.45, display: "flex", alignItems: "center", justifyContent: "center",
      }}
    >
      {initial}
    </div>
  );
}