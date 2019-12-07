/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class DBProject {

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of DBProject
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public DBProject (String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end DBProject

   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQuery (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; i++){
		System.out.print(rsmd.getColumnName(i) + "\t");
	    }
	    System.out.println();
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery


public int executeQueryNoPrint (String query) throws SQLException {
     // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(!outputHeader){
	    for(int i = 1; i <= numCol; i++){
		System.out.print(rsmd.getColumnName(i) + "\t");
	    }
	    System.out.println();
	    outputHeader = false;
	 }
         //for (int i=1; i<=numCol; ++i)
            //System.out.print (rs.getString (i) + "\t");
         //System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery



public String executeQueryReturnData (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();
      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);
      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      String data = "";
      

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    //for(int i = 1; i <= numCol; i++){
		//System.out.print(rsmd.getColumnName(i) + "\t");
	    //}
	    //System.out.println();
	    outputHeader = false;
	 }
	 
		for (int i=1; i<=numCol; ++i)
            //System.out.print (rs.getString (i) + "\t");
            data = rs.getString(i);
         //System.out.println ();
		

      }//end while
      stmt.close ();
      return data;
   }//end executeQuery
   
   
   
public void executeQueryReturnDataMultiple (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; ++i){
		System.out.print(rsmd.getColumnName(i) + "\t");
	    }
	    System.out.println();
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
     //return rowCount;
   }//end executeQuery




   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            DBProject.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if
      
      Greeting();
      DBProject esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the DBProject object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new DBProject (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statements
				System.out.println("MAIN MENU");
				System.out.println("---------");
				System.out.println("1. Add new customer");
				System.out.println("2. Add new room");
				System.out.println("3. Add new maintenance company");
				System.out.println("4. Add new repair");
				System.out.println("5. Add new Booking"); 
				System.out.println("6. Assign house cleaning staff to a room");
				System.out.println("7. Raise a repair request");
				System.out.println("8. Get number of available rooms");
				System.out.println("9. Get number of booked rooms");
				System.out.println("10. Get hotel bookings for a week");
				System.out.println("11. Get top k rooms with highest price for a date range");
				System.out.println("12. Get top k highest booking price for a customer");
				System.out.println("13. Get customer total cost occurred for a give date range"); 
				System.out.println("14. List the repairs made by maintenance company");
				System.out.println("15. Get top k maintenance companies based on repair count");
				System.out.println("16. Get number of repairs occurred per year for a given hotel room");
				System.out.println("17. < EXIT");

            switch (readChoice()){
				   case 1: addCustomer(esql); break;
				   case 2: addRoom(esql); break;
				   case 3: addMaintenanceCompany(esql); break;
				   case 4: addRepair(esql); break;
				   case 5: bookRoom(esql); break;
				   case 6: assignHouseCleaningToRoom(esql); break;
				   case 7: repairRequest(esql); break;
				   case 8: numberOfAvailableRooms(esql); break;
				   case 9: numberOfBookedRooms(esql); break;
				   case 10: listHotelRoomBookingsForAWeek(esql); break;
				   case 11: topKHighestRoomPriceForADateRange(esql); break;
				   case 12: topKHighestPriceBookingsForACustomer(esql); break;
				   case 13: totalCostForCustomer(esql); break;
				   case 14: listRepairsMade(esql); break;
				   case 15: topKMaintenanceCompany(esql); break;
				   case 16: numberOfRepairsForEachRoomPerYear(esql); break;
				   case 17: keepon = false; break;
				   default : System.out.println("Unrecognized choice!"); break;
            }//end switch
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main
   
   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface      	               \n" +
         "*******************************************************\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         }catch (Exception e) {
            System.out.println("Your input is invalid!");
            continue;
         }//end try
      }while (true);
      return input;
   }//end readChoice

   
   public static void addCustomer(DBProject esql){
	  // Given customer details add the customer in the DB 
      // Your code goes here.
      // ...
      // ...
       try{
         String query = "INSERT INTO Customer (customerID, fName,lName, Address, phNo, DOB, gender) VALUES (";
         
         String customerID = Integer.toString(esql.executeQueryNoPrint("Select * from Customer"));
		 query += customerID + ", '";
         System.out.println("Customer ID generated: "+ customerID);
		 

        
         
         
         System.out.print("\tEnter First Name: ");
         String input = in.readLine();
         query += input + "', '";
         
         System.out.print("\tEnter Last Name: ");
         input = in.readLine();
         query += input + "', '";
         
         System.out.print("\tEnter Address: ");
         input = in.readLine();
         query += input + "', ";
         
         System.out.print("\tEnter Phone Number: ");
         input = in.readLine();
         query += input + ", '";
         
         
         System.out.print("\tEnter Date of Birth (mm/dd/yyyy): ");
         input = in.readLine();
         query += input + "', '";
         
         
         
		 //checking for valid manager in the hotel 
		 while (input.charAt(2) != '/' || input.charAt(5) != '/'){
			System.out.print("\tInvalid date format. Enter Date  (mm/dd/yyyy): ");
			input = in.readLine();
		 }
         
         
         System.out.print("\tEnter Gender: ");
         input = in.readLine();
         query += input + "')";
			//System.out.println(query);
         esql.executeUpdate(query);
         //System.out.println ("total row(s): " + rowCount);
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end addCustomer

   public static void addRoom(DBProject esql){
	  // Given room details add the room in the DB
      // Your code goes here.
      // ...
      // ...
      
      try{
         String query = "INSERT INTO Room (hotelID, roomNo,roomType) VALUES (";
         System.out.print("\tEnter Hotel ID: ");
         String input = in.readLine();
  
         int rows = 0;
		 rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ input);
		 //checking for valid manager in the hotel 
	
		 while (rows == 0){
			System.out.print("\tInvalid Hotel ID. Enter hotel ID: ");
			input = in.readLine();
		    rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ input);
		 }
         
         query += input + ", ";
         
         
         
         System.out.print("\tEnter Room No: ");
         input = in.readLine();
         query += input + ", '";
         
         System.out.print("\tEnter Room Type: ");
         input = in.readLine();
         query += input + "')";

         esql.executeUpdate(query);
        //System.out.println ("total row(s): " + rowCount);
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end addRoom

   public static void addMaintenanceCompany(DBProject esql){
      try{
         String query = "INSERT INTO MaintenanceCompany(cmpID, name, address, isCertified) VALUES (";
         System.out.print("\tEnter Company ID: ");
         String input = in.readLine();
         query += input + ", '";
         
         System.out.print("\tEnter Name: ");
         input = in.readLine();
         query += input + "', ";
         
         System.out.print("\tEnter TRUE if Certified: ");
         input = in.readLine();
         query += input + ")";

         esql.executeUpdate(query);
        //System.out.println ("total row(s): " + rowCount);
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end addMaintenanceCompany

   public static void addRepair(DBProject esql){
	 try{
		 
		 
         String query = "INSERT INTO Repair(rID, hotelID, roomNo, mCompany, repairDate, description, repairType) VALUES (";
         
         
       
         String input = Integer.toString(esql.executeQueryNoPrint("Select * from repair"));
         System.out.print("\tRepair ID generated: " + input);
         query += input + ", ";
         
         System.out.print("\tEnter Hotel ID: ");
         input = in.readLine();
         
          int rows = 0;
		 rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ input);
		 while (rows == 0){
			System.out.print("\tInvalid Hotel ID. Enter hotel ID: ");
			input = in.readLine();
		    rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ input);
		 }
         
         query += input + ", ";
   
         System.out.print("\tEnter Room Number: ");
         String roomNo = in.readLine();
         rows = 0;
		 rows = esql.executeQueryNoPrint("Select roomNo from room where roomno = "+ roomNo + " AND HotelID = " + input);
		 
		 while (rows == 0){
			System.out.print("\tInvalid Room No. Enter Room no: ");
			input = in.readLine();
		    rows = esql.executeQueryNoPrint("Select roomNo from room where roomno = "+ roomNo + " AND HotelID = " + input);
		 }
        
         
         query += roomNo + ", ";
         
         
         System.out.print("\tEnter Company ID: ");
         input = in.readLine();
 
         rows = 0;
		 rows = esql.executeQueryNoPrint("Select * from maintenanceCompany where cmpid = "+ input);
		 
		 while (rows == 0){
			System.out.print("\tInvalid CompanyID. Enter CompanyID: ");
			input = in.readLine();
		    rows = esql.executeQueryNoPrint("Select * from maintenanceCompany where cmpid = "+ input);
		 }
		 
         query += input + ", '";
         
         System.out.print("\tEnter Repair Date: ");
         input = in.readLine();
         
         while (input.charAt(2) != '/' || input.charAt(5) != '/'){
			System.out.print("\tInvalid date format. Enter Date (mm/dd/yyyy): ");
			input = in.readLine();
		 }
         
         query += input + "', '";
 
         System.out.print("\tEnter Description: ");
         input = in.readLine();
         query += input + "', '";
         
         System.out.print("\tEnter Repair Type: ");
         input = in.readLine();
         query += input + "')";

         esql.executeUpdate(query);
        //System.out.println ("total row(s): " + rowCount);
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end addRepair

   public static void bookRoom(DBProject esql){
	  // Given hotelID, roomNo and customer Name create a booking in the DB 
      // Your code goes here.
      // ...
      // ...
      try{
		  
		 String Bookingquery = "Select * from Booking";
		 int bookingID = esql.executeQueryNoPrint(Bookingquery);		// Gives New Booking ID
		 System.out.println("\tBooking ID generated: " + Integer.toString(bookingID));
		 System.out.print("\tEnter Hotel ID: ");
         String hotelID = in.readLine();
		
		 int rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ hotelID);
		 while (rows == 0){
			System.out.print("\tInvalid Hotel ID. Enter hotel ID: ");
			hotelID = in.readLine();
		    rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ hotelID);
		 }
	
		
		 System.out.print("\tEnter Room Number: ");
         String roomNo = in.readLine();
		 
		 rows = 0;
		 rows = esql.executeQueryNoPrint("Select roomNo from room where roomno = "+ roomNo + " AND HotelID = " + hotelID);
		 
		 while (rows == 0){
			System.out.print("\tInvalid Room No. Enter Room no: ");
			roomNo = in.readLine();
		    rows = esql.executeQueryNoPrint("Select roomNo from room where roomno = "+ roomNo + " AND HotelID = " + hotelID);
		 }
		 
		 
		 
		 System.out.print("\tEnter Customer's First Name: ");
         String fname = in.readLine();
       
		 System.out.print("\tEnter Customer's Last Name: ");
         String lname = in.readLine();
         
         rows = 0;
		 rows = esql.executeQueryNoPrint("Select * from customer where fname = '"+ fname + "' AND lname = '" + lname + "'" );
		 
		 while (rows == 0){
			System.out.print("\tInvalid Name. ");
			
			System.out.print("\tEnter Customer's First Name: ");
			fname = in.readLine();
       
			System.out.print("\tEnter Customer's Last Name: ");
			lname = in.readLine();
			
		    rows = esql.executeQueryNoPrint("Select * from customer where fname = '"+ fname + "' AND lname = '" + lname + "'" );
		 }
         
		 String custID = (esql.executeQueryReturnData("Select C.customerID From Customer C where c.fname = '" + fname + "' AND c.lname = '" + lname + "' "));		//
  
  
		 System.out.print("\tEnter Date of Booking (mm/dd/yyyy):  ");
		 String bookDate = in.readLine();
		 
		     
         while (bookDate.charAt(2) != '/' || bookDate.charAt(5) != '/'){
			System.out.print("\tInvalid date format. Enter (mm/dd/yyyy): ");
			bookDate = in.readLine();
		 }
         
		 
		 System.out.print("\tEnter number of people staying in room:  ");
		 String noOfPeople = in.readLine();
		 
		 System.out.print("\tEnter price of booking:  ");
		 String priceBooking = in.readLine();
		 
		  
         String query = "INSERT INTO Booking(bID, customer, hotelID, roomNo, BookingDate, noOfPeople, price) VALUES ( " + Integer.toString(bookingID) + ", ";
		 query += custID + ", " +  hotelID + ", " + roomNo + ", '" + bookDate + "', " + noOfPeople + ", " + priceBooking + ")";
		 
		
		 
        //System.out.println(query);
         esql.executeUpdate(query);
    
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
      
      
   }//end bookRoom

   public static void assignHouseCleaningToRoom(DBProject esql){
	  // Given Staff SSN, HotelID, roomNo Assign the staff to the room 
      // Your code goes here.
      // ...
      // ...
      
      try{
		  
		 String Assignquery = "Select * from Assigned";
		 int asgID = esql.executeQueryNoPrint(Assignquery);		// Gives New Assignment ID
		 System.out.println("\tAssign ID generated: " + Integer.toString(asgID));
		 
         String query = "INSERT INTO Assigned(asgID, staffID, hotelID, roomNo) VALUES (";
         query += Integer.toString(asgID) + ", ";
         
         System.out.print("\tEnter Staff SSN: ");
         String input = in.readLine();
         
         
         int rows = esql.executeQueryNoPrint("Select * from staff where ssn = "+ input + " and ROLE = 'HouseCleaning'" );
		 while (rows == 0){
			System.out.print("\tInvalid SSN. Enter Staff SSN: ");
			input = in.readLine();
		    rows = esql.executeQueryNoPrint("Select * from staff where ssn = "+ input  + " and ROLE = 'HouseCleaning'");
		 }
         




		query += input + ", ";
		
        System.out.print("\tEnter Hotel ID: ");
        input = in.readLine();
         
         
         rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ input);
		 while (rows == 0){
			System.out.print("\tInvalid Hotel ID. Enter hotel ID: ");
			input = in.readLine();
		    rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ input);
		 }
	 
         query += input + ", ";     
         
		 System.out.print("\tEnter Room Number: ");
         String roomNo = in.readLine();
         rows = 0;
		 rows = esql.executeQueryNoPrint("Select roomNo from room where roomno = "+ roomNo + " AND HotelID = " + input);
		 
		 while (rows == 0){
			System.out.print("\tInvalid Room No. Enter Room number: ");
			input = in.readLine();
		    rows = esql.executeQueryNoPrint("Select roomNo from room where roomno = "+ roomNo + " AND HotelID = " + input);
		 }
        
         query += roomNo + ")";

         esql.executeUpdate(query);
        //System.out.println ("total row(s): " + rowCount);
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
      
   }//end assignHouseCleaningToRoom
   
   public static void repairRequest(DBProject esql){
	  // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request in the DB
      // Your code goes here.
      // ...
      // ...
      
      try{
		  
		 String Requestquery = "Select * from Request";
		 int reqID = esql.executeQueryNoPrint(Requestquery);		// Gives New Booking ID
		 System.out.println("\tRequest ID generated: " + Integer.toString(reqID));
		 
		  
         System.out.print("\tEnter Staff SSN: ");
         String managerID = in.readLine();
         
         int rows = esql.executeQueryNoPrint("Select * from staff where ssn = "+ managerID + " and Role = 'Manager'");
		 while (rows == 0){
			System.out.print("\tInvalid SSN. Enter Staff SSN: ");
			managerID = in.readLine();
		    rows = esql.executeQueryNoPrint("Select * from staff where ssn = "+ managerID + " and Role = 'Manager'");
		 }
         
    
         System.out.print("\tEnter repairID: ");
         String repairID = in.readLine();
         
         
         rows = esql.executeQueryNoPrint("Select * from repair where rid = "+ repairID);
		 while (rows == 0){
			System.out.print("\tInvalid repair ID. Enter Repair ID: ");
			repairID = in.readLine();
		    rows = esql.executeQueryNoPrint("Select * from repair where rid = "+ repairID);
		 }
         
         
         System.out.print("\tEnter date: ");
         String date = in.readLine();         
         while (date.charAt(2) != '/' || date.charAt(5) != '/'){
			System.out.print("\tInvalid date format. Enter Date  (mm/dd/yyyy): ");
			date = in.readLine();
		 }
         
         
         System.out.print("\tEnter description: ");
         String description = in.readLine();
         
       
         //rows = esql.executeQuery("Select * from MaintenanceCompany where name = "+ companyName);
         
        //   int rows = 0;
		// int hotelID = esql.executeQuery("Select hotelID from Repair where rID = "+ repairID);
		 //checking for valid manager in the hotel 
		// while (rows == 0){
		//	System.out.print("\tInvalid manager ID. Enter staff SSN: ");
		//	managerID = in.readLine();
		//	rows = esql.executeQuery("Select * from Hotel where manager = "+ managerID + " AND hotelID = " + Integer.toString(hotelID));  
		// }
         
         String query = "INSERT INTO Request(reqID, managerID, repairID, requestDate, description) VALUES (" + reqID + ", " + managerID + ", " + repairID + ", '" + date + "', '" + description + "')";
         esql.executeUpdate(query);

      }catch(Exception e){
         System.err.println (e.getMessage());
      }
      
      
      
   }//end repairRequest
   
   public static void numberOfAvailableRooms(DBProject esql){
	  // Given a hotelID, get the count of rooms available 
      // Your code goes here.
      // ...
      // ...
      
      try{
		  
		 System.out.print("\tEnter Hotel ID: ");
         String hotelID = in.readLine();
         
         int rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ hotelID);
		 while (rows == 0){
			System.out.print("\tInvalid Hotel ID. Enter hotel ID: ");
			hotelID = in.readLine();
		    rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ hotelID);
		 }
         
         
         
		 String Countquery = "Select DISTINCT roomNo from Room where hotelID = ";
		 Countquery += hotelID + " EXCEPT";
		 
		 Countquery += " (Select DISTINCT roomNo from Booking where hotelID = ";
		 Countquery += hotelID + " )";
		 
		 int total = esql.executeQueryNoPrint(Countquery);		// Gives New Assignment ID
		 //System.out.println("\tAssign ID generated: " + Integer.toString(asgID));
		 
		 System.out.println("\t" + total +" rooms have never been booked in hotelID " + hotelID);
		 
        //System.out.println ("total row(s): " + rowCount);
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end numberOfAvailableRooms
   
   public static void numberOfBookedRooms(DBProject esql){
	  // Given a hotelID, get the count of rooms booked
      // Your code goes here.
      // ...
      // ...
      
      try{
	   System.out.print("\tEnter Hotel ID: ");
       String hotelID = in.readLine();
       
       
       int rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ hotelID);
		 while (rows == 0){
			System.out.print("\tInvalid Hotel ID. Enter hotel ID: ");
			hotelID = in.readLine();
		    rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ hotelID);
		 }
       
       
       String Countquery = "Select DISTINCT roomNo from Booking where hotelID = ";
	   Countquery += hotelID;
	   
	   int total = esql.executeQueryNoPrint(Countquery);		// Gives New Assignment ID
		 //System.out.println("\tAssign ID generated: " + Integer.toString(asgID));
		 
		 System.out.println("\t" + total +" rooms are booked in hotelID " + hotelID);
	   
	   
      }catch(Exception e){
         System.err.println (e.getMessage());

	}
   }//end numberOfBookedRooms
   
   public static void listHotelRoomBookingsForAWeek(DBProject esql){
	  // Given a hotelID, date - list all the rooms available for a week(including the input date) 
      // Your code goes here.
      // ...
      // ...
      try{
		System.out.print("\tEnter Hotel ID: ");
		String hotelID = in.readLine();
		
		
		 int rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ hotelID);
		 while (rows == 0){
			System.out.print("\tInvalid Hotel ID. Enter hotel ID: ");
			hotelID = in.readLine();
		    rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ hotelID);
		 }
      
		System.out.print("\tEnter date of week (mm/dd/yyyy): ");
		String date = in.readLine();
		  
         while (date.charAt(2) != '/' || date.charAt(5) != '/'){
			System.out.print("\tInvalid date format. Enter Date (mm/dd/yyyy): ");
			date = in.readLine();
		 }
		
		String query = "Select DISTINCT * from Booking where hotelID = ";
		query += hotelID;
		query += " AND bookingDate >= '" + date + "' AND bookingDate -7  <= '" + date + "' " ;
		
		System.out.print("\n");
		esql.executeQueryReturnDataMultiple(query);
		
	}catch(Exception e){
         System.err.println (e.getMessage());
 
	}
 

      
   }//end listHotelRoomBookingsForAWeek
   
   public static void topKHighestRoomPriceForADateRange(DBProject esql){
	  // List Top K Rooms with the highest price for a given date range
      // Your code goes here.
      // ...
      // ...
      try{
		System.out.print("\tEnter K value: ");
		String K = in.readLine();
      
		System.out.print("\tEnter start date: ");
		String startDate = in.readLine();
        
         while (startDate.charAt(2) != '/' || startDate.charAt(5) != '/'){
			System.out.print("\tInvalid date format. Enter Date (mm/dd/yyyy): ");
			startDate = in.readLine();
		 }
		
		
		System.out.print("\tEnter end date: ");
		String endDate = in.readLine();
		
      
         while (endDate.charAt(2) != '/' || endDate.charAt(5) != '/'){
			System.out.print("\tInvalid date format. Enter Date  (mm/dd/yyyy): ");
			endDate = in.readLine();
		 }
		
		

		String query = "Select * from Booking where bookingDate >= '" + startDate + "' AND bookingDate <= '" + endDate + "' Order by price desc limit " + K;		
		System.out.print("\n");
		esql.executeQueryReturnDataMultiple(query);
		
	}catch(Exception e){
         System.err.println (e.getMessage());
 
	}
      
      
      
      
   }//end topKHighestRoomPriceForADateRange
   
   public static void topKHighestPriceBookingsForACustomer(DBProject esql){
	  // Given a customer Name, List Top K highest booking price for a customer 
      // Your code goes here.
      // ...
      // ...
      try{
		System.out.print("\tEnter K value: ");
		String K = in.readLine();
      
		System.out.print("\tEnter Customer first name: ");
		String fname = in.readLine();
		
		System.out.print("\tEnter Customer last name: ");
		String lname = in.readLine();
		
		
		 int rows = 0;
		 rows = esql.executeQueryNoPrint("Select * from customer where fname = '"+ fname + "' AND lname = '" + lname + "'" );
		 
		 while (rows == 0){
			System.out.print("\tInvalid Name. ");
			
			System.out.print("\tEnter Customer's First Name: ");
			fname = in.readLine();
       
			System.out.print("\tEnter Customer's Last Name: ");
			lname = in.readLine();
			
		    rows = esql.executeQueryNoPrint("Select * from customer where fname = '"+ fname + "' AND lname = '" + lname + "'" );
		 }
		
		
		
		String custID = (esql.executeQueryReturnData("Select C.customerID From Customer C where c.fname = '" + fname + "' AND c.lname = '" + lname + "' "));
	

		String query = "Select price from booking  where customer = " + custID + " Order by price desc limit " + K;		
		System.out.print("\n");
		esql.executeQueryReturnDataMultiple(query);
		
	}catch(Exception e){
         System.err.println (e.getMessage());
     } 
      
   }//end topKHighestPriceBookingsForACustomer
   
   public static void totalCostForCustomer(DBProject esql){
	  // Given a hotelID, customer Name and date range get the total cost incurred by the customer
      // Your code goes here.
      // ...
      // ...
      try{
		System.out.print("\tEnter hotelID: ");
		String hotelID = in.readLine();
		
		
		 int rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ hotelID);
		 while (rows == 0){
			System.out.print("\tInvalid Hotel ID. Enter hotel ID: ");
			hotelID = in.readLine();
		    rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ hotelID);
		 }
      
		System.out.print("\tEnter Customer first name: ");
		String fname = in.readLine();
		
		System.out.print("\tEnter Customer last name: ");
		String lname = in.readLine();
		
		 rows = 0;
		 rows = esql.executeQueryNoPrint("Select * from customer where fname = '"+ fname + "' AND lname = '" + lname + "'" );
		 
		 while (rows == 0){
			System.out.print("\tInvalid Name. ");
			
			System.out.print("\tEnter Customer's First Name: ");
			fname = in.readLine();
       
			System.out.print("\tEnter Customer's Last Name: ");
			lname = in.readLine();
			
		    rows = esql.executeQueryNoPrint("Select * from customer where fname = '"+ fname + "' AND lname = '" + lname + "'" );
		 }
	
		System.out.print("\tEnter start date: ");
		String startDate = in.readLine();
        
         while (startDate.charAt(2) != '/' || startDate.charAt(5) != '/'){
			System.out.print("\tInvalid date format. Enter Date (mm/dd/yyyy): ");
			startDate = in.readLine();
		 }
		
		
		System.out.print("\tEnter end date: ");
		String endDate = in.readLine();
		
      
         while (endDate.charAt(2) != '/' || endDate.charAt(5) != '/'){
			System.out.print("\tInvalid date format. Enter Date  (mm/dd/yyyy): ");
			endDate = in.readLine();
		 }
		
		String custID = (esql.executeQueryReturnData("Select C.customerID From Customer C where c.fname = '" + fname + "' AND c.lname = '" + lname + "' "));
	

		String query = "Select SUM(price) from booking  where customer = " + custID + " AND hotelID =" + hotelID + " AND bookingDate >= '" + startDate + "' AND bookingDate <= '" + endDate + "' GROUP BY customer";		
		System.out.print("\n");
		esql.executeQueryReturnDataMultiple(query);
		
	}catch(Exception e){
         System.err.println (e.getMessage());
     } 
      
      
      
      
   }//end totalCostForCustomer
   
   public static void listRepairsMade(DBProject esql){
	  // Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo
      // Your code goes here.
      // ...
      // ...
      
      try{
		System.out.print("\tEnter Company name: ");
		String companyName = in.readLine();
  
		  int rows = 0;
		 rows = esql.executeQueryNoPrint("Select * from maintenanceCompany where name = '"+ companyName + "'");
		 
		 while (rows == 0){
			System.out.print("\tInvalid CompanyID. Enter CompanyID: ");
			companyName = in.readLine();
		    rows = esql.executeQueryNoPrint("Select * from maintenanceCompany where name = '"+ companyName + "'");
		 }
		 
		
		String cmpID = (esql.executeQueryReturnData("Select cmpID From MaintenanceCompany where name = '" + companyName + "' "));
	

		String query = "Select rID, hotelID, repairtype, roomNo from repair  where mCompany = " + cmpID ;		
		System.out.print("\n");
		esql.executeQueryReturnDataMultiple(query);
		
	}catch(Exception e){
         System.err.println (e.getMessage());
     } 
      
   }//end listRepairsMade
   
   public static void topKMaintenanceCompany(DBProject esql){
	  // List Top K Maintenance Company Names based on total repair count (descending order)
      // Your code goes here.
      // ...
      // ...
      
      try{
		System.out.print("\tEnter K: ");
		String K = in.readLine();
		String query = "Select Count(*), m.name from repair r, MaintenanceCompany m where r.mCompany = m.cmpID group by m.name order by count desc limit " + K ;		
		System.out.print("\n");
		esql.executeQueryReturnDataMultiple(query);
		
	}catch(Exception e){
         System.err.println (e.getMessage());
     } 
      
   }
      //end topKMaintenanceCompany
   
   public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
	  // Given a hotelID, roomNo, get the count of repairs per year
      // Your code goes here.
      // ...
      // ...
      
      try{
      System.out.print("\tEnter hotelID: ");
		String hotelID = in.readLine();
		
		 int rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ hotelID);
		 while (rows == 0){
			System.out.print("\tInvalid Hotel ID. Enter hotel ID: ");
			hotelID = in.readLine();
		    rows = esql.executeQueryNoPrint("Select hotelID from hotel where HotelID = "+ hotelID);
		 }
		
 
		System.out.print("\tEnter roomNo: ");
		String roomNo = in.readLine();
	
		rows = esql.executeQueryNoPrint("Select roomNo from room where roomno = "+ roomNo + " AND HotelID = " + hotelID);
		 
		 while (rows == 0){
			System.out.print("\tInvalid Room No. Enter Room number: ");
			roomNo = in.readLine();
		    rows = esql.executeQueryNoPrint("Select roomNo from room where roomno = "+ roomNo + " AND HotelID = " + hotelID);
		 }
	
 
	String query = "Select Count(*), EXTRACT(Year from repairDate) from repair where hotelID = " + hotelID + " AND roomno = " + roomNo + "Group by EXTRACT(Year from repairDate)";
	esql.executeQueryReturnDataMultiple(query);
      }
      catch(Exception e){
         System.err.println (e.getMessage());
     } 

      
   }//end listRepairsMade

}//end DBProject
