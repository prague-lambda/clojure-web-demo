(ns demo.core
  (:require     [clojure.string         :as str]
                [ring.adapter.jetty     :as jetty]
                [demo.page              :as page]
                [demo.message           :as msg]
                [ring.util.response     :as resp]
                [ring.middleware session stacktrace params keyword-params])
  (:use         compojure.core))

(defonce rooms (atom {}))

(defroutes room-route
  (GET "/" [room :as req]
     (let [logs (@rooms room)]
         (page/render-room room logs (-> req :session :author))))
  (POST "/" [room author body :as req]
     (let [logs       (@rooms room)
           written-by (or (when-not (empty? author) author)
                          (-> req :session :author)
                          "Anonymous")]
       (when-not (empty? body)
         (swap! rooms update-in [room]
                msg/log-add-message author body))
       ;; redirect back to room
       (-> (resp/redirect-after-post (str "/room/" room))
           ;;  and add a author into session as a cookie
           (assoc-in [:session :author] author)))))

(defn room-validate
  "Validates room parameter - if room already exits"
  [{{room :room} :params}]
  (when-not (@rooms room)
    (resp/not-found (str "No such room: " room))))

(defroutes main-route
  (GET "/"  []
     (page/list-chatrooms @rooms))
  (context "/room/:room" []
     room-validate
     room-route)
  (POST "/new-room" [:as r]
    (let [new-name (-> r :params :room-id
                       (str/replace #"[/?%<>&]" "_"))]
      (swap! rooms update-in [new-name] #(or % (sorted-map)))
      (resp/redirect (str "room/" new-name)))))

(def jetty-handler
  (-> #'main-route
      ;; sessions & cookies
      ring.middleware.session/wrap-session
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
