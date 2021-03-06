# Attendance API

API to operate attendants and attendances built in Clojure.

[![Build Status](https://travis-ci.org/ne1ro/attendance-api.svg?branch=master)](https://travis-ci.org/ne1ro/attendance-api)
[![Coverage Status](https://coveralls.io/repos/github/ne1ro/attendance-api/badge.svg)](https://coveralls.io/github/ne1ro/attendance-api)

## Usage

```clojure
lein install && lein repl
```

## Development

### Start local development server

When in REPL:
```
(use 'ring.adapter.jetty)(run-jetty app {:port 3000})
```

## Configuration

Use `profiles.clj` in development and ENV variables in prod.

## License

neiro © 2018-2019

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
