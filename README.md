--------------------------------------------------------------------------------


clojure-webservice server
===========================

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


clojure-ws-client
=================

a client to the server

## Usage

FIXME

## License

Copyright(c) 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.

fixmes
======

default action must be redirect to /repl/(+ 2 2) page

(runr "http://localhost:3000" '(clojure.core/+ 2 2))
doesnt work, produces:
Exception 404  clj-http.client/wrap-exceptions/fn--2424 (client.clj:35)
http://localhost:3000/repl/%28clojure.core%2F%2B+2+2%29
 because it doesn't allow encoded slashes.
I guess it is like here:
http://www.jampmark.com/web-scripting/5-solutions-to-url-encoded-slashes-problem-in-apache.html
but with jetty web-server.

notes
=====

(use 'ring.adapter.jetty)

(defn start-web []
  (run-jetty (var my-site) {:port 8080 :join? false}))

dependencies
============

A client depends on serizlization and doesn't depend on the server. But the server does depend both on serizlization and on the client. This is because server can also use remote methods