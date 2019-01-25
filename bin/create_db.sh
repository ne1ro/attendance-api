#!/bin/bash
sqlite3 attendance.db "CREATE TABLE attendants (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'first_name' text NOT NULL, 'last_name' text NOT NULL);\
  CREATE TABLE attendancies (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'attendant_id' INTEGER NOT NULL, day DATE NOT NULL, status BOOLEAN, UNIQUE(day, 'attendant_id')
      FOREIGN KEY('attendant_id') REFERENCES attendants(id) ON DELETE CASCADE);\
  CREATE TABLE tokens (token text NOT NULL);
"
