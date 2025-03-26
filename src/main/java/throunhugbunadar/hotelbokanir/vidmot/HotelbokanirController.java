package throunhugbunadar.hotelbokanir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import throunhugbunadar.hotelbokanir.vinnsla.GagnasafnsTenging;
import throunhugbunadar.hotelbokanir.vinnsla.Hotel;
import throunhugbunadar.hotelbokanir.vinnsla.HotelVinnsla;

import java.util.List;

public class HotelbokanirController {
    @FXML
    private CheckBox fxBar;
    @FXML
    private CheckBox fxPool;
    @FXML
    private CheckBox fxGym;
    @FXML
    private TextField fxNameOfhotel;

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
        boolean pool = fxPool.isSelected();
        boolean gym = fxGym.isSelected();
        boolean bar = fxBar.isSelected();
        String nameOfHotel = fxNameOfhotel.getText().trim();

        hotel.clear();

        List<Hotel> listiLausHotel = HotelVinnsla.finnaLausHotel(checkInDagur, checkOutDagur, pool, gym, bar, nameOfHotel);
        hotel.addAll(listiLausHotel);
        fxListView.setItems(hotel);
    }

}