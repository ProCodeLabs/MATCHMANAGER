CREATE TABLE Player (
  Id INTEGER PRIMARY KEY AUTOINCREMENT,
  Surname text,
  Lastname text,
  Nickname text,
  image blob
);

CREATE TABLE Team (
  Id INTEGER PRIMARY KEY AUTOINCREMENT,
  Name text
);

CREATE TABLE Match (
  Id INTEGER PRIMARY KEY AUTOINCREMENT,
  Time date,
  TeamOne text,
  TeamTwo text
);