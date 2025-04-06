package throunhugbunadar.hotelbokanir.vidmot;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import throunhugbunadar.hotelbokanir.UserController;
import throunhugbunadar.hotelbokanir.vinnsla.BookingDB;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
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

    private UserController userCon = null;

    public boolean hasUserCon() {return userCon != null;}
    public void setUsername(String name) {fxUsername.setText(name);}
    public void setEmail(String email) {fxEmail.setText(email);}
    public void setAlert(String s) {fxAlert.setText(s);}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fxEmailButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> fxNewEmail.getText().trim().isEmpty(),
                fxNewEmail.textProperty()));

        fxPasswordButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> fxNewPassword.getText().trim().isEmpty(),
                fxNewPassword.textProperty()));

    }

    public void redAlert(boolean b) {
        if (b) {fxAlert.setTextFill(Color.RED);}
        else {fxAlert.setTextFill(Color.BLUE);}
    }

    public void setUserCon(UserController c) {
        userCon = c;
        fxUsername.setText(c.getUser().getUsername());
        fxEmail.setText(c.getUser().getEmail());
    }

    public void fxChangeEmail(MouseEvent event) {
        fxAlert.setText("");
        if (userCon == null) {
            System.out.println("The ProfileController must be associated with a UserController");
        }
        String newEmail = fxNewEmail.getText().trim();
        userCon.changeEmail(this, newEmail);

    }

    public void fxChangePassword(MouseEvent event) {
        fxAlert.setText("");
        if (userCon == null) {
            System.out.println("The ProfileController must be associated with a UserController");
        }
        String newPassword = fxNewPassword.getText().trim();
        userCon.changePassword(this, newPassword);
    }

    public void fxDeleteAccount(MouseEvent event) {
        fxAlert.setText("");
        if (userCon == null) {
            System.out.println("The ProfileController must be associated with a UserController");
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
}
