(ns demo.message
  "A library for dummy storing of messages")

(defn log-add
  [LOG MESSAGE]
  (if (empty? LOG)
    (sorted-map 1 MESSAGE)
    (assoc LOG (-> LOG rseq first first inc) MESSAGE)))

(defn log-add-message
  [LOG AUTHOR BODY]
  (log-add LOG {:body   BODY,
                :author AUTHOR,
                :date   (java.util.Date.)}))
