(ns dashboard.ui.app-test
  (:require [dashboard.ui.app :as app]
            [purnam.test])
  (:use-macros
    [purnam.core :only [obj arr !]]
    [purnam.test :only [describe it is]]
    [gyr.test :only [describe.ng describe.controller
                     it-uses it-compiles]]))

(describe.ng
  {:doc "Storage"
   :module myApp
   :inject [storage]}
  (it "allows saving and retriving"
    (storage.put "hello" "world")
    (is (storage.get "hello") "world")
    
    (storage.clear)
    (is (storage.get "hello") nil)))

(describe.controller
  {:doc "AppCtrl"
   :module myApp
   :controller AppCtrl}

  (it "has key and val within the scope"
    (is $scope.key "hello")
    (is $scope.val "world"))

  (it "has put and get functionality"
    ($scope.putStore $scope.key $scope.val)
    (is ($scope.getStore "hello") "world"))

  (it "additional tests"
    (! $scope.key "bye")
    ($scope.putStore $scope.key $scope.val)
    (is ($scope.getStore "hello") nil)
    (is ($scope.getStore "bye") "world")))
