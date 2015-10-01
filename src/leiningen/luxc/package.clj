;;  Copyright (c) Eduardo Julian. All rights reserved.
;;  This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
;;  If a copy of the MPL was not distributed with this file,
;;  You can obtain one at http://mozilla.org/MPL/2.0/.

(ns leiningen.luxc.package
  (:import (java.io InputStream
                    File
                    FileInputStream
                    FileOutputStream
                    BufferedInputStream
                    ByteArrayOutputStream)
           (java.util.jar Manifest
                          Attributes$Name
                          JarEntry
                          JarInputStream
                          JarOutputStream
                          )))

;; [Utils]
(defn ^:private write-file! [prefix ^File file ^JarOutputStream out]
  (if (.isDirectory file)
    (let [prefix* (str prefix "/" (.getName file))]
      (doseq [file* (.listFiles file)]
        (write-file! prefix* file* out)))
    (with-open [in (new BufferedInputStream (new FileInputStream file))]
      (let [buffer-length (* 1024 1024)
            buffer (byte-array buffer-length)]
        (.putNextEntry out (new JarEntry (str prefix "/" (.getName file))))
        (loop [bytes-read 0]
          (when (not= -1 bytes-read)
            (.write out buffer 0 bytes-read)
            (recur (.read in buffer 0 buffer-length))))
        (doto out
          (.flush)
          (.closeEntry)
          ))
      )))

;; [Exports]
(defn package [project output-path]
  (let [output-file (new File output-path)]
    (with-open [out (->> output-file (new FileOutputStream) (new JarOutputStream))]
      (doseq [$group (.listFiles (new File "source"))]
        (write-file! "" $group out))
      {[:extension "jar"] (.getAbsolutePath output-file)}
      )))
