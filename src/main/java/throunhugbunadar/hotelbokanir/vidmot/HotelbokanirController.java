package throunhugbunadar.hotelbokanir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import throunhugbunadar.hotelbokanir.vidmot.SignInController;
import throunhugbunadar.hotelbokanir.vinnsla.GagnasafnsTenging;
import throunhugbunadar.hotelbokanir.vinnsla.Hotel;
import throunhugbunadar.hotelbokanir.vinnsla.HotelVinnsla;
import throunhugbunadar.hotelbokanir.vinnsla.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.net.URL;
import java.util.ResourceBundle;

public class HotelbokanirController implements Initializable {
    @FXML
    private VBox fxSearchResultBox;
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
    //@FXML
    //private TextField fxNameOfhotel;
    @FXML
    private TextField fxNumRooms;
    @FXML
    private ComboBox<String> fxHotelName;
    @FXML
    private ComboBox<String> fxLocation;

    @FXML
    private ListView<Hotel> fxListView;
    @FXML
    private DatePicker fxCheckIn;
    @FXML
    private DatePicker fxCheckOut;
    private User user;
    private ObservableList<Hotel> hotels = FXCollections.observableArrayList();
    private final String[] places = {"No preference","Reykjavik", "Selfoss", "Akureyri", "Egilsstadir", "Vik","Hvammstangi","Isafjordur"};
    private final String[] htlNames = {"No preference","Hotel Reykjavik", "Hotel Selfoss", "Hotel Akureyri", "Hotel Egilsstadir", "Hotel Vik","Hotel Hvammstangi","Hotel Hilton","Hotel Isafjordur","Hotel Fron","Hotel Austur","Hotel Hraun","Hotel Nord"};

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //ObservableList<String> locs =
                //FXCollections.observableArrayList(places);
        fxLocation.setItems(FXCollections.observableArrayList(places));
        fxLocation.setValue("No preference");

        //ObservableList<String> names =
                //FXCollections.observableArrayList(htlNames);
        fxHotelName.setItems(FXCollections.observableArrayList(htlNames));
        fxHotelName.setValue("No preference");

        fxErrorLabel.setText("");

    }


    @FXML
    public void onSearch(ActionEvent event) {
        int numRooms;
        try {
            numRooms = Integer.parseInt(fxNumRooms.getText().trim());
            assert numRooms >= 0;
        } catch(Exception e) {
            fxErrorLabel.setText("Please provide the number of rooms as a non-negative integer");
            return;
        }
        if(fxCheckIn.getValue() == null || fxCheckOut.getValue() == null){
            fxErrorLabel.setText("Please select a check-in and check-out date");
        }else {
            String checkInDagur = fxCheckIn.getValue().toString();
            String checkOutDagur = fxCheckOut.getValue().toString();
            boolean pool = fxPool.isSelected();
            boolean gym = fxGym.isSelected();
            boolean bar = fxBar.isSelected();
            String hotelName = fxHotelName.getSelectionModel().getSelectedItem();
            String location = fxLocation.getSelectionModel().getSelectedItem();

            hotels.clear();

            //System.out.print(hotelName +" "+ location);

            HotelVinnsla vinnsla = new HotelVinnsla();
            List<Hotel> listiLausHotel = vinnsla.findAvailableHotels(location,checkInDagur, checkOutDagur, pool, gym, bar, hotelName);
            hotels.addAll(listiLausHotel);

            System.out.print("hotels found: "+listiLausHotel.size());

            fxListView.setItems(hotels);
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