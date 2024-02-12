(ns app.application
  (:require
   [com.fulcrologic.fulcro.application :as app]
   [clojure.pprint]))

(defonce app (app/fulcro-app))

(comment
  (type app)
  (= (::app/state-atom app)
     (:app.applications.app/state-atom app)
     (:com.fulcrologic.fulcro.application/state-atom app))
  ::app/state-atom
  (keys app)
  )
