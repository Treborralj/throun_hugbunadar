package throunhugbunadar.hotelbokanir.vidmot;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import throunhugbunadar.hotelbokanir.UserController;
import throunhugbunadar.hotelbokanir.vinnsla.Booking;
import throunhugbunadar.hotelbokanir.vinnsla.BookingDB;
import throunhugbunadar.hotelbokanir.vinnsla.Hotel;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProfileInteractive implements Initializable {
    @FXML
    private Label fxUsername;
    @FXML
    private Label fxEmail;
    @FXML
    private TextField fxNewEmail;
    @FXML
    private TextField fxNewPassword;
    @FXML
    private Button fxEmailButton;
    @FXML
    private Button fxPasswordButton;
    @FXML
    private Label fxAlert;
    @FXML
    private HBox fxHBox;
    @FXML
    private VBox fxVBox;
    @FXML
    private ListView<Booking> fxBookingsView;
    private UserController userCon = null;

    public void setEmail(String email) {
        fxEmail.setText(email);
    }

    public void setAlert(String s) {
        fxAlert.setText(s);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fxEmailButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> fxNewEmail.getText().trim().isEmpty(),
                fxNewEmail.textProperty()));

        fxPasswordButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> fxNewPassword.getText().trim().isEmpty(),
                fxNewPassword.textProperty()));

        fxVBox.setMinWidth(250);
        //fxVBox.setMinHeight(380);
        fxHBox.setMaxHeight(380);
    }

    public void redAlert(boolean b) {
        if (b) {
            fxAlert.setTextFill(Color.RED);
        } else {
            fxAlert.setTextFill(Color.BLUE);
        }
    }

    public void setUserCon(UserController c) {
        userCon = c;
        fxUsername.setText(c.getUser().getUsername());
        fxEmail.setText(c.getUser().getEmail());
    }

    public void fxChangeEmail(MouseEvent event) {
        fxAlert.setText("");
        if (userCon == null) {
            System.out.println("The ProfileInteractive must be associated with a UserController");
            return;
        }
        String newEmail = fxNewEmail.getText().trim();
        userCon.changeEmail(this, newEmail);
    }

    public void fxChangePassword(MouseEvent event) {
        fxAlert.setText("");
        if (userCon == null) {
            System.out.println("The ProfileInteractive must be associated with a UserController");
            return;
        }
        String newPassword = fxNewPassword.getText().trim();
        userCon.changePassword(this, newPassword);
    }

    public void fxDeleteAccount(MouseEvent event) {
        fxAlert.setText("");
        if (userCon == null) {
            System.out.println("The ProfileInteractive must be associated with a UserController");
        }
        boolean confirmed = false;
        try {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Are you sure you want to delete your account?");
            Optional<ButtonType> result = a.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                confirmed = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            redAlert(true);
            setAlert("Could not confirm request");
        }
        if (confirmed) {
            try {
                userCon.deleteAccount(this);
            } catch (Exception e) {
                e.printStackTrace();
                redAlert(true);
                setAlert("Could not delete account");
            }
        }
    }

    public void fxMyBookings() throws Exception {
        try {
            ObservableList<Booking> bookings = FXCollections.observableArrayList();
         int userId = userCon.getUser().getUserID();
         List<Booking> listMyBookings = BookingDB.getBookings(userId);
         bookings.addAll(listMyBookings);
         fxBookingsView.setItems(bookings);
         Stage stage = (Stage) fxBookingsView.getScene().getWindow();
         stage.setWidth(800);
         //stage.setHeight(380);
         fxHBox.setMinWidth(800);

         fxBookingsView.setMinWidth(800-fxVBox.getWidth() - 20);
         fxBookingsView.setMaxWidth(800-fxVBox.getWidth() - 20);
         fxBookingsView.setMaxHeight(fxVBox.getHeight()-40);
         }
         catch (Exception e) {
            e.printStackTrace();
         }
    }
}
