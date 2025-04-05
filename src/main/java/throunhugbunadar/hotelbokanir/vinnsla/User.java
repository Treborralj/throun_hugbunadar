package throunhugbunadar.hotelbokanir.vinnsla;

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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
