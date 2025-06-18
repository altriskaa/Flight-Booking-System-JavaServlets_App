// [A] [REFACTOR (id: RC70)] 18/06/25 - "Constructor has 16 parameters, which is greater than 7 authorized. Methods should not have too many parameters java:S107"

package models;

import java.util.ArrayList;

public class FlightSeatInfo {
    public int economySeats;
    public int businessSeats;
    public int firstSeats;
    public int totalSeats;
    public int currentSeats;
    public ArrayList<Seat> seats;

    public FlightSeatInfo(ArrayList<Seat> seats, int totalSeats, int currentSeats,
                          int economySeats, int businessSeats, int firstSeats) {
        this.seats = seats;
        this.totalSeats = totalSeats;
        this.currentSeats = currentSeats;
        this.economySeats = economySeats;
        this.businessSeats = businessSeats;
        this.firstSeats = firstSeats;
    }
}
