(ns app.parser
  (:require
   [app.resolvers]
   [com.wsscode.pathom.core :as p]
   [com.wsscode.pathom.connect :as pc]
   [taoensso.timbre :as log]))

(def pathom-parser
  (p/parser {::p/env     {::p/reader                 [p/map-reader
                                                      pc/reader2
                                                      pc/ident-reader
                                                      pc/index-reader]
                          ::pc/mutation-join-globals [:tempids]}
             ::p/mutate  pc/mutate
             ::p/plugins [(pc/connect-plugin {::pc/register app.resolvers/resolvers})
                          p/error-handler-plugin
                          ;; or p/elide-special-outputs-plugin
                          (p/post-process-parser-plugin p/elide-not-found)]}))

(defn api-parser [query]
  (log/info "Process" query)
  (pathom-parser {} query))



(comment
  (api-parser [{[:list/id :friends] [:list/id]}])
  (api-parser [{[:person/id 1] [:person/name :person/age]}])
  (api-parser [{[:list/id :friends] [:list/id {:list/people [:person/name]}]}])
  )
