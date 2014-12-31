(ns dashboard.api.core
  (:require [liberator.core :refer [resource defresource]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.util.response :as resp]
            [compojure.core :refer [defroutes ANY context GET]]
            [compojure.route :as route]))

(defresource current-time
  :available-media-types ["text/plain"]
  :handle-ok (fn [ctx]
               "Hello World!"))

(defroutes user-routes
  (GET "/" [] (resp/redirect "/index.html"))
  (context "/api" []
    (ANY "/hello" [] current-time))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> user-routes
      wrap-params))