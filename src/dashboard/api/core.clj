(ns dashboard.api.core
  (:require [liberator.core :refer [resource defresource]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.util.response :as resp]
            [compojure.core :refer [defroutes ANY context GET POST DELETE]]
            [compojure.route :as route]
            [cemerick.rummage :as sdb]
            [dashboard.api.simpledb :refer [config domain]]
            [dashboard.api.liberator-utils :as utils]))

(defresource log-items
             :allowed-methods [:get :post]
             :available-media-types ["application/json"]
             :handle-ok (fn [_]
                          (sdb/query-all config (format "select * from %s", domain)))
             :post! (fn [ctx]
                      (dosync
                        (let [body (slurp (get-in ctx [:request :body]))
                              id (str (java.util.UUID/randomUUID))
                              msg {::sdb/id id :content body}
                              _ (sdb/put-attrs config domain msg)]
                          {::id id})))
             :post-redirect? (fn [ctx] {:location (format "/log/%s" (::id ctx))}))

(defresource log-item [x]
             :allowed-methods [:get :delete]
             :available-media-types ["application/json"]
             :known-content-type? #(utils/check-content-type % ["application/json"])
             :exists? (fn [_] (if-let [d (sdb/get-attrs config domain x)] {::data d}))
             :delete! (fn [_] (sdb/delete-attrs config domain x))
             :malformed? #(utils/parse-json % ::data)
             :handle-ok ::data)

(defroutes user-routes
           (GET "/" [] (resp/redirect "/index.html"))
           (context "/api" []
                    (ANY "/log" [] log-items)
                    (ANY "/log/:x" [x] (log-item x)))

           (route/resources "/")
           (route/not-found "Page not found"))

(def app
  (-> user-routes
      wrap-params))