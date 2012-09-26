(ns demo.core
  (:require     [ring.adapter.jetty     :as jetty]))

(defn hello-world
  "Return hello world page"
  [REQ]
  {:status 200,
   :body   (str "Hello, world!\n"
                "It is " (java.util.Date.))})

(defn -main [& [PORT]]
  (jetty/run-jetty #'hello-world
                   ;; join? false will return to command line
                   {:join?              false
                    :port               (if PORT
                                          (Integer/parseInt PORT)
                                          8080)}))
