package throunhugbunadar.hotelbokanir.vinnsla;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/

public class HotelDB {
    public List<Hotel> findAvailableHotels(String location, String checkIn, String checkOut, boolean pool, boolean gym, boolean bar, String nameOfHotel){

        List<Hotel> lausHotel = new ArrayList<>();

        String sqlSkipun = """
            SELECT h.* 
            FROM hotels h
            LEFT JOIN (
                SELECT hotel_id, COUNT(*) AS booked_rooms
                FROM bookings
                WHERE check_in <= ? AND check_out > ?
                GROUP BY hotel_id
            ) b ON h.id = b.hotel_id
            WHERE (h.num_rooms - COALESCE(b.booked_rooms, 0)) > 0
            AND (? = 0 OR h.pool = 1)
            AND (? = 0 OR h.gym = 1)
            AND (? = 0 OR h.bar = 1)
            AND (? = 'No preference' OR h.name LIKE ?)
            AND (? = 'No preference' OR h.location LIKE ?);
        """;

        try{
            Connection conn = ConnectionToDB.connect();
            PreparedStatement pstmt = conn.prepareStatement(sqlSkipun);

            pstmt.setString(1, checkOut);
            pstmt.setString(2, checkIn);
            pstmt.setInt(3, pool ? 1 : 0);
            pstmt.setInt(4, gym ? 1 : 0);
            pstmt.setInt(5, bar ? 1 : 0);
            pstmt.setString(6, nameOfHotel);
            pstmt.setString(7, "%" + nameOfHotel + "%");
            pstmt.setString(8, location);
            pstmt.setString(9, "%" + location + "%");
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                lausHotel.add(new Hotel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getInt("num_rooms"),
                        rs.getBoolean("pool"),
                        rs.getBoolean("gym"),
                        rs.getBoolean("bar")
                        ));
            }
        } catch (SQLException e) {
            System.out.println("Ekki tókst að sækja laus hótel " + e.getMessage());
        }
        return lausHotel;
    }

}
