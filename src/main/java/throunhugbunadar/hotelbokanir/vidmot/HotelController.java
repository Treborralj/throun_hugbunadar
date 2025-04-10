package throunhugbunadar.hotelbokanir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import throunhugbunadar.hotelbokanir.vidmot.BookingController;
import throunhugbunadar.hotelbokanir.vidmot.MyBookingsController;
import throunhugbunadar.hotelbokanir.vinnsla.*;
import throunhugbunadar.hotelbokanir.UserController;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    private Button fxNameLabel;
    @FXML
    private Label fxErrorLabel;
    @FXML
    private CheckBox fxBar;
    @FXML
    private CheckBox fxPool;
    @FXML
    private CheckBox fxGym;
    @FXML
    private Button fxBookingsButton;
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
    private ObservableList<Hotel> hotels = FXCollections.observableArrayList();
    private final String[] places = {"No preference","Reykjavik", "Selfoss", "Akureyri", "Egilsstadir", "Hella","Hvammstangi","Isafjordur"};
    private final String[] htlNames = {"No preference","Hotel Reykjavik", "Hotel Selfoss", "Hotel Akureyri", "Hotel Egilsstadir", "Hotel Hella","Hotel Hvammstangi","Hotel Hilton","Hotel Isafjordur","Hotel Fron","Hotel Austur","Hotel Hraun","Hotel Nord"};
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
        try {
            int number = Integer.parseInt(fxNumRooms.getText().trim());
        } catch (NumberFormatException e) {
            fxErrorLabel.setText("Please provide the number of rooms as a non-negative integer");
        }
        if(Integer.parseInt(fxNumRooms.getText().trim()) <= 0) {
            fxErrorLabel.setText("Please provide the number of rooms as a non-negative integer");
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

            List<Hotel> listiLausHotel = HotelDB.findAvailableHotels(location, checkInDagur, checkOutDagur, pool, gym, bar, hotelName, Integer.parseInt(fxNumRooms.getText().trim()));
            hotels.addAll(listiLausHotel);

            System.out.print("hotels found: "+listiLausHotel.size());
            fxListView.setItems(hotels);
        }
    }
    @FXML
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
    @FXML
    public void fxOpenBooking(){
        fxErrorLabel.setText("");

        if (!userCon.isSignedIn()) {
            fxErrorLabel.setText("You must sign in before booking.");
            fxErrorLabel.setTextFill(Color.RED);
            return;
        }

        if(fxCheckIn.getValue() != null && fxCheckOut.getValue() != null && !fxNumRooms.getText().isEmpty() && fxListView.getSelectionModel().getSelectedItem() != null) {
            try {
                Hotel selectedHotel = fxListView.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("booking-dialog.fxml"));
                DialogPane bookingDialogPane = fxmlLoader.load();

                BookingController controller = fxmlLoader.getController();
                String hotelName = selectedHotel.getName();
                String checkIn = fxCheckIn.getValue().toString();
                String checkOut = fxCheckOut.getValue().toString();
                String username = userCon.getUser().getUsername();
                int rooms = Integer.parseInt(fxNumRooms.getText());
                int price = selectedHotel.getPrice();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
                LocalDate checkOutDate = LocalDate.parse(checkOut, formatter);
                int nights = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
                int totalPrice = price*nights*rooms;

                controller.setBookingInfo(hotelName, checkIn, checkOut, username, rooms, totalPrice);

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirm info");
                dialog.setDialogPane(bookingDialogPane);
                Optional<ButtonType> result = dialog.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    BookingDB.addBooking(selectedHotel.getId(), userCon.getUser().getUserID(), checkIn, checkOut, rooms, hotelName, price);
                    fxErrorLabel.setTextFill(Color.BLUE);
                    fxErrorLabel.setText("Your booking has been added");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            fxErrorLabel.setText("Before booking you must sign-in, select check-out and check-in dates, select a hotel and select the number of rooms");
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
