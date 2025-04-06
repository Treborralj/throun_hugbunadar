package throunhugbunadar.hotelbokanir.vidmot;

import javafx.scene.control.Label;
import throunhugbunadar.hotelbokanir.vinnsla.Booking;

import java.util.ArrayList;
import java.util.List;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing :
 *
 *****************************************************************************/
public class BookingController {
    public Label fxUsersName;
    public Label fxCheckInDate;
    public Label fxCheckOutDate;
    public Label fxHotelName;
    public Label fxNumberOfRooms;

    public void setBookingInfo(String hotelName, String checkIn, String checkOut, String user, int rooms) {
        fxHotelName.setText(hotelName);
        fxCheckInDate.setText(checkIn);
        fxCheckOutDate.setText(checkOut);
        fxUsersName.setText(user);
        fxNumberOfRooms.setText(String.valueOf(rooms));
    }
}
