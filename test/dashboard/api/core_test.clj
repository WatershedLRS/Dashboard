(ns dashboard.api.core-test
  (:require dashboard.api.core)

  (:use [ring.mock.request :only [request header]]
        [compojure.core :only [ANY]]
        [midje.sweet]
        [dashboard.api.checkers]))


(facts "listing log items"
       (let [handler (ANY "/" [] dashboard.api.core/log-items)
             response (handler (request :get "/"))]
         response => OK
         response => (body "{}")
         response => (content-type "application/json;charset=UTF-8"))
       (against-background
         (sdb/query-all anything anything) => {}))
