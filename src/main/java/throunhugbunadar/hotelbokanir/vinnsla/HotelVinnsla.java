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
public class HotelVinnsla {
    public static List<Hotel> finnaLausHotel(String checkIn, String checkOut){
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
            WHERE (h.num_rooms - COALESCE(b.booked_rooms, 0)) > 0;
        """;

        try{
            Connection conn = GagnasafnsTenging.connect();
            PreparedStatement pstmt = conn.prepareStatement(sqlSkipun);

            pstmt.setString(1, checkOut);
            pstmt.setString(2, checkIn);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                lausHotel.add(new Hotel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getInt("num_rooms")
                        ));
            }
        } catch (SQLException e) {
            System.out.println("Ekki tókst að sækja laus hótel " + e.getMessage());
        }
        return lausHotel;
    }
}
