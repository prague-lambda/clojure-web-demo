(defproject demo "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure     "1.4.0"]
                 [ring/ring-jetty-adapter "1.1.5"]
                 [ring/ring-core          "1.1.5"]  ;; parsing parameters etc
                 [ring/ring-devel         "1.1.5"]  ;; dev middleware like stactrace
                 [hiccup                  "1.0.1"]
                 [compojure               "1.1.3"]]

  :plugins      [[lein-cljsbuild          "0.2.7"]]

  :main         "demo.core/-main"
  :cljsbuild    {:repl-listen           "9000",
                 :crossovers            [demo.message]
                 :builds [{:jar         true,
                           :compiler    {:output-to     "resources/public/main.js",
                                         :output-dir    "resources/public/js"
                                         :optimizations :none,
                                         :pretty-print  true}}]})
