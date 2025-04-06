package throunhugbunadar.hotelbokanir.vinnsla;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
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

    public Booking(int bookingId, int hotelId, int userId, String checkIn, String checkOut, int numberOfRooms){
        this.bookingId = bookingId;
        this.userId = userId;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfRooms = numberOfRooms;
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

    @Override
    public String toString() {
        return "You have booked " + numberOfRooms + " room from " + checkIn + " to " + checkOut;
        //þarf að prenta hótel nafn ekki hótel id
    }
}
