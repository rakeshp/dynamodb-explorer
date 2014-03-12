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


(defn find-hash-key [table-name]
  (let [table-description (describe-table table-name)
        primary-keys (get-in table-description [:prim-keys])
        [hash-key]  (for [[k v] primary-keys :when (= (:key-type v) :hash)] k)]
    hash-key))

(defn get-item [table-name id]
  (let [hash-key (find-hash-key table-name)]
    (far/get-item creds table-name {hash-key id})))


