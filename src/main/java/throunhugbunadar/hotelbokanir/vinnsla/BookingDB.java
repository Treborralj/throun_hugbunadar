package throunhugbunadar.hotelbokanir.vinnsla;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public class BookingDB {
    public static void addBooking(int hotelId, int userID, String checkIn, String checkOut, int numberOfRooms) {
        String sql = "INSERT INTO bookings (hotel_id, user_id, check_in, check_out, rooms_booked) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionToDB.connect();
             Statement stmt = conn.createStatement();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            stmt.execute("PRAGMA foreign_keys = ON");

            System.out.println("Inserting booking:");
            System.out.println("Hotel ID: " + hotelId);
            System.out.println("User ID: " + userID);
            System.out.println("Check-in: " + checkIn);
            System.out.println("Check-out: " + checkOut);
            System.out.println("Rooms: " + numberOfRooms);

            pstmt.setInt(1, hotelId);
            pstmt.setInt(2, userID);
            pstmt.setString(3, checkIn);
            pstmt.setString(4, checkOut);
            pstmt.setInt(5, numberOfRooms);

            int radir = pstmt.executeUpdate();
            if (radir > 0) {
                System.out.println("Bókun tókst fyrir " + userID);
            } else {
                System.out.println("Ekki tókst að senda inn bókun");
            }

        } catch (SQLException e) {
            System.out.println("Galli við að setja bókun inn í gagnasafn: " + e.getMessage());
        }
    }
}