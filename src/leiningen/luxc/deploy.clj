;;  Copyright (c) Eduardo Julian. All rights reserved.
;;  This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
;;  If a copy of the MPL was not distributed with this file,
;;  You can obtain one at http://mozilla.org/MPL/2.0/.

(ns leiningen.luxc.deploy
  (:require [cemerick.pomegranate.aether :as aether]
            [leiningen.core.main :as main]
            [leiningen.pom :as pom]
            [leiningen.deploy :as _deploy]
            [leiningen.luxc.package :as &package]))

;; Borrowed from leiningen.deploy
(defn- abort-message [message]
  (cond (re-find #"Return code is 405" message)
        (str message "\n" "Ensure you are deploying over SSL.")
        (re-find #"Return code is 401" message)
        (str message "\n" "See `lein help deploying` for an explanation of how"
             " to specify credentials.")
        :else message))

(defn files-for [project repo]
  (let [signed? (_deploy/sign-for-repo? repo)
        ;; If pom is put in "target/", :auto-clean true will remove it if the
        ;; jar is created afterwards. So make jar first, then pom.
        artifacts (merge (&package/package project (str (:name project) "-" (:version project) ".jar"))
                         {[:extension "pom"] (pom/pom project)})
        sig-opts (_deploy/signing-opts project repo)]
    (if (and signed? (not (.endsWith (:version project) "-SNAPSHOT")))
      (reduce merge artifacts (map #(_deploy/signature-for-artifact % sig-opts)
                                   artifacts))
      artifacts)))

(defn deploy [project repository]
  (let [repo (_deploy/repo-for project repository)
        files (files-for project repo)]
    (try (main/debug "Deploying" files "to" repo)
      (aether/deploy
       :coordinates [(symbol (:group project) (:name project))
                     (:version project)]
       :artifact-map files
       :transfer-listener :stdout
       :repository [repo])
      (catch org.sonatype.aether.deployment.DeploymentException e
        (when main/*debug* (.printStackTrace e))
        (main/abort (abort-message (.getMessage e)))))))
