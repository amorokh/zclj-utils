(ns zclj-utils.om-next-basic
  (:require [goog.dom :as gdom]
            [om.dom :as dom]
            [om.next :as om :refer-macros [defui]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Utils

(enable-console-print!)

(defn log [msg]
  (.log js/console msg))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Read
(defmulti read om/dispatch)

(defmethod read :count
  [{:keys [state] :as env} key params]
  {:value (:count @state)})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Mutate
(defmulti mutate om/dispatch)

(defmethod mutate 'increment
  [{:keys [state] :as env} key params]
  {:value  {:keys [:count]}
   :action #(swap! state update-in [:count] inc)})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Components

(defui MainComponent
  static om/IQuery
  (query
   [this]
   [:count])
  Object
  (render
   [this]
   (dom/div nil
     (dom/span nil (str "Count : " (:count (om/props this))))
     (dom/button
      #js {:onClick (fn [e] (om/transact! this '[(increment)]))}
      "Increase"))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Application

(defonce app-state (atom {:count 0}))

(def parser (om.next/parser {:read read :mutate mutate}))

(def reconciler
  (om/reconciler {:state app-state
                  :parser parser}))

(defn main []
  (if-let [node (gdom/getElement "app")]
    (do
      (log "Loading MainComponent")
      (om/add-root! reconciler MainComponent node))))

(main)

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

