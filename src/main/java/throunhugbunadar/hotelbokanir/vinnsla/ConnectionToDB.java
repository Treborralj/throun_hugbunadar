package throunhugbunadar.hotelbokanir.vinnsla;

import java.sql.*;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public class ConnectionToDB {
    private static final String dbURL =
            "jdbc:sqlite:src/main/resources/hotel_bookin.db";

    public static Connection connect() throws SQLException {
        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(dbURL);
        } catch(ClassNotFoundException e){
            System.out.println("SQLite JDBC driver was not found: " + e.getMessage());
        }catch (SQLException e) {
            System.out.println("Connection to database failed: " + e.getMessage());
        }
        return null;
    }
}

