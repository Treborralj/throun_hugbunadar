package throunhugbunadar.hotelbokanir;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import throunhugbunadar.hotelbokanir.vidmot.MyBookingsController;
import throunhugbunadar.hotelbokanir.vidmot.ProfileInteractive;
import throunhugbunadar.hotelbokanir.vidmot.SignInInteractive;
import throunhugbunadar.hotelbokanir.vinnsla.Booking;
import throunhugbunadar.hotelbokanir.vinnsla.BookingDB;
import throunhugbunadar.hotelbokanir.vinnsla.User;
import throunhugbunadar.hotelbokanir.vinnsla.UserDB;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import throunhugbunadar.hotelbokanir.HotelController;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public class UserController {
    private User user;
    private Stage profileStage;
    private HotelController hotelCon;

    public User getUser() {
        return user;
    }

    public boolean isSignedIn() {
        return user != null;
    }

    public void signOut() {
        user = null;
        hotelCon.signout();
    }

    public UserController(HotelController h) {
        hotelCon = h;
    }

    ;

    public boolean signIn() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sign-in-dialog.fxml"));
            DialogPane signUpDialogPane = fxmlLoader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Sign in");
            dialog.setDialogPane(signUpDialogPane);
            SignInInteractive c = fxmlLoader.getController();

            Button button = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
            button.addEventFilter(ActionEvent.ACTION, event -> {
                if (c.getUserName().isEmpty()) {
                    if (c.signingUp()) {
                        c.setAlert("Please choose a username");
                    } else {
                        c.setAlert("Please enter your username");
                    }
                    event.consume();
                } else if (c.getPassword().isEmpty()) {
                    if (c.signingUp()) {
                        c.setAlert("Please choose a password");
                    } else {
                        c.setAlert("Please enter your password");
                    }
                    event.consume();
                } else if (c.signingUp()) {
                    if (c.getEmail().isEmpty()) {
                        c.setAlert("Please enter your email-address");
                        event.consume();
                    } else if (UserDB.nameTaken(c.getUserName())) {
                        c.setAlert("This username already exists, please choose a different one");
                        event.consume();
                    }
                } else if (!c.signingUp()) {
                    if (!UserDB.nameTaken(c.getUserName())) {
                        c.setAlert("This username is not associated with any account. If you wish to create a new account, please indicate it by checking the box.");
                        event.consume();
                    } else {
                        boolean verified = false;
                        try {
                            verified = UserDB.verifyPassword(c.getUserName(), c.getPassword());
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
                    boolean userAdded = UserDB.addUser(username, email, password);
                    if (!userAdded) {
                        return false;
                    }
                }
                user = UserDB.findUser(username, password);
            }
        } catch (Exception e) {
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
            ProfileInteractive p = fxmlLoader.getController();
            p.setUserCon(this);
            profileStage.setWidth(280);
            profileStage.setHeight(380);
            profileStage.setTitle("User Profile");
            profileStage.setScene(scene);
            profileStage.setOnCloseRequest(event -> {
                hotelCon.profileButtonDisabled(false);
            });
            profileStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void changeEmail(ProfileInteractive p, String newEmail) {
        assert (!newEmail.isEmpty());
        User userTemp = UserDB.changeEmail(user.getUsername(), user.getPassword(), newEmail.trim());
        if (userTemp == null) {
            p.redAlert(true);
            p.setAlert("Could not change email");
        } else {
            p.setEmail(newEmail);
            p.redAlert(false);
            p.setAlert("Your e-mail address has been changed");
            user = userTemp;
        }
    }

    public void changePassword(ProfileInteractive p, String newPassword) {
        assert (!newPassword.isEmpty());
        User userTemp = UserDB.changePassword(user.getUsername(), user.getPassword(), newPassword.trim());
        if (userTemp == null) {
            p.redAlert(true);
            p.setAlert("Could not change password");
        } else {
            p.redAlert(false);
            p.setAlert("Your password has been changed");
            user = userTemp;
        }
    }

    public void deleteAccount(ProfileInteractive p) {
        boolean success = UserDB.deleteAccount(user);
        if (success) {
            profileStage.close();
            signOut();
        } else {
            p.redAlert(true);
            p.setAlert("Could not delete account");
        }
    }

}
