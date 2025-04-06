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
    public static boolean addUser(String username, String email, String password) {
        String sql = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
        int radir = 0;
        try (Connection conn = ConnectionToDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            radir = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting user into table: " + e.getMessage());
        }
        return radir > 0;
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

    public static User findUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = ConnectionToDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

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

    public static boolean nameTaken(String name) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        ResultSet rs;
        int radir = 0;
        try (Connection conn = ConnectionToDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                radir = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error inserting user into table: " + e.getMessage());
        }
        return radir > 0;
    }

    public static boolean changeEmail(String username, String newEmail) {
        String sql = "UPDATE users SET email = ? WHERE username = ?;";

        try (Connection conn = ConnectionToDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newEmail);
            pstmt.setString(2, username);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error changing email: " + e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean changePassword(String username, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ?;";

        try (Connection conn = ConnectionToDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error changing password: " + e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean verifyPassword(String username,String password) throws Exception {
        String sql = "SELECT password FROM users WHERE username = ?;";
        ResultSet rs;
        String pw = "";
        try (Connection conn = ConnectionToDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                pw = rs.getString("password");
            }
        } catch (SQLException e) {
            throw new Exception("Error verifying password: " + e.getMessage());
        }
        return password.equals(pw);
    }

    public static boolean deleteAccount(User user) {
        String sql = "DELETE FROM users WHERE id = ?;";
        int id = user.getUserID();
        String username = user.getUsername();
        String password = user.getPassword();

        try (Connection conn = ConnectionToDB.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error deleting account: " + e.getMessage());
            return false;
        }
        if (findUser(username, password) != null) {
            System.out.println("Could not delete account from database");
            return false;
        }
        return true;
    }

}