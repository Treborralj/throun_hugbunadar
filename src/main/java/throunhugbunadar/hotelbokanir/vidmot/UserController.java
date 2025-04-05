package throunhugbunadar.hotelbokanir;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import throunhugbunadar.hotelbokanir.vidmot.SignupController;
import throunhugbunadar.hotelbokanir.vinnsla.User;
import throunhugbunadar.hotelbokanir.vinnsla.UserDB;
import java.io.IOException;
import java.util.Optional;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public class UserController {
    private User user;
    private boolean loggedIn;

    //public String getUserName() {return fxName.getText().trim();}
    //public String getPassword() {return fxPassword.getText();}
    //public String getEmail(){return fxEmail.getText().trim();}
    public User getUser() {return user;}
    public boolean isLoggedIn() {return loggedIn;}

    public UserController(){
        user = null;
        loggedIn = false;
    };

    public User signUp() throws IOException {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sign-up-dialog.fxml"));
            DialogPane signUpDialogPane = fxmlLoader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Sign up");
            dialog.setDialogPane(signUpDialogPane);

            /**
            Button button = (Button) getDialogPane().lookupButton(ButtonType.OK);

            button.addEventFilter(ActionEvent.ACTION, event -> {
                final Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.initOwner(window);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.CANCEL) {
                        event.consume();
                    }
                });
            });
            */
            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                SignupController controller = fxmlLoader.getController();
                String username = controller.getUserName();
                String password = controller.getPassword();
                String email = controller.getEmail();

                //UserDB.addUser(name,password,email);
                user = new User(username,password,email);
                loggedIn = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    return user;
    }
}
