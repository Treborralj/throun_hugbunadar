package throunhugbunadar.hotelbokanir;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HotelbookingApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HotelbookingApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 600);
        scene.getStylesheets().add(getClass().getResource("/css/hello-view.css").toExternalForm());
        stage.setMinWidth(563);
        stage.setMinHeight(800);
        stage.setTitle("Hótelbókun!!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
