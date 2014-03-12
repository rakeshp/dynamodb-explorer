(ns dynamodb_explorer.pages
  (:use [hiccup.core               :only [html]]
        )
  (:require
    [hiccup.page            :as   hiccup]))

(defn javascript-tag
  "Wrap the supplied javascript up in script tags and a CDATA section."
  [script]
  [:script {:type "text/javascript"}
   (str "//<![CDATA[\n" script "\n//]]>")])

(defn home [tables]
  (html
    (hiccup/doctype :html5)
    [:html
     [:head
      [:title "Explorer"]
      [:link {:rel "stylesheet" :href "/jquery.jsonview.css"}]
      [:script {:type "text/javascript" :src "http://code.jquery.com/jquery.min.js"}]
      [:script {:type "text/javascript" :src "/jquery.jsonview.js"}]
      ]
     [:body
      [:label "Table: "]
      [:select#table (for [x tables] [:option x])]
      [:button#describe "Describe"]
      [:br] [:br]
      [:label "Id: "] [:input#item-id {:type "text"}] [:button#get "Get"]
      [:br]
      [:h2 "Data"]
      [:br]
      [:div#data]

      (hiccup/include-js "/js/home.js")
      ]
     ]))
