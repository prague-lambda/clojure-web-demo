(ns demo.page
  (:use             hiccup.core
                    hiccup.element      ; link-to
                    hiccup.form         ; forms
                    hiccup.util         ; url-encode
                    hiccup.page))       ; html5

(defn page [TITLE & BODY]
  (html5 [:html
          [:head
           [:title TITLE]]
          (into [:body] BODY)]))

(defn list-chatrooms [MAP]
  (page "Simple chatroom"
        ;;  list rooms
        (for [[name room]  (sort-by first MAP)]
          [:p (link-to (str "room/" (url-encode name)) name) "[" (count room) "]"])
        ;; add a form to create new room
        [:form {:method "post" :action "new-room"}
         "Room name:" (text-field "room-id") (submit-button "Enter")]))

(defn render-message
  [[ID {:keys [author date body]}]]
  [:p [:b (escape-html author)] " "  date [:br]
   (escape-html body)])

(defn render-room [NAME LOGS AUTHOR]
  (page (str "Room " NAME)
        [:h1 NAME " room"]
        ;; input part
        [:form {:method "post" :action (str "/room/" (url-encode NAME))}
         (text-field :author (or AUTHOR "Anonymous")) [:br]
         (text-area {:rows 5 :cols 40} :body) [:br]
         (submit-button "Submit")]
        ;; messages
        (when LOGS
          (map render-message (rseq LOGS)))
        [:p
         (link-to "/" "Back to main page")]))
