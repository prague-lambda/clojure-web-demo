(ns demo.page
  (:require         [demo.message :as msg])
  (:use             [hiccup core element form util page]))

(defn page [TITLE & BODY]
  (html5 [:html
          [:head
           [:link {:rel "stylesheet" :href "/main.css"}]
           [:title TITLE]]
          (into [:body] BODY)]))

(defn include-cljs []
  (list (include-js "/js/goog/base.js")
        (include-js "/main.js")
        (javascript-tag "goog.require (\"demo.repl\");")
        (javascript-tag "goog.require (\"demo.refresh\");")))

(defn list-chatrooms [MAP]
  (page "Simple chatroom"
        ;;  list rooms
        (for [[name room]  (sort-by first MAP)]
          [:p (link-to (str "room/" (url-encode name)) name) "[" (count room) "]"])
        ;; add a form to create new room
        [:form {:method "post" :action "new-room"}
         "Room name:" (text-field "room-id") (submit-button "Enter")]))

(defn render-room [NAME LOGS AUTHOR EMAIL]
  (page (str "Room " NAME)
        (include-cljs)
        [:h1 NAME " room"]
        ;; input part
        [:form {:method "post" :action (str "/room/" (url-encode NAME))}
         (text-field :author (or AUTHOR "Anonymous")) [:br]
         (text-field {:placeholder "Email"} :email (or EMAIL "")) [:br]
         (text-area {:rows 5 :cols 40} :body) [:br]
         (submit-button {:id "submit-message"} "Submit")]
        ;; messages
        [:div#messages {:data-message-room NAME}
         (when LOGS
           (map #(-> %
                     (update-in [1 :author] escape-html)
                     (update-in [1 :body] escape-html)
                     msg/render-message)
                (rseq LOGS)))]
        [:p
         (link-to "/" "Back to main page")]))
