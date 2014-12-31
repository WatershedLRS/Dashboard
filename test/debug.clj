(require 'compojure.core)
(require 'ring.adapter.jetty)

(use 'dashboard.api.core)

(ring.adapter.jetty/run-jetty app {:port 3000 :join? false})