package throunhugbunadar.hotelbokanir.vinnsla;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public class BookingDB {
    public static void addBooking(int hotelId, int userID, String checkIn, String checkOut, int numberOfRooms) {
        String sql =
                "INSERT INTO bookings (hotel_id, userID, check_in, check_out, numberOfRooms) VALUES (?, ?, ?, ?, ?)";

        try  {
            Connection conn = ConnectionToDB.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

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
            System.out.println("Galli við að setja bókun inn í gagnasafn" + e.getMessage());
        }
    }
}
