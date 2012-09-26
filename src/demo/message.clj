(ns demo.message
  "A library for dummy storing of messages")

(defn log-add
  [LOG MESSAGE]
  (if (empty? LOG)
    (sorted-map 1 MESSAGE)
    (assoc LOG (-> LOG rseq first first inc) MESSAGE)))

(defn log-add-message
  [LOG AUTHOR BODY DATE]
  (log-add LOG {:body   BODY,
                :author AUTHOR,
                :date   DATE}))

(defn render-message
  [[ID {:keys [author date body]}]]
  [:p {:id (str "message-" ID)} [:b author] " " date [:br] body])
