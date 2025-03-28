package throunhugbunadar.hotelbokanir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import throunhugbunadar.hotelbokanir.vidmot.SignInController;
import throunhugbunadar.hotelbokanir.vinnsla.GagnasafnsTenging;
import throunhugbunadar.hotelbokanir.vinnsla.Hotel;
import throunhugbunadar.hotelbokanir.vinnsla.HotelVinnsla;
import throunhugbunadar.hotelbokanir.vinnsla.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class HotelbokanirController {
    @FXML
    private Label fxNameLabel;
    @FXML
    private Label fxErrorLabel;
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
    private User user;
    private ObservableList<Hotel> hotel = FXCollections.observableArrayList();

    @FXML
    public void onSearch(ActionEvent event) {
        if(fxCheckIn.getValue() == null || fxCheckOut.getValue() == null){
            fxErrorLabel.setText("Please select a check-in and check-out date");
        }else {
            String checkInDagur = fxCheckIn.getValue().toString();
            String checkOutDagur = fxCheckOut.getValue().toString();
            boolean pool = fxPool.isSelected();
            boolean gym = fxGym.isSelected();
            boolean bar = fxBar.isSelected();
            String nameOfHotel = fxNameOfhotel.getText().trim();

            hotel.clear();

            HotelVinnsla vinnsla = new HotelVinnsla();
            List<Hotel> listiLausHotel = vinnsla.findAvailableHotels(checkInDagur, checkOutDagur, pool, gym, bar, nameOfHotel);
            hotel.addAll(listiLausHotel);
            fxListView.setItems(hotel);
            fxErrorLabel.setText("");
        }
    }

    public void fxOpenSignIn(ActionEvent event) throws IOException {
       try {
           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sign-in-dialog.fxml"));
           DialogPane SignInDialogPane = fxmlLoader.load();

           Dialog<ButtonType> dialog = new Dialog<>();
           dialog.setTitle("Sign In");
           dialog.setDialogPane(SignInDialogPane);

           Optional<ButtonType> result = dialog.showAndWait();

           if (result.isPresent() && result.get() == ButtonType.OK) {
               SignInController controller = fxmlLoader.getController();
               String username = controller.getUserName();
               String password = controller.getPassword();
               String email = controller.getEmail();
               user = new User(username, password, email);
           }
       }catch (IOException e){
           e.printStackTrace();
       }
       fxNameLabel.setText(user.getUsername());
    }
}