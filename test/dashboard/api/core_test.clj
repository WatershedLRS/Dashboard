(ns dashboard.api.core-test
  (:require dashboard.api.core)

  (:use [ring.mock.request :only [request header]]
        [compojure.core :only [ANY]]
        [midje.sweet :only [facts]]
        [dashboard.api.checkers]))

(facts "about a simple GET"
  (let [handler (ANY "/" [] dashboard.api.core/current-time)
        response (handler (request :get "/"))]
    response => OK
    response => (body "Hello World!")
    response => (content-type "text/plain;charset=UTF-8")
    ))
