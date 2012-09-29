# Adding ClojureScript to Chat Room Demo

In the last iteration of our demo we add ClojureScript to improve our 
(simple chat server)[demo/demo-3-chat]. We will poll server using XhrIo
request to fetch new chat room messages without page refresh.

lein-cljsbuild
--------------

We use [lein-cljsbuild](https://github.com/emezeske/lein-cljsbuild) Leiningen 
plugin to integrate ClojureScript to our project. See [project.clj](project.clj)
for changes and cljsbuild configuration.

Type `lein cljsbuild once` to compile .cljs files to JavaScript. 

To modify our ClojureScript life in the browser, we can also start ClojureScript 
REPL using `lein cljsbuild repl-listen`. This command will listen for repl
request on port 9000. Then we need to connect the browser.

Once we compile ClojureScript files, we can launch Jetty web server, point
our browser to main page and enter a chat room. Then we launch JavaScript developer
console in Chrome and type `demo.repl.start_repl ()`.

Crossovers
----------

Crossovers in Cljsbuild enable code sharing between Clojure and
ClojureScript. ClojureScript files are stored in [src-cljs/](src-cljs)
directory, crossovers are in stored in [src/](src) directory and
configured in [project.clj](project.clj) file.

We will use crossover functionality to share message rendering in
[message.clj](src/demo/message.clj). Output from function `render-message`
can be rendered into HTML by both Hiccup library and `clojure.browser.dom`.

Extending server
----------------

To enable ClojureScript polling we performed two changes to [core.clj](src/demo/core.clj):

* `(resources "/")` to serve compiled JavaScript files from resources/ directory.
* we added api-routing to fulfill browser polling requests.


In [page.clj](src/demo/page.clj) we included JavaScript and added new attributes
to identify messages from JavaScript.

Server polling from browser
---------------------------

ClojureScript uses [Google Closure](http://closure-library.googlecode.com/svn/docs/index.html) library. 
We use `goog.net.XhrIo` and `goog.Timer` to build simple polling mechanism. 

`clojure.browser.dom`, `net` and `event` libraries are simple ClojureScript wrappers around
Google Closure functionality.



