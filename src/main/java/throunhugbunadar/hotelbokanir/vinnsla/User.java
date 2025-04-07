package throunhugbunadar.hotelbokanir.vinnsla;

import java.util.List;


/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public class User {
    private String username;
    private String password;
    private String email;
    private int userID;


    public User(String username, String password, String email, int userID){
        this.username = username;
        this.password = password;
        this.email = email;
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }
    public int getUserID(){
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

}
