(ns dynamodb_explorer.handler
  (:use compojure.core
        [ring.middleware.json :only [wrap-json-response]]
        [ring.util.response :only [response]]
        [ring.adapter.jetty :only (run-jetty)])
  (:require [compojure.handler :as handler]
            [org.httpkit.server :refer [run-server]]
            [ring.middleware.reload :as rl]
            [dynamodb_explorer.pages :as p]
            [dynamodb_explorer.db :as db]
            [clojure.data.json :as json]
            [compojure.route :as route]))

(defroutes json-routes
           (POST "/table/describe" [name] (response  (db/describe-table name)))
           (POST "/table/get-item" [name id] (response  (db/get-item name id)))
           )
(defroutes app-routes
  (GET "/" [] (p/home (db/list-tables)))
  (wrap-json-response json-routes)
  (route/resources "/")
  (route/not-found "Not Found"))


(def app
  (handler/site app-routes))


;; ## Server Lifecycle~

(defonce server (atom nil))

(defn kill! []
  (swap! server (fn [s] (when s (s) nil))))

(defn -main []
  (swap! server
         (fn [s]
           (if s
             (do (println "Server already running!") s)
             (do (println "Booting server on port 8080.")
                 (run-server (rl/wrap-reload #'app) {:port 8080}))))))

(defn running?
  "Returns true if the server is currently running, false otherwise."
  []
  (identity @server))

(defn cycle!
  "Cycles the existing server - shut down the relaunch."
  []
  (kill!)
  (-main))
