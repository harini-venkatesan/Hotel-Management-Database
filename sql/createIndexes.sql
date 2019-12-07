DROP INDEX IF EXISTS indexHotel;  
DROP INDEX IF EXISTS indexStaff ;
DROP INDEX if exists  indexRoom ;
DROP INDEX if exists  indexCustomer ;
DROP INDEX if exists  indexMaintenanceCompany ;
DROP INDEX if exists indexBooking ;
DROP INDEX if exists  indexRepair  ;
DROP INDEX if exists  indexRequest ;
DROP INDEX if exists  indexAssigned  ;


CREATE INDEX indexHotel
ON Hotel
(hotelID);

CREATE INDEX indexStaff
ON Staff
(fName);

CREATE INDEX indexRoom
ON Room
(roomNo);

CREATE INDEX indexCustomer
ON Customer
(fName);

CREATE INDEX indexMaintenanceCompany
ON MaintenanceCompany
(address);

CREATE INDEX indexBooking
ON Booking
(bookingDate);

CREATE INDEX indexRepair
ON Repair
(mCompany);

CREATE INDEX indexRequest
ON Request
(requestDate);

CREATE INDEX indexAssigned
ON Assigned
(staffID);


