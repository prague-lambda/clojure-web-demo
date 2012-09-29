# Demo 1

Demonstrates very basic ring and jetty usage. [core.clj](src/demo/core.clj) 
has only two functions: hello-world and -main.

Function -main launches jetty server - and there are only two
parameters: function which handles any request and configuration map.

Handler function accepts one argument (request) which returns result map
(includes keys like :status, :body etc). If you expected complex configuration
options (like in Apache) it is not there. All functionality is achieved using
handler function. 

It does not mean that you have to handcraft all functionality yourself :)
You can build complex functionality by combining "middleware" and routing library.
Middleware functions provide functionality like session or parameter handling.
Routing library allow you to write rules to describe your site structure.

More on this in demo 2.

leiningen2
-------------

To start with our project, we executed leiningen command:

`lein new demo`
	
Command created project.clj and standard file structure:

- [project.clj](project.clj) configuration file.
- [.gitignore](.gitignore) file
- [doc/](doc/) directory
- Basic [src/demo/core.clj](src/demo/core.clj)
- [test/](test/) directory
	
project.clj
-----------

File project.clj describes the project. We added dependencies on ring (and jetty) here.
		
Execution
---------
To execute our example we can run


`lein run`

or 

```
lein repl
> (use 'demo.core)
> (-main)
```
