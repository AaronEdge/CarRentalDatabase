import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the system management class. It contains all of the functions that interact with the database directly. It also manages the
 * state of the logged in user.
 * 
 * @author Aaron Edge (113612)
 */
public class SystemManager {
    
    // Database Server
    private final String host = "jdbc:derby://localhost:1527/Hire_From_US";
    private final String uName = "aaronedge";
    private final String uPass= "aaronedge";
    private Connection con;
    
    // Logged in user
    public String loginErrorString = "Login Error";
    
    private Integer LoggedInUserID;
    private String LoggedInUserName;
    private Integer CurrentPermissionLevel = 100;
    
    // Selection
    public Customer selectedCustomer;
    public Car selectedCar;
    public User selectedUser;
    public Hire selectedHire;
    
    public Date startDate;
    public Date endDate;
    
    //For Updates
    private boolean isUpdate;
    
    public boolean checkUpdate() {
        return isUpdate;
    }
    
    public void setIsUpdate(boolean inValue) {
        isUpdate = inValue;
    }
    
    /**
     * Connect to database
     */
    public void doConnect() {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            con = DriverManager.getConnection(host, uName, uPass);
            System.out.println("Connection Established");
        } catch(SQLException err){
            System.out.print(err.getMessage());
        }
    }
    
    /**
     * Log out the current user
     */
    public void Logout() {
        LoggedInUserID = null;
        LoggedInUserName = null;
        CurrentPermissionLevel = 100;
        MainClass.getInstance().myStageManager.GoToWindow("Login.fxml");
    }
    
    /**
     *  Attempt to login a user
     * 
     * @param UserID
     * @param Password
     * @return
     */
    public boolean Login(String UserID, String Password) {
        // validation
        if ("".equals(UserID)) {
            loginErrorString = "Please enter username";
            return false;
        }
        if("".equals(Password)) {
            loginErrorString = "Please enter password";
            return false;
        }
       
        // check database
        String query = "SELECT ID, USERNAME, USERPASSWORD, PERMISSIONLEVEL FROM USERS WHERE USERNAME='"+UserID+"'";
        Statement stmt = null;
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()==true){
            
                if (rs.getString("USERPASSWORD").equals(Password)) {
                    LoggedInUserID = rs.getInt("ID");
                    LoggedInUserName = rs.getString("USERNAME");
                    CurrentPermissionLevel = rs.getInt("PERMISSIONLEVEL");
                    System.out.println("User "+LoggedInUserName+" Logged In");
                    return true;
                } else
                {
                    loginErrorString = "Invalid Password";
                    return false;
                }
            } else {
                loginErrorString = "Invalid User";
                return false;
            }
        } catch(SQLException err) {
            System.out.print(err.getMessage());
        }
        loginErrorString = "Login Error";
        return false;
    }
    
    /**
     * This function is used to input all of the objects stored in memory into the database.
     */
    public void MakeNewHire() {
        AddNewHire(startDate, endDate, selectedCustomer.ID.getValue(), LoggedInUserID, selectedCar.LicenceNumber.getValue());
    }
    
    /**
     * Clears all the selected objects in memory.
     */
    public void ClearSelection() {
        selectedCustomer = null;
        selectedCar = null;

        startDate = null;
        endDate = null;
    }
    
    /**
     * Get the user name of the user currently logged in
     * @return
     */
    public String GetLoggedInUser()
    {
        return LoggedInUserName;
    }
    public int GetLoggedInUserID()
    {
        return LoggedInUserID;
    }
    
    /**
     * Get the permission level of the currently logged in user
     * @return
     */
    public int GetPermissionLevel()
    {
        return CurrentPermissionLevel;
    }
    
    /**
     *  Get the column names of a given table
     * 
     * @param tableName
     * @return
     */
    public String[] GetColNames(String tableName)
    {
        
        String query = "SELECT * FROM "+tableName;
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            // GetMetaData
            ResultSetMetaData metaData= rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] ColNames = new String[columnCount];
            
            for (int i=1; i<=columnCount; i++) {
                ColNames[i-1] = metaData.getColumnName(i);
            }
            return ColNames;
        } catch(SQLException err) {
            System.out.print(err.getMessage());
        }
        return new String[]{"Error"};
    }
    
    /**
     *  Returns an array of string with all of the names of the makes of cars stored in the database.
     * @return
     */
    public String[] GetAllMakes()
    {
        
        String query = "SELECT DISTINCT MAKE FROM CARS";
        Statement stmt = null;
        List<String> makeList = new ArrayList<String>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                makeList.add(rs.getString("MAKE"));
            }
            return makeList.toArray(new String[0]);
        } catch(SQLException err) {
            System.out.print(err.getMessage());
        }
        return new String[]{"Error"};
    }

    /**
     *  Returns an array of string with all of the names of the models of cars stored in the database.
     * @param myMake
     * @return
     */
    public String[] GetAllModels(String myMake)
    {
        
        String query = "SELECT DISTINCT MODEL FROM CARS WHERE MAKE = '"+myMake+"'";
        Statement stmt = null;
        List<String> modelList = new ArrayList<String>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                modelList.add(rs.getString("MODEL"));
            }
            return modelList.toArray(new String[0]);
        } catch(SQLException err) {
            System.out.print(err.getMessage());
        }
        return new String[]{"Error"};
    }
    
    /**
     *  Convert a date from string format to date format
     * 
     * @param indate
     * @return
     */
    public String convertStringToDate(Date indate)
    {
       String dateString = null;
       SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
       try{
            dateString = sdfr.format( indate );
       }catch (Exception ex ){
            System.out.println(ex);
       }
       return dateString;
    }
    
    /**
     *  Converts a data variable to a format useable by the database.
     * 
     * @param indate
     * @return
     */
    public String convertDateToStringForSQL(Date indate)
    {
       String dateString = null;
       SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd");
       try{
            dateString = sdfr.format( indate );
       }catch (Exception ex ){
            System.out.println(ex);
       }
       return dateString;
    }
    
    /**
     * helper function to convert date to a localdate
     * @param theDate
     * @return
     */
    public LocalDate getLocalDateFromDate(Date theDate) {
        Instant instant = theDate.toInstant();
        ZonedDateTime zdt = instant.atZone( ZoneId.systemDefault() );
        LocalDate ld = zdt.toLocalDate();
        return ld;
    }
    
    /**
     *  helper function to convert localdate to a date
     * 
     * @param theDate
     * @return
     */
    public Date getDateFromLocalDate(LocalDate theDate) {
        ZonedDateTime zdt = theDate.atStartOfDay(ZoneId.systemDefault());
        Instant instant = zdt.toInstant();
        Date d = java.util.Date.from( instant );
        return d;
    }
    
    /**
     * Get a list of all the customers in the customer table
     * @return
     */
    public ObservableList<Customer> getAllCustomersList() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        String query = "SELECT * FROM CUSTOMERS";
        Statement stmt = null;
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                customerList.add(new Customer(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), convertStringToDate(rs.getDate("DATEOFBIRTH")), String.valueOf(rs.getInt("PHONENUMBER")), rs.getString("ADDRESS"), rs.getString("LICENCENUMBER")));
            }
        } catch(SQLException err) {
            System.out.print(err.getMessage());
        }
        return customerList;
    }
    
    /**
     * Get a list of all the cars in the cars table
     * 
     * @return
     */
    public ObservableList<Car> getAllCarsList() {
        ObservableList<Car> carList = FXCollections.observableArrayList();
        String query = "SELECT * FROM CARS";
        Statement stmt = null;
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                carList.add(new Car(rs.getString("LICENCENUMBER"), rs.getString("MAKE"), rs.getString("MODEL"), rs.getBoolean("HASGPS"), rs.getDouble("RATE"), rs.getString("BODYTYPE"), rs.getInt("ODOMETERREADING")));
            }
        } catch(SQLException err) {
            System.out.print(err.getMessage());
        }
        return carList;
    }
    
    /**
     *  Get a list of all the users in the users table
     * @return
     */
    public ObservableList<User> getAllUsersList() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        String query = "SELECT * FROM USERS";
        Statement stmt = null;
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                userList.add(new User(rs.getInt("ID"), rs.getString("USERNAME"), rs.getInt("PERMISSIONLEVEL"), rs.getString("USERPASSWORD")));
            }
        } catch(SQLException err) {
            System.out.print(err.getMessage());
        }
        return userList;
    }
    
    /**
     *  Get a list of all the hires in the hires table
     * @return
     */
    public ObservableList<Hire> getAllHiresList() {
        ObservableList<Hire> hireList = FXCollections.observableArrayList();
        String query = "SELECT * FROM Hire";
        Statement stmt = null;
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                hireList.add(new Hire(rs.getInt("ID"), rs.getDate("STARTDATE"), rs.getDate("DATEEND"), rs.getInt("CUSTOMERID"), rs.getInt("USERID"), rs.getString("LICENCENUMBER")));
            }
        } catch(SQLException err) {
            System.out.print(err.getMessage());
        }
        return hireList;
    }
    
    /**
     *  Add a new customer to the customer table
     * 
     * @param firstName
     * @param lastName
     * @param dob
     * @param phone
     * @param address
     * @param licence
     * @param expiration
     * @param card
     * @param security
     * @return
     */
    public boolean AddNewCustomer(String firstName, String lastName, String dob, String phone, String address, String licence, String expiration, String card, String security) {
        boolean isSucessfull = false;
        
        String query = "INSERT INTO CUSTOMERS( FIRSTNAME, LASTNAME, DATEOFBIRTH, PHONENUMBER, ADDRESS, LICENCENUMBER, LICENCEEXPIRATIONDATE, CARDNUMBER, CARDSECURITYCODE ) "
                + "VALUES( '"+firstName+"', '"+lastName+"', '"+dob+"', "+phone+", '"+address+"', '"+licence+"', '"+expiration+"', "+card+", "+security+" )";
        System.out.println(query);
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException err) {
            System.out.print(err.getMessage());
            isSucessfull = false;
        }
        return isSucessfull;
    }
    
    /**
     *  Add a new car to the cars table
     *  
     * @param licenceNumber
     * @param make
     * @param model
     * @param type
     * @param hasGPS
     * @param rate
     * @return
     */
    public boolean AddNewCar(String licenceNumber, String make, String model, String type, boolean hasGPS, double rate, int Odometer) {
        boolean isSucessfull = false;
        
        String query = "INSERT INTO CARS( LICENCENUMBER, MAKE, MODEL, BODYTYPE, HASGPS, RATE, ODOMETERREADING ) "
                + "VALUES( '"+licenceNumber+"', '"+make+"', '"+model+"', '"+type+"', "+hasGPS+", "+rate+", "+Odometer+" )";
        System.out.println(query);
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException err) {
            System.out.print(err.getMessage());
            isSucessfull = false;
        }
        return isSucessfull;
    }
    
    /**
     * Add a new user to the users table
     * 
     * @param myUserName
     * @param myPermissionLevel
     * @param myPassword
     * @return
     */
    public boolean AddNewUser(String myUserName, int myPermissionLevel, String myPassword) {
        boolean isSucessfull = false;
        
        String query = "INSERT INTO USERS( USERNAME, PERMISSIONLEVEL, USERPASSWORD) "
                + "VALUES( '"+myUserName+"', "+myPermissionLevel+", '"+myPassword+"' )";
        System.out.println(query);
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException err) {
            System.out.print(err.getMessage());
            isSucessfull = false;
        }
        return isSucessfull;
    }
    
    /**
     * Add new hire to the hire table
     *  
     * @param myStartDate
     * @param myEndDate
     * @param myCustomerID
     * @param myUserID
     * @param myLicence
     * @return
     */
    public boolean AddNewHire(Date myStartDate, Date myEndDate, int myCustomerID, int myUserID, String myLicence) {
        boolean isSucessfull = false;
        
        String query = "INSERT INTO HIRE( STARTDATE, DATEEND, CUSTOMERID, USERID, LICENCENUMBER) "
                + "VALUES( '"+convertDateToStringForSQL(myStartDate)+"', '"+convertDateToStringForSQL(myEndDate)+"', "+myCustomerID+", "+myUserID+", '"+myLicence+"' )";
        System.out.println(query);
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException err) {
            System.out.print(err.getMessage());
            isSucessfull = false;
        }
        return isSucessfull;
    }
    
    /**
     *  Delete a customer record from the database
     * @param customerID
     */
    public void DeleteCustomer(int customerID) {
        String query = "DELETE FROM CUSTOMERS WHERE ID = "+customerID;
        System.out.println(query);
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException err) {
            System.out.print(err.getMessage());
        }
    }
    
    /**
     * Delete a car record from the database
     * @param licenceNumber
     */
    public void DeleteCar(String licenceNumber) {
        String query = "DELETE FROM CARS WHERE LICENCENUMBER = '"+licenceNumber+"'";
        System.out.println(query);
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException err) {
            System.out.print(err.getMessage());
        }
    }
    
    /**
     * Delete a user record from the database
     * @param userID
     */
    public void DeleteUser(int userID) {
        String query = "DELETE FROM USERS WHERE ID = "+userID;
        System.out.println(query);
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException err) {
            System.out.print(err.getMessage());
        }
    }
    
    /**
     * Delete a hire record from the database
     * @param hireID
     */
    public void DeleteHire(int hireID) {
        String query = "DELETE FROM HIRE WHERE ID = "+hireID;
        System.out.println(query);
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException err) {
            System.out.print(err.getMessage());
        }
    }
    
    /**
     * Calculates the cost of hire for a given hire id
     * @param HireID
     * @return
     */
    public double CalculateCostOfHire(int HireID) {
        double insuranceCost = 20;
        double cost = 0.0;
        String query = "SELECT HIRE.STARTDATE, HIRE.DATEEND, CARS.RATE FROM HIRE LEFT JOIN CARS ON HIRE.LICENCENUMBER = CARS.LICENCENUMBER WHERE HIRE.ID = "+HireID;
        Statement stmt = null;
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(query);
            if(rs.first()){
                long diff = rs.getDate("DATEEND").getTime()-rs.getDate("STARTDATE").getTime();
                int days = (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                cost = days*(rs.getInt("RATE")+insuranceCost);
            } else {
                System.out.println("No Hire with ID: "+HireID);
            }
        } catch(SQLException err) {
            System.out.print(err.getMessage());
        }
        return cost;
    }
    /**
     * Calculates the cost of hire using data in memory
     * @return
     */
    public double CalculateCostOfHire() {
        double insuranceCost = 20;
        double cost = 0.0;
        long diff = endDate.getTime()-startDate.getTime();
        int days = (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        cost = days*(selectedCar.getRate()+insuranceCost);
        return cost;
    }
    
    /**
     * Calculates the commission for all of the users that have the Sales assistant permission level 
     * and returns a string array of of each users id and commission earned
     * @return
     */
    public String[] CalculateCommission() {
        List<String> userPrintout = new ArrayList<String>();
        String Users = "SELECT ID, USERNAME FROM USERS WHERE PERMISSIONLEVEL = 3";
        Statement USRstmt = null;
        Statement HIREstmt = null;
        try {
            USRstmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            HIREstmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet UsersResults = USRstmt.executeQuery(Users);
            while(UsersResults.next()){
                double totalCommission = 0.0;
                String HireString = "SELECT HIRE.ID FROM HIRE LEFT JOIN USERS ON HIRE.USERID = USERS.ID WHERE USERS.ID = "+UsersResults.getInt("ID");
                ResultSet hires = HIREstmt.executeQuery(HireString);
                while(hires.next()){
                    totalCommission = totalCommission+CalculateCostOfHire(hires.getInt("ID"));
                }
                totalCommission = totalCommission*0.05;
                userPrintout.add(UsersResults.getString("USERNAME")+"   £"+totalCommission);
            }
        } catch(SQLException err) {
            System.out.print(err.getMessage());
        }
        return userPrintout.toArray(new String[0]);
    }
    
    

    public boolean UpdateCustomer(Integer customerID,String firstName, String lastName, String dob, String phoneNumber, String address, String licence, String expiration, String cardNumber, String security) {
        boolean isSucessfull = false;

         String query = "UPDATE CUSTOMERS SET FIRSTNAME = '"+firstName+"', LASTNAME = '"+lastName+"', DATEOFBIRTH = '"+dob+"', PHONENUMBER = "+phoneNumber+", ADDRESS = '"+address+"', LICENCENUMBER = '"+licence+"', LICENCEEXPIRATIONDATE = '"+expiration+"', CARDNUMBER = "+cardNumber+", CARDSECURITYCODE = "+security+" WHERE ID = "+customerID+"";
         System.out.println(query);
         Statement stmt = null;
         try {
             stmt = con.createStatement();
             stmt.executeUpdate(query);
         } catch(SQLException err) {
             System.out.print(err.getMessage());
             isSucessfull = false;
         }
         return isSucessfull;
    }
    
    public boolean UpdateCar(String licenceNumber, String make, String model, String type, boolean hasGPS, double rate, int Odometer) {
        boolean isSucessfull = false;
        
        String query = "UPDATE CARS SET MAKE = '"+make+"', MODEL = '"+model+"', BODYTYPE = '"+type+"', HASGPS = "+hasGPS+", RATE = "+rate+", ODOMETERREADING = "+Odometer+" WHERE LICENCENUMBER = '"+licenceNumber+"'";
        System.out.println(query);
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException err) {
            System.out.print(err.getMessage());
            isSucessfull = false;
        }
        return isSucessfull;
    }
    
    public boolean UpdateUser(Integer userID, String myUserName, int myPermissionLevel, String myPassword) {
        boolean isSucessfull = false;
        
        String query = "UPDATE USERS SET USERNAME = '"+myUserName+"', PERMISSIONLEVEL = "+myPermissionLevel+", USERPASSWORD = '"+myPassword+"' WHERE ID = "+userID+"";
        System.out.println(query);
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException err) {
            System.out.print(err.getMessage());
            isSucessfull = false;
        }
        return isSucessfull;
    }
    
    public boolean UpdateHire(int hireID, Date myStartDate, Date myEndDate, int myCustomerID, int myUserID, String myLicence) {
        boolean isSucessfull = false;
        
        String query = "UPDATE HIRE SET STARTDATE = '"+convertDateToStringForSQL(myStartDate)+"', DATEEND = '"+convertDateToStringForSQL(myEndDate)+"', CUSTOMERID = "+myCustomerID+", USERID = "+myUserID+", LICENCENUMBER = '"+myLicence+"' WHERE ID = "+hireID+"";
        System.out.println(query);
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException err) {
            System.out.print(err.getMessage());
            isSucessfull = false;
        }
        return isSucessfull;
    }
    
    public boolean UpdateCarOdometer(String licenceNumber, int Odometer) {
        boolean isSucessfull = false;
        
        String query = "UPDATE CARS SET ODOMETERREADING = "+Odometer+" WHERE LICENCENUMBER = "+licenceNumber+"";
        System.out.println(query);
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException err) {
            System.out.print(err.getMessage());
            isSucessfull = false;
        }
        return isSucessfull;
    }
    
    // Extra functionality
    
    // Most Hired car
    
    public String GetMostHiredCarName() {
        String carName = "No Car";
        
        String query = "SELECT LICENCENUMBER, COUNT(LICENCENUMBER) AS numberofhires FROM HIRE GROUP BY LICENCENUMBER ORDER BY numberofhires";
        System.out.println(query);
        Statement stmt = null;
        
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery(query);
             rs.last();
             carName = rs.getString("LICENCENUMBER");
             
             String carQuery = "SELECT MAKE, MODEL FROM CARS WHERE LICENCENUMBER = '"+carName+"'";
             ResultSet carRs = stmt.executeQuery(carQuery);
             carRs.first();
             carName = carRs.getString("MAKE")+" "+carRs.getString("MODEL");
             
        } catch(SQLException err) {
            System.out.print(err.getMessage());
        }
        return carName;
    }
    
    public String GetBestSalesPerson() {
        String userName = "No User";
        String userID;
        
        String query = "SELECT COUNT(ID) AS Occurences, USERID FROM HIRE GROUP BY USERID ORDER BY Occurences";
        System.out.println(query);
        Statement stmt = null;
        
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery(query);
             rs.last();
             userID = rs.getString("USERID");
             
             String carQuery = "SELECT USERNAME FROM USERS WHERE ID = "+userID+"";
             ResultSet userRS = stmt.executeQuery(carQuery);
             userRS.first();
             userName = userRS.getString("USERNAME");
             
        } catch(SQLException err) {
            System.out.print(err.getMessage());
        }
        return userName;
    }
}
