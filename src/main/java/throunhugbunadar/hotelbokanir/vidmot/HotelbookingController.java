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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import throunhugbunadar.hotelbokanir.vidmot.BookingController;
import throunhugbunadar.hotelbokanir.vidmot.MyBookingsController;
import throunhugbunadar.hotelbokanir.vidmot.SignInController;
import throunhugbunadar.hotelbokanir.vinnsla.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.net.URL;
import java.util.ResourceBundle;

public class HotelbookingController implements Initializable {
    @FXML
    public TextField fxNumberOfRooms;
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
    @FXML
    private Button fxBookingsButton;
    //@FXML
    //private TextField fxNameOfhotel;
    @FXML
    private ComboBox<String> fxHotelName;
    @FXML
    private ComboBox<String> fxLocation;

    @FXML
    private ListView<Hotel> fxListView;
    @FXML
    private ListView<Booking> fxBookingList;
    @FXML
    private DatePicker fxCheckIn;
    @FXML
    private DatePicker fxCheckOut;
    private User user;
    private ObservableList<Hotel> hotels = FXCollections.observableArrayList();
    private ObservableList<Booking> bookings = FXCollections.observableArrayList();
    private final String[] places = {"No preference","Reykjavik", "Selfoss", "Akureyri", "Egilsstadir", "Vik","Hvammstangi","Isafjordur"};
    private final String[] htlNames = {"No preference","Hotel Reykjavik", "Hotel Selfoss", "Hotel Akureyri", "Hotel Egilsstadir", "Hotel Vik","Hotel Hvammstangi","Hotel Hilton","Hotel Isafjordur","Hotel Fron","Hotel Austur","Hotel Hraun","Hotel Nord"};

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fxLocation.setItems(FXCollections.observableArrayList(places));
        fxLocation.setValue("No preference");

        fxHotelName.setItems(FXCollections.observableArrayList(htlNames));
        fxHotelName.setValue("No preference");

        fxErrorLabel.setText("");
    }

    @FXML
    public void onSearch(ActionEvent event) {
        int numRooms;
        try {
            numRooms = Integer.parseInt(fxNumberOfRooms.getText());
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

            HotelDB vinnsla = new HotelDB();
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
                String username = controller.getUserName().trim();
                String password = controller.getPassword().trim();
                String email = controller.getEmail().trim();

                User foundUser = UserDB.findUser(username, email, password);

                if (foundUser == null) {
                    UserDB.addUser(username, email, password);
                    foundUser = UserDB.findUser(username, email, password);
                }
                if (foundUser != null) {
                    user = foundUser;
                    fxNameLabel.setText(user.getUsername());
                    fxErrorLabel.setText("");
                    fxBookingsButton.setVisible(true);
                } else {
                    fxErrorLabel.setText("Sign-in failed. Please check your info.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (user != null) {
            fxNameLabel.setText(user.getUsername());
        } else {
            fxErrorLabel.setText("Could not sign in.");
        }
    }

    public void fxOpenBooking(){
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
                int rooms = Integer.parseInt(fxNumberOfRooms.getText());
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
                    BookingDB.addBooking(selectedHotel.getId(), user.getUserID(), checkIn, checkOut, rooms, hotelName, price);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            fxErrorLabel.setText("Before booking you must sign-in, select check-out and check-in dates, select a hotel and select the number of rooms needed");
        }
    }

    public void fxMyBookings() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mybookings.fxml"));
            Pane myBookingPane = fxmlLoader.load();

            MyBookingsController controller = fxmlLoader.getController();
            BookingDB vinnsla = new BookingDB();
            int userId = user.getUserID();
            List<Booking> listMyBookings = vinnsla.getBookings(userId);
            bookings.setAll(listMyBookings);
            controller.setBookingsList(bookings);

            Scene scene = new Scene(myBookingPane);
            Stage stage = new Stage();
            stage.setTitle("Current Bookings");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
