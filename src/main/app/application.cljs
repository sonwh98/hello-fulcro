(ns app.application
  (:require
   [com.fulcrologic.fulcro.application :as app]
   [com.fulcrologic.fulcro.networking.http-remote :as http]
   [clojure.pprint]))

(defonce app (app/fulcro-app
               {:remotes {:remote (http/fulcro-http-remote {})}}))

(comment
  (type app)
  (= (::app/state-atom app)
     (:app.applications.app/state-atom app)
     (:com.fulcrologic.fulcro.application/state-atom app))
  ::app/state-atom
  (keys app)
  )
