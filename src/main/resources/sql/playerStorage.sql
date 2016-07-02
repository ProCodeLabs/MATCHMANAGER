CREATE TABLE user_storage (
      id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
      teamId INTEGER,

      forename TEXT NOT NULL,
      surname TEXT NOT NULL
);
