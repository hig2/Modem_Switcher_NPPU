

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Objects;


public class ModemMonitor extends Application{
    public static void main(String[] args) {
        launch(args);
    }




    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("TestGui.fxml")));
            primaryStage.setTitle("ModemMonitor v1.0");
            primaryStage.setScene(new Scene(root, 400, 170, Color.web("#3C3F41")));
            primaryStage.getIcons().add(new Image("picture/favicon.png"));
            primaryStage.setResizable(false);
            primaryStage.show();
            NppuConnect.connect();
        } catch(Exception e) {
            e.printStackTrace();
        }


    }
}