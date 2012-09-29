# Demo 2

project.clj
-----------

In our second demo we added two libraries to help us with Clojure
development web development

* [Hiccup](https://github.com/weavejester/hiccup) 
* [Compojure](https://github.com/weavejester/compojure) 
	
See [project.clj](project.clj) for changes.

Hiccup
------

Hiccup library uses Clojure data types to express HTML structure.
HTML tree is expressed using vectors and list. Vectors start
with element keyword and are optionally followed with map 
of attributes. 

See [page.clj](src/demo/page.clj) for hello-world example.

Compojure
---------

Compojure defines DSL (domain specific language) for request routing.
In [core.clj](src/demo/core.clj) we use it fullfil request GET /.
More complex examples will follow in Demo 3.

Middleware
----------

Function `jetty-handler` in [core.clj](src/demo/core.clj) shows how 
you can extend basic jetty functionallity using middleware. `jetty-handler`
is now composed from four independent parts - our routing function (`main-route`),
then two functions for processing arguments and development utility which
wraps any exception and produces readable output both in browser and in RPEL.
