package models;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MuhammadHarris
 */
public class FBS 
{        
    public ArrayList<Features> populateFeatures(Connection con)
    {
        ArrayList<Features> features = new ArrayList();        
        
        try
        {            
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM Features";
            ResultSet rs = stmt.executeQuery(sql);

            
            while (rs.next())
            {
                boolean isChanged = rs.getBoolean("isChanged");

                int type = rs.getInt("type");

                double seatPitch = rs.getDouble("seatPitch");
                double seatWidth = rs.getDouble("seatWidth");

                Double newSeatPitch = rs.getDouble("NewSeatPitch");
                                                
                Double newSeatWidth = rs.getDouble("NewSeatWidth");
                
                String videoType = rs.getString("VideoType");
                String newVideoType = rs.getString("NewVideoType");

                String seatType = rs.getString("SeatType");
                String newSeatType = rs.getString("NewSeatType");

                String powerType = rs.getString("PowerType");
                String newPowerType = rs.getString("NewPowerType");


                String wifi = rs.getString("Wifi");
                String newWifi = rs.getString("NewWifi");

                String specialFood = rs.getString("SpecialFood");
                String newSpecialFood = rs.getString("NewSpecialFood");
                
                int price = rs.getInt("price");
                int newPrice = rs.getInt("newPrice");

                features.add(new Features(price,newPrice,type,isChanged, seatPitch, seatWidth,newSeatPitch,newSeatWidth,videoType,newVideoType,seatType,newSeatType,powerType,newPowerType,wifi,newWifi,specialFood,newSpecialFood));                
            }

        }
        catch (SQLException ex) {
            Logger.getLogger(FBS.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
        
        return features;                       
    }
    
    
    public ArrayList<Customer> populateCustomers(Connection con)
    {
        ArrayList<Customer> customers = new ArrayList();        
        
        try
        {            
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM Customers";
            ResultSet rs = stmt.executeQuery(sql);

            
            while (rs.next())
            {
                String name = rs.getString("name");
                String email = rs.getString("email");

                customers.add(new Customer(name,email, null));                
            }            
        }
        catch (SQLException ex) {
            Logger.getLogger(FBS.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
        return customers;                                      
    }



    public ArrayList<Flight> getAllFlights(Connection con, ArrayList<Customer> customers, ArrayList<Features> features) {
        ArrayList<Flight> flights = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM Flights";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Flight flight = loadFlightFromResultSet(rs);
                ArrayList<Seat> seats = loadSeatsForFlight(con, flight, customers, features);
                flight.setSeats(seats);
                flights.add(flight);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FBS.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }

        return flights;
    }

    // [REFACTOR (id: RC1)] 12/06/25 - "Refactor this method to reduce its Cognitive Complexity from 23 to the 15 allowed. java:S3776" [A]
    // [REFACTOR (id: RC70)] 18/06/25 - "Constructor has 16 parameters, which is greater than 7 authorized. Methods should not have too many parameters java:S107" [M]
    private Flight loadFlightFromResultSet(ResultSet rs) throws SQLException {
        boolean isChanged = rs.getBoolean("isChanged");
        String flightName = rs.getString("FlightName");
        int totalSeats = rs.getInt("TotalSeats");
        int currentSeats = rs.getInt("CurrentSeats");
        String departureCity = rs.getString("DepartureCity");
        String arrivalCity = rs.getString("ArrivalCity");
        Date departureDate = rs.getDate("DepartureDate");
        Date arrivalDate = rs.getDate("ArrivalDate");
        int economySeats = rs.getInt("EconomySeats");
        int businessSeats = rs.getInt("BusinessSeats");
        int firstSeats = rs.getInt("FirstSeats");
        int oldESeats = rs.getInt("OldESeats");
        int oldBSeats = rs.getInt("OldBSeats");
        int oldFSeats = rs.getInt("OldFSeats");
        int oldTSeats = rs.getInt("OldTSeats");


        FlightSeatHistory history = new FlightSeatHistory(oldESeats, oldBSeats, oldFSeats, oldTSeats);
        FlightSeatInfo seatInfo = new FlightSeatInfo(new ArrayList<>(), totalSeats, currentSeats, economySeats, businessSeats, firstSeats);
        FlightRouteInfo routeInfo = new FlightRouteInfo(departureCity, arrivalCity, departureDate, arrivalDate);

        return new Flight(flightName, isChanged, history, seatInfo, routeInfo);
    }

    private ArrayList<Seat> loadSeatsForFlight(Connection con, Flight flight, ArrayList<Customer> customers, ArrayList<Features> features) throws SQLException {
        ArrayList<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM Seats WHERE flightName = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, flight.getFlightName());
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int seatNumber = rs.getInt("SeatNumber");
            String customerEmail = rs.getString("CustomerEmail");
            String featureType = rs.getString("FeatureType");

            Customer customer = findCustomerByEmail(customers, customerEmail);
            Features feature = getFeatureByType(featureType, features);

            Seat seat = new Seat(seatNumber, flight, feature, customer);
            seats.add(seat);

            if (customer != null) {
                customer.setSeat(seat);
            }
        }

        rs.close();
        stmt.close();
        return seats;
    }

    private Customer findCustomerByEmail(ArrayList<Customer> customers, String email) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                return customer;
            }
        }
        return null;
    }

    private Features getFeatureByType(String type, ArrayList<Features> features) {
        switch (type) {
            case "Economy":
                return features.get(0);
            case "Business":
                return features.get(1);
            case "First Class":
                return features.get(2);
            default:
                return null;
        }
    }







    //--------------Methods Exposed to Web Services---------------------//
    public static int getPrice(String origin, String destination)
    {
        int price = 0;
        //get db path
        String p = "";

        try 
        {
            p = new File(FBS.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath() + "\\airlines.accdb";
        }
        catch (URISyntaxException ex) 
        {
            System.out.println("Error loading database file.");
        }

        String host =   "jdbc:ucanaccess://" + p;

        // Step 1: Loading or registering Oracle JDBC driver class
        try {

            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException cnfex) {

            System.out.println("Problem in loading or "
                    + "registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }
        
        try 
        {
            Connection con = DriverManager.getConnection(host);            
            
            //To prevent SQL Injection
            
            PreparedStatement statement = null;
            
            String q = "SELECT * FROM FLIGHTS WHERE DEPARTURECITY = ? AND ARRIVALCITY = ? AND ECONOMYSEATS > 0";
            
            statement = con.prepareStatement(q);
            
            statement.setString(1, origin);
            statement.setString(2, destination);

            ResultSet r = statement.executeQuery();

            boolean isFound = false;
            
            while(r.next() && !isFound)
            {
                isFound = true;                                
            }
            
            if (isFound)
            {
                q = "SELECT PRICE FROM FEATURES WHERE TYPE = 0";
                statement = con.prepareStatement(q);
                r = statement.executeQuery();

                while (r.next())
                {
                   price = r.getInt(1);                    
                }
            }
            
            statement.close();
            r.close();                
            con.close();            
        }
        catch (SQLException ex) 
        {
            
        }
        
        return price;
    }        
    
    
    public static int getSeats(String flightName, String date)
    {
        int seats = 0;
        
        //get db path
        String p = "";

        try 
        {
            p = new File(FBS.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath() + "\\airlines.accdb";
        }
        catch (URISyntaxException ex) 
        {
            System.out.println("Error loading database file.");
        }

        String host =   "jdbc:ucanaccess://" + p;

        // Step 1: Loading or registering Oracle JDBC driver class
        try {

            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException cnfex) {

            System.out.println("Problem in loading or "
                    + "registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }
        
        try 
        {
            Connection con = DriverManager.getConnection(host);            
            
            //To prevent SQL Injection
            
            PreparedStatement statement = null;
            
            String q = "SELECT CURRENTSEATS FROM FLIGHTS WHERE FLIGHTNAME = ? AND DEPARTUREDATE = ?";
            
            statement = con.prepareStatement(q);
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            java.util.Date d = format.parse(date);            
            
            statement.setString(1, flightName);
            statement.setTimestamp(2, new Timestamp(d.getTime()));
            
            ResultSet r = statement.executeQuery();

            boolean isFound = false;
            
            while(r.next())
            {
                seats = r.getInt(1);
            }
                        
            statement.close();
            r.close();                
            con.close();            
        }
        catch (SQLException | ParseException ex) 
        {
            
        }
        
        return seats;
    }        
    
    //-------------------------------FOR AUTOCOMPLETION THROUGH AJAX----------------------------//
    public ArrayList<String> getCities(Connection con, String city_name) 
    {
        ArrayList<String> list = new ArrayList();
        PreparedStatement ps = null;
        String data;
        try 
        {
            ps = con.prepareStatement("SELECT * FROM CITIES  WHERE CITYNAME  LIKE ?");
            ps.setString(1, city_name + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) 
            {
                data = rs.getString("CITYNAME");
                list.add(data);
            }
        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        return list;
    }
}


