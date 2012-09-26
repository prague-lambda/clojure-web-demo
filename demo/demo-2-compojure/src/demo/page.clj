(ns demo.page
  (:use             [hiccup.core]))

(defn hello-world [& [WHO]]
  (html [:html
         [:head
           [:title "Hello World"]]
         [:body
          [:h1 "Hello, " (or WHO "Prague Clojurians")]]]))
