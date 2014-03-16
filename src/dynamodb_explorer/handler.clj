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
            [compojure.route :as route]))

(defroutes json-routes
           (POST "/table/describe" [name] (response  (db/describe-table name)))
           (POST "/table/get-item" [name hash range] (response  (db/get-item name hash range)))
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

(defn -main [& [port]]
  (swap! server
         (fn [s]
           (if s
             (do (println "Server already running!") s)
             (do (let [port (or port 9090)]
                   (println "Booting server on port " port)
                   (run-server (rl/wrap-reload #'app) {:port port})))))))

(defn running?
  "Returns true if the server is currently running, false otherwise."
  []
  (identity @server))

(defn cycle!
  "Cycles the existing server - shut down the relaunch."
  []
  (kill!)
  (-main 9090))
