(ns dashboard.api.simpledb
  (:require
    [cemerick.rummage :as sdb]
    [cemerick.rummage.encoding :as enc]))

(def client (sdb/create-client "AKIAJSOVYRGPJKPYC3YA" "STU+WqbPD37FS4TvNqgFmhgRzmR0TLHCS75Ph456"))
(def domain "dashboard_events")
(def config (assoc enc/keyword-strings :client client))
