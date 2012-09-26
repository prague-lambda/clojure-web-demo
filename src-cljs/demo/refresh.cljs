(ns demo.refresh
  (:require            [clojure.browser.dom    :as dom]
                       [clojure.browser.net    :as net]
                       [clojure.browser.event  :as event]
                       [cljs.reader            :as reader]
                       [goog.dom               :as gdom]
                       [goog.Timer             :as timer]
                       [demo.message           :as msg]))

(defn get-messages []
  (dom/get-element "messages"))

(defn get-last-message-id
  "Returns id of last message (if any message available)"
  []
  (if-let [e (gdom/getFirstElementChild (get-messages))]
    (-> (.getAttribute e "id")
        (subs 8)
        reader/read-string)))

(defn get-room
  "Return room id"
  [] (.getAttribute (get-messages) "data-message-room"))

;;; event handlers

(defn process-data
  [DATA]
  (let [last-id (get-last-message-id)
        m       (get-messages)]
    (doseq [[id msg :as d] DATA]
      (when (< last-id id)
        (dom/insert-at m (dom/element (msg/render-message d)) 0)))))

;;; connections

(def conn-poll
  "Polling connection"
  (doto (net/xhr-connection)
    (event/listen :success #(-> % .-target .getResponseText
                                reader/read-string process-data))))

(defn maybe-poll-server
  "Maybe poll the server"
  [& IGNORED]
  (when-not (.isActive conn-poll)
    (net/transmit conn-poll (str "/api/" (get-room)
                                 "?since=" (get-last-message-id)))))

(def server-polling-timer
  "Poll server"
  (doto (goog.Timer. 1000)
    (event/listen goog.Timer/TICK maybe-poll-server)
    (.start)))

;;; task - try to write post handler :)
