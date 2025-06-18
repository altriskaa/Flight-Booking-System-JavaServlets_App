// [A] [REFACTOR (id: RC70)] 18/06/25 - "Constructor has 16 parameters, which is greater than 7 authorized. Methods should not have too many parameters java:S107"


package models;

public class OldSeatInfo {
    public int oldESeats;
    public int oldBSeats;
    public int oldFSeats;
    public int oldTSeats;

    public OldSeatInfo(int oldESeats, int oldBSeats, int oldFSeats, int oldTSeats) {
        this.oldESeats = oldESeats;
        this.oldBSeats = oldBSeats;
        this.oldFSeats = oldFSeats;
        this.oldTSeats = oldTSeats;
    }
}
