package throunhugbunadar.hotelbokanir.vinnsla;

/******************************************************************************
 * @author
 * Tölvupóstur:
 * Lýsing :
 *
 *****************************************************************************/
public class Booking {
    private final int bookingId;
    private final int userId;
    private final int hotelId;
    private final String checkIn;
    private final String checkOut;
    private final int numberOfRooms;
    private final String hotelName;
    private final int totalPrice;

    public Booking(int bookingId, int hotelId, int userId, String checkIn, String checkOut, int numberOfRooms, String hotelName, int totalPrice) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfRooms = numberOfRooms;
        this.hotelName = hotelName;
        this.totalPrice = totalPrice;
    }

    public int getUserId() {
        return userId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public String getHotelName() {
        return hotelName;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "You have booked " + numberOfRooms + " room/s at " + hotelName + " from " + checkIn + " to " + checkOut;
    }
}
