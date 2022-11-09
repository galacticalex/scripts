
; rscan.clj

; SPDX-FileCopyrightText 2022 Orcro Limited <team@orcro.co.uk>
;
; SPDX-License-Identifier: Apache-2.0

; Inspired by the R function scan(), this script reads user input data as a 1D array (denominated by presses of the <Enter> key), then either returns the data as-is, or performs some sort of processing (such as applying + to the data, resulting in a sum) if a flag is specified. 

; Example usage at the shell:
; 
; $ bb rscan.clj +
; 1 : 4
; 2 : 12
; 3 : 27
; 4 : 36
; 5 : 
; 79


(defn scan []
  (loop [entries [] n 1]
    (let [entry (do (print n ": ")
                    (flush)
                    (read-line))] 
      (if (not= entry "")
        (recur (conj entries (Float/parseFloat entry)) (+ n 1))
        entries))))


(case (first *command-line-args*)
  nil (println (scan)) ; print the array to stdout
  "+" (print (float (apply + (map bigdec (scan)))))
  "t" (spit "out.txt" (scan)))







