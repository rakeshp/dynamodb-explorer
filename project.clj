(defproject dynamodb_explorer "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [ring/ring-json "0.2.0"]
                 [com.amazonaws/aws-java-sdk "1.7.1"]
                 [com.taoensso/faraday "1.1.0"]]
  :plugins [[lein-ring "0.8.10"]]
  :ring {:handler dynamodb_explorer.handler/app}
  :profiles
  {:dev {:dependencies [
                         [http-kit "2.1.13"]
                         [ring/ring-devel "1.2.1"]
                         [ring/ring-jetty-adapter "1.2.1"]
                         [javax.servlet/servlet-api "2.5"]
                         [ring-mock "0.1.5"]]}})
