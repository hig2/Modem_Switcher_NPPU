import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.text.Text;

public class MyController implements Initializable {
    private void showStatusMassage(){
        Thread statusMassageWatcherThread = new Thread(()->{
            while (true){
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                textStatus.setText(NppuConnect.getMessageCurrentStatus());
            }
        });
        statusMassageWatcherThread.start();
    }

    @FXML
    private Button m1Button;

    @FXML
    private Button m2Button;

    @FXML
    private Text textStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showStatusMassage();
    }

    public void switchM1(ActionEvent event) {

        System.out.println("btn_1!");
        m1Button.setStyle("-fx-background-color: #ff0000; ");

        // Model Data


        // Show in VIEW


    }

    public void switchM2(ActionEvent event) {
        System.out.println("btn_2!");
        // Model Data


        // Show in VIEW


    }

}

