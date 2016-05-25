CREATE TABLE Player (
  Id integer,
  Surname text,
  Lastname text,
  Nickname text,
  image blob
);

CREATE TABLE Team (
  Id integer,
  Name text
);

CREATE TABLE Match (
  Id integer,
  Time date,
  TeamOne text,
  TeamTwo text
);