(defproject dynamodb_explorer "0.2.0"
  :description "Webapp to explore dynamodb"
  :url ""
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]

                 [ring/ring-json "0.2.0"]
                 [com.amazonaws/aws-java-sdk "1.7.1"]
                 [com.taoensso/faraday "1.1.0"]

                 [http-kit "2.1.13"]
                 [ring/ring-devel "1.2.1"]
                 [ring/ring-jetty-adapter "1.2.1"]]
  :plugins [[lein-ring "0.8.10"]]
  :ring {:handler dynamodb_explorer.handler/app}
  :main  dynamodb_explorer.handler
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring-mock "0.1.5"]]}})
