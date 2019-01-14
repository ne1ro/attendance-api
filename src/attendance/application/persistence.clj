(ns attendance.application.persistence)

(defprotocol Persistence
  "Abstraction for attendance persistence operations"
  (get-attendant [this id] "Returns attendant by ID or nil")
  (create-attendant [this attendant-form] "Creates attendant")
  (delete-attendant [this id] "Deletes attendant by ID")
  (list-attendances-days [this] "Returns list of days where were attendances")
  (list-attendances-by-attendant [this id] "Filters attendances by attendant ID")
  (list-attendances [this day] "Returns attendances filtered by day")
  (get-attendance-by-day [this attendant-id day] "Gets attendance by attendant ID and day")
  (create-attendance [this form] "Creates attendance mark")
  (delete-attendance [this attendance] "Removes attendance mark"))
