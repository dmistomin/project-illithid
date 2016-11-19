(ns illithid.handlers
  (:require
    [re-frame.core :refer [reg-event-db after debug]]
    [clojure.spec :as s :include-macros true]
    [illithid.db :as db :refer [app-db]]
    [illithid.character.core :as c]
    [illithid.character.ability :as a]))

;;;

(defn validate-schema!
  "Throw an exception if db doesn't match the schema."
  [db]
  (when-not (s/valid? ::db/db db)
    (throw (js/Error. (str "schema check failed: "
                           (s/explain-str ::db/db db))))))

(def middleware [debug (after validate-schema!)])

;;;

(reg-event-db
  :initialize-db
  middleware
  (fn [_ _] app-db))

(reg-event-db
  :set-ability
  middleware
  (fn [db [_ ability new-value]]
    (assoc-in db [::db/character ::c/abilities ability] new-value)))

(reg-event-db
  :adjust-ability
  middleware
  (fn [db [_ ability change]]
    (update-in db [::db/character ::c/abilities ability] (partial + change))))
