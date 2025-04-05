package throunhugbunadar.hotelbokanir.vidmot;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignupController {
    @FXML
    private Label fxAlert;
    @FXML
    private TextField fxUsername;
    @FXML
    private TextField fxPassword;
    @FXML
    private TextField fxEmail;

    public String getUserName() {
        return fxUsername.getText().trim();
    }
    public String getPassword() {
        return fxPassword.getText().trim();
    }
    public String getEmail(){
        return fxEmail.getText().trim();
    }
}
