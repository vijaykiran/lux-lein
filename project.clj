;;  Copyright (c) Eduardo Julian. All rights reserved.
;;  This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
;;  If a copy of the MPL was not distributed with this file,
;;  You can obtain one at http://mozilla.org/MPL/2.0/.

(defproject lux/lein "0.1.0"
  :description "The Leiningen plugin for the Lux programming language."
  :url "https://github.com/LuxLang/lein-luxc"
  :license {:name "Mozilla Public License"
            :url "https://www.mozilla.org/en-US/MPL/2.0/"}
  :scm {:name "git"
        :url "https://github.com/LuxLang/lein-luxc"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :signing {:gpg-key "eduardoejp@gmail.com"}
  :deploy-repositories [["clojars" {:creds :gpg}]]
  :eval-in :leiningen)
