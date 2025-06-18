// [A] [REFACTOR (id: RC70)] 18/06/25 - "Constructor has 16 parameters, which is greater than 7 authorized. Methods should not have too many parameters java:S107"

package models;

import java.sql.Date;

public class FlightInfo {
    public String flightName;
    public int totalSeats;
    public int currentSeats;
    public String departureCity;
    public String arrivalCity;
    public Date departureDate;
    public Date arrivalDate;

    public FlightInfo(String flightName, int totalSeats, int currentSeats, String departureCity, String arrivalCity, Date departureDate, Date arrivalDate) {
        this.flightName = flightName;
        this.totalSeats = totalSeats;
        this.currentSeats = currentSeats;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }
}


