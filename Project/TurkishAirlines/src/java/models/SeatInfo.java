// [A] [REFACTOR (id: RC70)] 18/06/25 - "Constructor has 16 parameters, which is greater than 7 authorized. Methods should not have too many parameters java:S107"

package models;

public class SeatInfo {
    public int economySeats;
    public int businessSeats;
    public int firstSeats;

    public SeatInfo(int economySeats, int businessSeats, int firstSeats) {
        this.economySeats = economySeats;
        this.businessSeats = businessSeats;
        this.firstSeats = firstSeats;
    }
}


