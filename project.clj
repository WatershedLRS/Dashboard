(defproject dashboard "0.1.0-SNAPSHOT"
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler dashboard.api.core/app
         :auto-reload true
         :reload-paths ["src"]}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [im.chit/gyr "0.3.1"]
                 [liberator "0.12.2"]]

  :profiles {:dev {:dependencies [[ring/ring-jetty-adapter "1.3.1" :exclusions [joda-time]]
                                  [ring-mock "0.1.2"]
                                  [ring/ring-devel "1.3.1" :exclusions [joda-time]]
                                  [midje "1.6.3" :exclusions [org.clojure/clojure]]
                                  [compojure "1.3.1"]
                                  [org.clojure/clojurescript "0.0-2511"]]
                   :plugins [[lein-cljsbuild "1.0.4"],
                             [lein-less "1.7.2"]
                             [lein-midje "3.1.3" :exclusions [leiningen-core]]]}}

  :source-paths ["src"]
  :test-paths ["test"]

  :less {:source-paths ["resources/public/less"]
         :target-path "resources/public/css"}

;  :hooks [leiningen.less leiningen.cljsbuild]

  :cljsbuild {:builds {:test
                       {:source-paths ["test/dashboard/ui"],
                        :compiler {:pretty-print true,
                                   :output-to "resources/public/js//dashboard-test.js",
                                   :optimizations :simple}}
                       :app
                       {:source-paths ["src/dashboard/ui"],
                        :compiler {:pretty-print true,
                                   :output-to "resources/public/js/dashboard.js",
                                   :optimizations :simple}}}})