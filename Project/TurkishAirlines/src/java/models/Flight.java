package models;


import java.sql.Date;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MuhammadHarris
 */
public class Flight {
    
    String flightName;
    
    ArrayList<Seat> seats;
    int totalSeats;
    int currentSeats;
    
    String departureCity;
    String arrivalCity;
    
    Date departureDate;
    Date arrivalDate;
    
    int economySeats;
    int businessSeats;
    int firstSeats;
    
    int oldESeats;
    int oldBSeats;
    int oldFSeats;
    int oldTSeats;
    
    public boolean isChanged;

    // [REFACTOR (id: RC70)] 18/06/25 - "Constructor has 16 parameters, which is greater than 7 authorized. Methods should not have too many parameters java:S107" [M]
    public Flight(String flightName, boolean isChanged, FlightSeatHistory history,
                  FlightSeatInfo seatInfo, FlightRouteInfo routeInfo) {

        this.flightName = flightName;
        this.isChanged = isChanged;

        this.oldESeats = history.oldESeats;
        this.oldBSeats = history.oldBSeats;
        this.oldFSeats = history.oldFSeats;
        this.oldTSeats = history.oldTSeats;

        this.seats = seatInfo.seats;
        this.totalSeats = seatInfo.totalSeats;
        this.currentSeats = seatInfo.currentSeats;
        this.economySeats = seatInfo.economySeats;
        this.businessSeats = seatInfo.businessSeats;
        this.firstSeats = seatInfo.firstSeats;

        this.departureCity = routeInfo.departureCity;
        this.arrivalCity = routeInfo.arrivalCity;
        this.departureDate = routeInfo.departureDate;
        this.arrivalDate = routeInfo.arrivalDate;
    }


    public boolean isEmpty()
    {
        return (totalSeats==currentSeats);
    }
    
            
    public String getFlightName()
    {
        return flightName;
    }
    
    
    public int getOldESeats()
    {
        return oldESeats;
    }
    
    public int getOldBSeats()
    {
        return oldBSeats;
    }

    public int getOldFSeats()
    {
        return oldFSeats;
    }
    
    public int getOldTSeats()
    {
        return oldTSeats;
    }

    public int getEconomySeats()
    {
        return economySeats;
    }

    public int getBusinessSeats()
    {
        return businessSeats;
    }

    public int getFirstSeats()
    {
        return firstSeats;
    }

    public int getTotalSeats()
    {
        return totalSeats;
    }
    
    
    
    
    public void setOldESeats(int s)
    {
        oldESeats = s;
    }
    
    public void setOldBSeats(int s)
    {
        oldBSeats = s;
    }

    public void setOldFSeats(int s)
    {
        oldFSeats = s;
    }
    
    public void setOldTSeats(int s)
    {
        oldTSeats = s;
    }

    public void setEconomySeats(int s)
    {
        economySeats = s;
    }

    public void setBusinessSeats(int s)
    {
        businessSeats = s;
    }

    public void setFirstSeats(int s)
    {
        firstSeats = s;
    }

    public void setTotalSeats(int s)
    {
        totalSeats = s;
    }
    
    public void setCurrentSeats(int s)
    {
        currentSeats = s;
    }
    
    
    
    public String getDepartureCity()
    {
        return departureCity;
    }
    
    public String getArrivalCity()
    {
        return arrivalCity;
    }
    
    public Date getDepartureDate()
    {
        return departureDate;
    }
    
    public Date getArrivalDate()
    {
        return arrivalDate;
    }
    
    
    public int getCurrentSeats()
    {
        return currentSeats;
    }
    
    public void setCustomer (Customer c)            
    {
        seats.get(totalSeats-currentSeats).setCustomer(c);
        c.setSeat(seats.get(totalSeats-currentSeats));
        currentSeats--;
    }
    
    public void setSeats (ArrayList<Seat> s)
    {
        seats = s;
    }
}

