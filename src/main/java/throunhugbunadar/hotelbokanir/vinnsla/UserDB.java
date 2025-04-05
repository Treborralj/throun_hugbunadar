package throunhugbunadar.hotelbokanir.vinnsla;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing :
 *
 *****************************************************************************/
public class UserDB {
    public static void addUser(String username, String email, String password) {
        String sql = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionToDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            int radir = pstmt.executeUpdate();
            if (radir > 0) {
                System.out.println("User saved successfully: " + username);
            } else {
                System.out.println("Could not save user");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting user into table: " + e.getMessage());
        }
    }

    public static User findUser(String username, String email, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND email = ? AND password = ?";

        try (Connection conn = ConnectionToDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("id");
                    String uname = rs.getString("username");
                    String mail = rs.getString("email");
                    String pass = rs.getString("password");

                    return new User(uname, mail, pass, userId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding user: " + e.getMessage());
        }

        return null;
    }
}