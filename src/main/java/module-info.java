module throunhugbunadar.hotelbokanir {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens throunhugbunadar.hotelbokanir to javafx.fxml;
    opens throunhugbunadar.hotelbokanir.vidmot to javafx.fxml;
    opens throunhugbunadar.hotelbokanir.vinnsla to javafx.fxml;
    exports throunhugbunadar.hotelbokanir;
    exports throunhugbunadar.hotelbokanir.vidmot;
    exports throunhugbunadar.hotelbokanir.vinnsla;

}