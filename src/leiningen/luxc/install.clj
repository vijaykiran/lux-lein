;;  Copyright (c) Eduardo Julian. All rights reserved.
;;  This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
;;  If a copy of the MPL was not distributed with this file,
;;  You can obtain one at http://mozilla.org/MPL/2.0/.

(ns leiningen.luxc.install
  (:require [cemerick.pomegranate.aether :as aether]
            (leiningen [pom :as pom])
            [clojure.java.io :as io]
            (leiningen.luxc [package :as &package])))

(defn install [project output-path]
  (let [artifacts (&package/package project output-path)
        pom-file (pom/pom project)
        local-repo (:local-repo project)]
    (aether/install
     :coordinates [(symbol (:group project) (:name project))
                   (:version project)]
     :artifact-map artifacts
     :pom-file (io/file pom-file)
     :local-repo local-repo)))
