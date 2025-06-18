// [A] [REFACTOR (id: RC70)] 18/06/25 - "Constructor has 16 parameters, which is greater than 7 authorized. Methods should not have too many parameters java:S107"

package models;

import java.sql.Date;

public class FlightRouteInfo {
    public String departureCity;
    public String arrivalCity;
    public Date departureDate;
    public Date arrivalDate;

    public FlightRouteInfo(String departureCity, String arrivalCity,
                           Date departureDate, Date arrivalDate) {
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }
}




