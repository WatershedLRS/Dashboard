(ns dashboard.ui.app
  (:require [clojure.browser.repl :as repl])
  (:use-macros
    [purnam.core :only [! def.n obj]]
    [gyr.core :only [def.module def.config def.value def.directive
                     def.controller def.service]]))

(repl/connect "http://localhost:9000/repl")

(def.module myApp [])

(def.service myApp.storage []
  (let [store (atom {})]
    (obj :put (fn [k v] (swap! store #(assoc % k v)))
         :get (fn [k] (@store k))
         :clear (fn [] (reset! store {}))
         :print (fn [] (js/console.log (clj->js @store))))))

(def.controller myApp.AppCtrl [$scope storage]
  (! $scope.key "hello")
  (! $scope.val "world")
  (! $scope.printStore storage.print) 
  (! $scope.clearStore storage.clear)
  (! $scope.putStore storage.put)
  (! $scope.getStore storage.get))
