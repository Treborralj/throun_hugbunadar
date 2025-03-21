module throunhugbunadar.hotelbokanir {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens throunhugbunadar.hotelbokanir to javafx.fxml;
    exports throunhugbunadar.hotelbokanir;
}