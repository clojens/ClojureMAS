--------------------------------------------------------------------------------

# clojure-webservice server

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein deps
    lein ring server

and to test run:

    curl -X GET http://localhost:3000

I used [this resource][2].
[2]: http://mmcgrana.github.com/2010/08/clojure-rest-api.html

To add any feature use can just type C-c C-l from emacs if it was jacked in. To do it: M-x nrepl-jack-in

# clojure-ws-client

a client to the server

## Usage

FIXME

## License

Copyright(c) 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.

