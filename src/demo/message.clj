(ns demo.message
  "A library for dummy storing of messages")

(defn log-add
  [LOG MESSAGE]
  (if (empty? LOG)
    (sorted-map 1 MESSAGE)
    (assoc LOG (-> LOG rseq first first inc) MESSAGE)))

(defn log-add-message [LOG AUTHOR GRAVATAR BODY DATE]
  (log-add LOG {:body           BODY,
                :author         AUTHOR,
                :gravatar-token GRAVATAR,
                :date           DATE}))

(defn render-message [[ID {:keys [author gravatar-token date body]}]]
  [:p {:id (str "message-" ID)}
   [:img {:src (str "http://www.gravatar.com/avatar/" gravatar-token "?s=48")}]
   [:b author] " " date [:br] body])
