(ns dynamodb_explorer.db
  (:require [taoensso.faraday :as far]))

(def AWS_ACCESS_KEY (System/getenv "AWS_ACCESS_KEY"))
(def AWS_SECRET_KEY (System/getenv "AWS_SECRET_KEY"))
(def DYNAMODB_ENDPOINT (System/getenv "DYNAMODB_ENDPOINT"))

(def creds {:access-key AWS_ACCESS_KEY
            :secret-key AWS_SECRET_KEY
            :endpoint   DYNAMODB_ENDPOINT})

(defn list-tables []
  (map name (far/list-tables creds)))

(defn describe-table [table-name]
      (far/describe-table creds table-name))


(defn find-key [table-name key-type]
  (let [table-description (describe-table table-name)
        primary-keys (get-in table-description [:prim-keys])
        [key]  (for [[k v] primary-keys :when (= (:key-type v) key-type)] k)]
    key))

(defn find-hash-key [table-name]
  (find-key table-name :hash))

(defn find-range-key [table-name]
  (find-key table-name :range))

(defn get-item [table-name hash range]
  (let [hash-key (find-hash-key table-name)
        key-map {hash-key hash}
        range-key (if (and range (not= range "")) (find-range-key table-name))
        key-map (if (and range (not= range "")) (assoc-in key-map range-key range) key-map)]
    (far/get-item creds table-name key-map)))


(defn scan [table-name]
  (let [result
        (far/scan creds table-name {:limit 20})]
    {:result result}))

