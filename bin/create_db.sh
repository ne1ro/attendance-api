#!/bin/bash
sqlite3 attendance.db "CREATE TABLE attendants (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, firstName text NOT NULL, lastName text NOT NULL);\
  CREATE TABLE attendancies (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, attendantId INTEGER NOT NULL, day DATE NOT NULL, status BOOLEAN, FOREIGN KEY(attendantId) REFERENCES attendants(id) ON DELETE CASCADE);\
"
