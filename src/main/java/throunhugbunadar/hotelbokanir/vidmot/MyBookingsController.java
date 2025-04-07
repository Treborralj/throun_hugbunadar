package throunhugbunadar.hotelbokanir.vidmot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import throunhugbunadar.hotelbokanir.vinnsla.Booking;

public class MyBookingsController {
    @FXML
    private ListView<Booking> fxBookingList;

    private final ObservableList<Booking> bookings = FXCollections.observableArrayList();

    public void setBookingsList(ObservableList<Booking> list) {
        bookings.setAll(list);
        fxBookingList.setItems(bookings);
    }


}

