CREATE TABLE todos (
  id TEXT PRIMARY KEY NOT NULL UNIQUE,
  title TEXT NOT NULL,
  content TEXT NOT NULL,
  create_at TEXT NOT NULL,
  update_at TEXT NOT NULL
);
