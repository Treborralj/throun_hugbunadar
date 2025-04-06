package throunhugbunadar.hotelbokanir;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import throunhugbunadar.hotelbokanir.vidmot.BookingController;
import throunhugbunadar.hotelbokanir.vidmot.ProfileController;
import throunhugbunadar.hotelbokanir.vidmot.SignInController;
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
    private Stage profileStage;
    private throunhugbunadar.hotelbokanir.HotelbookingController hotelCon;

    //public String getUserName() {return fxName.getText().trim();}
    //public String getPassword() {return fxPassword.getText();}
    //public String getEmail(){return fxEmail.getText().trim();}
    public User getUser() {return user;}
    public boolean isSignedIn() {return user != null;}

    public void signOut() {
        user = null;
        hotelCon.signout();
    }

    public UserController(throunhugbunadar.hotelbokanir.HotelbookingController h){
        user = null;
        hotelCon = h;
    };

    public boolean signIn() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sign-in-dialog.fxml"));
            DialogPane signUpDialogPane = fxmlLoader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Sign in");
            dialog.setDialogPane(signUpDialogPane);
            SignInController c = fxmlLoader.getController();

            Button button = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
            button.addEventFilter(ActionEvent.ACTION, event -> {
                if (c.getUserName().isEmpty()) {
                    if (c.signingUp()) {c.setAlert("Please choose a username");}
                    else {c.setAlert("Please enter your username");}
                    event.consume();
                }
                else if (c.getPassword().isEmpty()) {
                    if (c.signingUp()) {c.setAlert("Please choose a password");}
                    else {c.setAlert("Please enter your password");}
                    event.consume();
                }
                else if (c.signingUp()) {
                    if (c.getEmail().isEmpty()) {
                        c.setAlert("Please enter your email-address");
                        event.consume();
                    }
                    else if (UserDB.nameTaken(c.getUserName())) {
                        c.setAlert("This username already exists, please choose a different one");
                        event.consume();
                    }
                }
                else if (!c.signingUp()) {
                    if (!UserDB.nameTaken(c.getUserName())) {
                        c.setAlert("This username is not associated with any account. If you wish to create a new account, please indicate it by checking the box.");
                        event.consume();
                    }
                    else {
                        boolean verified = false;
                        try {
                            verified = UserDB.verifyPassword(c.getUserName(),c.getPassword());
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            c.setAlert("Could not verify password");
                            event.consume();
                        }
                        if (!verified) {
                            c.setAlert("Wrong password");
                            event.consume();
                        }
                    }
                }
            });

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                String username = c.getUserName();
                String password = c.getPassword();
                if (c.signingUp()) {
                    String email = c.getEmail();
                    boolean userAdded = UserDB.addUser(username,email,password);
                    if (!userAdded) {return false;}
                }
                user = UserDB.findUser(username,password);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void openProfile() {
        try {
            profileStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(throunhugbunadar.hotelbokanir.HotelbookingApplication.class.getResource("user-profile.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 600);
            ProfileController p = fxmlLoader.getController();
            p.setUserCon(this);
            profileStage.setWidth(266);
            profileStage.setHeight(360);
            profileStage.setTitle("User Profile");
            profileStage.setScene(scene);
            profileStage.setOnCloseRequest(event -> {
                hotelCon.profileButtonDisabled(false);
            });
            profileStage.show();
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void changeEmail(ProfileController p, String newEmail) {
        assert(!newEmail.isEmpty());
        boolean success = UserDB.changeEmail(user.getUsername(), newEmail.trim());
        if (!success) {
            p.redAlert(true);
            p.setAlert("Could not change email");}
        else {
            p.setEmail(newEmail);
            p.redAlert(false);
            p.setAlert("Your e-mail address has been changed");
        }
    }

    public void changePassword(ProfileController p, String newPassword) {
        assert(!newPassword.isEmpty());
        boolean success = UserDB.changePassword(user.getUsername(), newPassword.trim());
        if (!success) {
            p.redAlert(true);
            p.setAlert("Could not change password");}
        else {
            p.redAlert(false);
            p.setAlert("Your password has been changed");}
    }

    public void deleteAccount(ProfileController p) {
        boolean success = UserDB.deleteAccount(user);
        if (success) {
            profileStage.close();
            signOut();}
        else {
            p.redAlert(true);
            p.setAlert("Could not delete account");
        }
    }
}
