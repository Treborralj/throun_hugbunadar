package throunhugbunadar.hotelbokanir.vidmot;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public class SignInController {
    @FXML
    public TextField fxEmail;
    @FXML
    private TextField fxName;
    @FXML
    private TextField fxPassword;

    public String getUserName() {
        return fxName.getText().trim();
    }

    public String getPassword() {
        return fxPassword.getText();
    }
    public String getEmail(){
        return fxEmail.getText().trim();
    }

}
