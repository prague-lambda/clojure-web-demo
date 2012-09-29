# Demo 3 - simple chat

In Demo 3 we implement simple "chatroom" using GET and POST
requests (no ajax at this moment).

Chatroom message
----------------

[message.clj](src/demo/message.clj) implements simple storage to 
store chatroom messages between calls. At this moment we do not
implement any complex database storage - we store all data
in sorted maps.

HTML rendering
---------------

[page.clj](src/demo/page.clj) renders basic main page and messages
using Hiccup library we introduced in Demo 2.

Routing
-------

We use Compojure to add more rules for routing in [core.clj](src/demo/core.clj).
Most notable changs

- We used `wrap-session` middleware to store message authors name in cookies.
  See how we add a cookie into response in `room-route` POST handling function.
  
- We use parameter destructuring to extract POST parameters in
  `room-route` and in `room-validate`. It has same syntax as
  specification of function parameters.
  
- In `main-route` definition we use destructuring to extract chat room name
  
```
(context "/room/:room" [] ...)
```
 
Compojure rules store extracted part into request and later we use this parameter
in `room-validate` and POST request.
