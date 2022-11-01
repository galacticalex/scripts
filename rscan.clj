; a reimplementation of the R functions sum(scan())
; takes user input stored as an array and computes the sum, each list item is demarkated with a CR (newline)
; see below for example usage

; $ bb rscan.clj
; $ 4
; $ 12
; $ 27
; $ 36
; $ 88
; $ 
; $ (4, 12, 27, 36, 88)

(defn scan
  "Collect stdin entries, demarcated by newlines, as an array. Returns the array."
  []
  (loop [entries [] n 1]
    (let [entry (do (print n ": ")
                    (flush)
                    (read-line))] 
      (if (not= entry "")
          (recur (conj entries (Float/parseFloat entry)) (+ n 1))
        entries))))

(print (scan))





