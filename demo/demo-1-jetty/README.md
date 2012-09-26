# demo

This is first example of 

1. leiningen2
-------------

We executed 

`lein new demo`
	
Command created project.clj and standard file structure:

    - project.clj
	- .gitignore
	- doc/
	- src/demo1/core.clj
	- test
	
project.clj
-----------

File project.clj describes the project. We added dependencies on ring (and jetty) here.
		
src/demo/core.clj
------------------	

First humble example of ring application. Execution

`lein run`

or 

`lein repl

