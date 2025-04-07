package throunhugbunadar.hotelbokanir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import throunhugbunadar.hotelbokanir.vidmot.BookingController;
import throunhugbunadar.hotelbokanir.vinnsla.*;
import throunhugbunadar.hotelbokanir.UserController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.net.URL;
import java.util.ResourceBundle;

public class HotelController implements Initializable {
    @FXML
    private TextField fxNumRooms;
    @FXML
    private Button fxOpenSignIn;
    @FXML
    private VBox fxSearchResultBox;
    @FXML
    private Button fxNameLabel;
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
    private final UserController userCon = new UserController(this);


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fxLocation.setItems(FXCollections.observableArrayList(places));
        fxLocation.setValue("No preference");

        fxHotelName.setItems(FXCollections.observableArrayList(htlNames));
        fxHotelName.setValue("No preference");

        fxErrorLabel.setText("");
        fxOpenSignIn.setText("Sign-in");
    }

    @FXML
    public void onSearch(ActionEvent event) {
        fxErrorLabel.setText("");
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

            List<Hotel> listiLausHotel = HotelDB.findAvailableHotels(location,checkInDagur, checkOutDagur, pool, gym, bar, hotelName);
            hotels.addAll(listiLausHotel);

            System.out.print("hotels found: "+listiLausHotel.size());
            fxListView.setItems(hotels);
        }
    }

    public void fxOpenSignIn(ActionEvent event) {
        if (fxOpenSignIn.getText().equals("Sign-out")) {
            userCon.signOut();
            fxOpenSignIn.setText("Sign-in");
            fxNameLabel.setText("Guest");
            return;
        }
        fxErrorLabel.setText("");
        boolean success = false;
        try {
            success = userCon.signIn();
        } catch(Exception e) {
            fxErrorLabel.setText("Could not sign in");
        }
        if(!success){
            fxErrorLabel.setText("Could not sign in");
        }
        else if (userCon.isSignedIn()) {
            fxNameLabel.setText(userCon.getUser().getUsername());
            fxOpenSignIn.setText("Sign-out");
            fxNameLabel.setDisable(false);
        }
    }

    public void fxOpenBooking(){
        fxErrorLabel.setText("");
        // && fxCheckIn.getValue() != null && fxCheckOut.getValue() != null && fxNumberOfRooms.getText().isEmpty() && fxListView.getSelectionModel().getSelectedItem() == null
        if(user != null) {
            try {
                Hotel selectedHotel = fxListView.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("booking-dialog.fxml"));
                DialogPane bookingDialogPane = fxmlLoader.load();

                BookingController controller = fxmlLoader.getController();
                String hotelName = selectedHotel.getName();
                String checkIn = fxCheckIn.getValue().toString();
                String checkOut = fxCheckOut.getValue().toString();
                String username = user.getUsername();
                int rooms = Integer.parseInt(fxNumRooms.getText());

                controller.setBookingInfo(hotelName, checkIn, checkOut, username, rooms);

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirm info");
                dialog.setDialogPane(bookingDialogPane);
                Optional<ButtonType> result = dialog.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    BookingDB.addBooking(selectedHotel.getId(), user.getUserID(), checkIn, checkOut, rooms);
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }
        else{
            fxErrorLabel.setText("Before booking you must sign-in, select check-in and check-out dates, select a hotel and select the number of rooms needed");
        }
    }

    public void fxOpenProfile() {
        userCon.openProfile();
        fxNameLabel.setDisable(true);
    }

    public void signout() {
        fxNameLabel.setDisable(true);
        fxNameLabel.setText("Guest");
        fxOpenSignIn.setText("Sign-in");
    }

    public void profileButtonDisabled(boolean b) {fxNameLabel.setDisable(b);}

}