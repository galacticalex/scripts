

; (require [org.httpkit.client :as http])


 (def urlregex #"https:\/\/.*|http:\/\/.*") ; regex used to match urls (needs to be more robust) 


(defn getUrls
  "Extract URLs from the file provided as an argument."
  [filePath]
  (re-seq urlregex (slurp (first filePath)))) ; The parameter takes a list, so (first ,,,) is needed.


(defn tryUrl
  "Sends an HTTP GET request to the passed argument. If the request is successful, prints true, else false."
  [url]
  (let [response? (try
                    (get url)
                    "\33[32mvalid.\33[0m"
                    (catch Throwable e
                      "\033[91minvalid.\033[0m"))]
    (println (str "URL: " url " is " response?)))
  nil)


(defn checkUrlList
  "Attempt a HTTP GET request for each URL in the parameter."
  [urls]
  (dorun (pmap tryUrl urls))
  (System/exit 0))


(defn handleStartup
  "Helper function to check startup parameters are specified correctly."
  [args]
  (let [startupErrors ["The file specified does not appear to exist.\n\n"
                       "The file to test does not appear to be specified correctly.\n\n"]] 
    (if (= (count args) 1) ; if there's a single argument
      (if (.exists (clojure.java.io/file (first args))) ; and it exists
        true ; return true (program can continue)
        (do (print (nth startupErrors 0)) false)) ; otherwise the file doesn't exist 
      (do (print (nth startupErrors 1)) false)))) ; or the parameters weren't specified correctly 


(defn -main
  "Start here!"
  [& args]
  (if (= true (handleStartup args))
    (checkUrlList (getUrls args))))


;(defn -main
;  "Program entry point."
;  [& args]
;  (print "getting here?")
;  (if (= true (handleStartup args))
;    (checkUrlList (getUrls (first args))))
;  (System/exit 0))


