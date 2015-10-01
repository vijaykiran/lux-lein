;;  Copyright (c) Eduardo Julian. All rights reserved.
;;  This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
;;  If a copy of the MPL was not distributed with this file,
;;  You can obtain one at http://mozilla.org/MPL/2.0/.

(ns leiningen.luxc.compiler
  (:refer-clojure :exclude [compile])
  (:require [leiningen.core.classpath :as classpath])
  (:import (java.io File
                    InputStreamReader
                    BufferedReader)))

(defn compile [project]
  (if-let [program-module (:lux/program project)]
    (let [class-path (->> (classpath/get-classpath project) (filter #(.endsWith % ".jar")) (interpose ":") (reduce str ""))
          command (str "java -Xss4m -cp " (str "luxc.jar:" class-path) " lux compile " program-module)
          process (.exec (Runtime/getRuntime) command)]
      (with-open [std-out (->> process .getInputStream (new InputStreamReader) (new BufferedReader))
                  std-err (->> process .getErrorStream (new InputStreamReader) (new BufferedReader))]
        (loop [line "[BEGIN]"]
          (when line
            (println line)
            (recur (.readLine std-out))))
        (loop [line ""]
          (when line
            (println line)
            (recur (.readLine std-err))))
        (println "[END]")))
    (println "Please provide a program main module in :lux/program")))
