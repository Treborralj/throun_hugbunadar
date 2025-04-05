package throunhugbunadar.hotelbokanir.vinnsla;

import java.sql.*;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public class ConnectionToDB {
    private static final String gagnasafnsURL =
            "jdbc:sqlite:src/main/resources/hotel_bookin.db";

    public static Connection connect() throws SQLException {
        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(gagnasafnsURL);
        } catch(ClassNotFoundException e){
            System.out.println("SQLite JDBC driver fannst EKKI: " + e.getMessage());
        }catch (SQLException e) {
            System.out.println("Tenging við gagnagrunn MISTÓKST: " + e.getMessage());
        }
        return null;
    }
}

