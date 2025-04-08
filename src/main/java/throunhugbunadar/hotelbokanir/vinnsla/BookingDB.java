package throunhugbunadar.hotelbokanir.vinnsla;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing :
 *
 *****************************************************************************/
public class BookingDB {
    public static void addBooking(int hotelId, int userID, String checkIn, String checkOut, int numberOfRooms, String hotelName, int price) {
        String sql = "INSERT INTO bookings (hotel_id, user_id, check_in, check_out, rooms_booked, hotel_name, price) VALUES (?, ?, ?, ?, ?, ?, ?)";

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
            pstmt.setString(6, hotelName);
            pstmt.setInt(7, price);

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

    public static List<Booking> getBookings(int userID) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";

        try (Connection conn = ConnectionToDB.connect();
             Statement stmt = conn.createStatement();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            stmt.execute("PRAGMA foreign_keys = ON");

            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                int hotelId = rs.getInt("hotel_id");
                String checkIn = rs.getString("check_in");
                String checkOut = rs.getString("check_out");
                int roomsBooked = rs.getInt("rooms_booked");
                String hotelName = rs.getString("hotel_name");
                int price = rs.getInt("price");

                Booking booking = new Booking(bookingId, hotelId, userID, checkIn, checkOut, roomsBooked, hotelName, price);
                bookings.add(booking);
            }

        } catch (SQLException e) {
            System.out.println("Villa við að sækja bókanir: " + e.getMessage());
        }
        return bookings;
    }

}
