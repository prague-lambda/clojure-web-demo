(ns demo.core
  (:require     [ring.adapter.jetty     :as jetty]
                [demo.page              :as page]
                [ring.middleware stacktrace params keyword-params])
  (:use         compojure.core))


(defroutes main-route
  (GET "/" [:as req]
    (page/hello-world (-> req :params :who))))

(def jetty-handler
  (-> #'main-route
      ;; transate :params {"name" val} into {:name val}
      ring.middleware.keyword-params/wrap-keyword-params
      ;; parse request to :query-params, :form-params and :params
      ring.middleware.params/wrap-params
      ;; handle exceptions and print stack traces
      ring.middleware.stacktrace/wrap-stacktrace))

(defn -main [& [PORT]]
  (jetty/run-jetty #'jetty-handler
                   ;; join? false will return to command line
                   {:join?              false
                    :port               (if PORT
                                          (Integer/parseInt PORT)
                                          8080)}))
