(ns demo.repl
  (:require [clojure.browser.repl :as repl]))

(defn start-repl []
  (repl/connect "http://localhost:9000/repl"))
