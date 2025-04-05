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
public class BokunarVinnsla {
    public static void baetaVidBokun(int hotelId, String nafnKunna, String checkIn, String checkOut) {
        String sql =
                "INSERT INTO bookings (hotel_id, customer_name, check_in, check_out) VALUES (?, ?, ?, ?)";

        try  {
            Connection conn = GagnasafnsTenging.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, hotelId);
            pstmt.setString(2, nafnKunna);
            pstmt.setString(3, checkIn);
            pstmt.setString(4, checkOut);

            int radir = pstmt.executeUpdate();
            if (radir > 0) {
                System.out.println("Bókun tókst fyrir " + nafnKunna);
            } else {
                System.out.println("Ekki tókst að senda inn bókun");
            }
        } catch (SQLException e) {
            System.out.println("Galli við að setja bókun inn í gagnasafn" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        baetaVidBokun(1, "María Stefánsdóttir", "10-04-2025", "15-04-2025");
    }
}
