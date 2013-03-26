(ns customers.customer)

(defn display-name [{:keys [firstname lastname]}]
  (format "%s %s" firstname lastname))

(defmodel customer [:person :contact]
  :attributes [[:firstname    [:required, :length 10, :pattern "(.*)"]]
               [:lastname     [:required, :length 5]]
               [:display-name [:derived-using display-name]]
               [:address-1    []]
               [:postcode     [:postcode]]
               [:home-page    [:url]]]
  :linked-to  [[:many address :by customer-id]])

(defn some-test-of [param1 param2]
  (if (= param1 param2)))

(defvalidator validate-something [{:keys [:firstname :secondname]}]
  (some-test-of firstname secondname)
  :validation-a "This model is invalid")

(validate customer
  :with [validate-something, validate-something-else])

(enhance customer :as jims-customer
  :with {
         :some-new-field [:required]})



(validate jims-customer
  :with [some-other-validator])

