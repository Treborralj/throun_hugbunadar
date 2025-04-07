package throunhugbunadar.hotelbokanir.vidmot;

import javafx.scene.control.Label;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : Controller for the booking dialog
 *****************************************************************************/
public class BookingController {
    public Label fxUsersName;
    public Label fxCheckInDate;
    public Label fxCheckOutDate;
    public Label fxHotelName;
    public Label fxNumberOfRooms;
    public Label fxTotalPrice;

    /**
     * Sets the labels to the given values
     * @param hotelName The name of a hotel (String)
     * @param checkIn A check-in date (String)
     * @param checkOut A check-out date (String)
     * @param user The name of a user (String)
     * @param rooms Amount of rooms booked (int)
     * @param price Price of the hotel (int)
     */
    public void setBookingInfo(String hotelName, String checkIn, String checkOut, String user, int rooms, int price) {
        fxHotelName.setText(hotelName);
        fxCheckInDate.setText(checkIn);
        fxCheckOutDate.setText(checkOut);
        fxUsersName.setText(user);
        fxNumberOfRooms.setText(String.valueOf(rooms));
        fxTotalPrice.setText(String.valueOf(price));
    }
}
