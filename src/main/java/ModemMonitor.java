

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
            primaryStage.setScene(new Scene(root, 400, 261, Color.web("#3C3F41")));
            primaryStage.getIcons().add(new Image("picture/favicon.png"));
            primaryStage.setResizable(false);
            primaryStage.show();
            NppuConnect.connect();

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.exit();
                    System.exit(0);
                }
            });
        } catch(Exception e) {
            e.printStackTrace();
        }


    }
}