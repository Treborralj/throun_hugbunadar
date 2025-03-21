package throunhugbunadar.hotelbokanir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import throunhugbunadar.hotelbokanir.vinnsla.GagnasafnsTenging;
import throunhugbunadar.hotelbokanir.vinnsla.Hotel;
import throunhugbunadar.hotelbokanir.vinnsla.HotelVinnsla;

import java.util.List;

public class HotelbokanirController {
    @FXML
    private TextField fxTextField;
    @FXML
    private ListView<Hotel> fxListView;
    @FXML
    private DatePicker fxCheckIn;
    @FXML
    private DatePicker fxCheckOut;
    private ObservableList<Hotel> hotel = FXCollections.observableArrayList();

    @FXML
    public void onSearch(ActionEvent event) {
        String checkInDagur = fxCheckIn.getValue().toString();
        String checkOutDagur = fxCheckOut.getValue().toString();

        hotel.clear();

        List<Hotel> listiLausHotel = HotelVinnsla.finnaLausHotel(checkInDagur, checkOutDagur);
        hotel.addAll(listiLausHotel);
        fxListView.setItems(hotel);
    }

}