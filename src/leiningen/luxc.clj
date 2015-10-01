;;  Copyright (c) Eduardo Julian. All rights reserved.
;;  This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
;;  If a copy of the MPL was not distributed with this file,
;;  You can obtain one at http://mozilla.org/MPL/2.0/.

(ns leiningen.luxc
  (:require [leiningen.pom :as pom]
            [leiningen.core.classpath :as classpath]
            (leiningen.luxc [compiler :as &compiler]
                            [deps :as &deps]
                            [package :as &package]
                            [install :as &install]
                            [deploy :as &deploy]))
  (:import (java.io File
                    InputStreamReader
                    BufferedReader)))

;; [Exports]
(defn luxc [project & args]
  (case (first args)
    "compile"
    (&compiler/compile project)

    "install"
    (do (&install/install project (str (:name project) "-" (:version project) ".jar"))
      (println "DONE!"))

    "deploy"
    (if-let [where (second args)]
      (&deploy/deploy project where)
      (println "[Error] You must specify where they deployment is to be done..."))

    ;; default...
    (println "Commands available: compile, install, deploy"))
  )
