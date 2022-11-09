

(def urlregex #"https:\/\/.*|http:\/\/.*") ; regex used to match urls (needs to be more robust)


(defn getUrls
  "Extract URLs from the file provided as an argument, returns a list."
  [filePath]
  (re-seq urlregex (slurp filePath))) ; The parameter takes a list, so (first ,,,) is needed.


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


(defn tryUrl
 ""
 [url]
 (let [response (org.httpkit.client/request {:url url})]
  (if (nil? (:status @response))
   (println (str "response status for " url " was nil"))
   (println (str "response status for " url " was " (:status @response))))))


(defn checkUrlList
 "Attempt a HTTP GET request for each URL in the list parameter."
 [urls]
 (dorun (map tryUrl urls)))

(if (= true (handleStartup *command-line-args*))
 (checkUrlList (getUrls *command-line-args*)))


	(println "\nend script")

