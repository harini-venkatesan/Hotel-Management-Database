SELECT B.bID
FROM Booking B 
WHERE B.hotelID = 155 AND B.roomNo = 2 AND B.bookingDate >= '1/23/2007' AND B.bookingDate < '1/30/2007'

SELECT max(B.price)
FROM Booking B
WHERE B.bookingDate >= '1/23/2007' AND B.bookingDate < '1/30/2007'
GROUP BY B.hotelID 

SELECT count(B.roomNo)
FROM Booking B
WHERE B.hotelID = 155 AND B.bookingDate = '1/23/2007' 
