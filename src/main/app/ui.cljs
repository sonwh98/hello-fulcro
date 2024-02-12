(ns app.ui
  (:require
   [com.fulcrologic.fulcro.algorithms.react-interop :as react-interop]    
   [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
   [com.fulcrologic.fulcro.dom :as dom]
   [com.fulcrologic.fulcro.components :as fc]
   [react]
   ["/js/MyReactComponent" :default MyReactComponent])) 

(def ui-my-react-component
  (react-interop/react-factory MyReactComponent))


(defsc Person [this {:person/keys [name age] :as param}]
  {:query [:person/name :person/age :foo/bar]
   :initial-state (fn [{:keys [name age] :as p}]
                    {:person/name name :person/age age})}
  (dom/li
   (dom/h5 (str name " (age: " age ")" param))
   (ui-my-react-component {:message "wassup world"})))

(def ui-person (comp/factory Person))

(defsc PersonList [this {:list/keys [label people]}]
  {:query [:list/label {:list/people (comp/get-query Person)}]
   :initial-state
   (fn [{:keys [label] :as p}]
     {:list/label label
      :list/people (if (= label "Friends")
                     [(comp/get-initial-state Person {:name "Sally" :age 32})
                      (comp/get-initial-state Person {:name "Joe" :age 22})]
                     [(comp/get-initial-state Person {:name "Fred" :age 11})
                      (comp/get-initial-state Person {:name "Bobby" :age 55})])})}
  (dom/div
   (dom/h4 label)
   (dom/ul
    (map ui-person people))))

(def ui-person-list (comp/factory PersonList))

(defsc Root [this {:keys [friends enemies]}]
  {:query [{:friends (comp/get-query PersonList)}
           {:enemies (comp/get-query PersonList)}]
   :initial-state (fn [params]
                    {:friends (comp/get-initial-state PersonList {:label "Friends"})
                     :enemies (comp/get-initial-state PersonList {:label "Enemies"})})}
  (dom/div
   (ui-my-react-component {:message "hi world"})
   (ui-person-list friends)
   (ui-person-list enemies)))

(comment
  (comp/get-initial-state PersonList {:label "Friends"})
  (com.fulcrologic.fulcro.application/current-state app.application/app)
  (-> app.application/app keys)
  (-> app.application/app :com.fulcrologic.fulcro.application/config)
  
  (comp/get-query PersonList)
  (comp/get-query Root)
  (comp/get-query Person)
  )
