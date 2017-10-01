(ns zclj-utils.csv
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(defn resource [file-path]
  (.getFile (clojure.java.io/resource file-path)))

(defn csv-from-file [file-name separator]
  (with-open [file (io/reader file-name)]
    (doall
     (csv/read-csv file :separator separator :quote \"))))

(defn csv-to-file [csv-data path separator]
  (with-open [writer (io/writer path)]
    (csv/write-csv writer csv-data :separator separator)))

(defn csv-resource [file-path separator]
  (with-open [file (io/reader (resource file-path))]
    (doall
     (csv/read-csv file :separator separator :quote \"))))


