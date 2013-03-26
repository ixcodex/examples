(ns customers.customer)

;; Data logic
(defn display-name [{:keys [firstname lastname]}]
  (format "%s %s" firstname lastname))

(defaggregate customer [:person :contact]
  :attributes [[:firstname    [:required, :length 10, :pattern "(.*)"]]
               [:lastname     [:required, :length 5]]
               [:display-name [:derived-using display-name]]
               [:address-1    []]
               [:postcode     [:postcode]]
               [:home-page    [:url]]]
  :linked-to  [[:many phone :by customer-id]])

;; Validation logic
(defn some-test-of [param1 param2]
  (if (= param1 param2)))

(defvalidator validate-something [{:keys [:firstname :secondname]}]
  (some-test-of firstname secondname)
  :validation-a "This model is invalid")

(validate customer
  :with [validate-something, validate-something-else])



(defvalue account-request [:account :request]
  :attributes [[:account-type [:one-of [:checking :credit]]]])

;; Process logic
(defprocess create-account :on customer [account-request]
  (do-some-stuff-with-the-request))


;; ====================================================================
;;
;; Extensibility....

(defmodel jims-customer :derived-from customer [:jims-tag]
  :adding {:some-new-field [:required]})

(validate jims-customer
  :with [some-other-validator])

