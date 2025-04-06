package throunhugbunadar.hotelbokanir.vidmot;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    @FXML
    private Label fxAlert;
    @FXML
    private TextField fxUsername;
    @FXML
    private TextField fxPassword;
    @FXML
    private TextField fxEmail;
    @FXML
    private Label fxEmailLabel;
    @FXML
    private CheckBox fxNewAccount;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fxEmailLabel.setVisible(false);
        fxEmail.setVisible(false);

        fxNewAccount.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    fxEmailLabel.setVisible(true);
                    fxEmail.setVisible(true);
                }
                else {
                    fxEmailLabel.setVisible(false);
                    fxEmail.setVisible(false);
                }
            }
        });
    }


    public boolean signingUp() {
        return fxNewAccount.isSelected();
    }

    public void setAlert(String m) {
        fxAlert.setText(m.trim());
    }

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
