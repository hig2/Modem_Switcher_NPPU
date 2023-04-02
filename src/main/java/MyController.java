import java.io.IOException;
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
                try {
                    if(NppuConnect.getNppuConnect() != null && NppuConnect.getNppuConnect().isConnected()){
                        switch (NppuConnect.getNppuConnect().getModemChanel()){
                            case 1:
                                m1Button.setStyle("-fx-background-color: #50C878");
                                m2Button.setStyle("-fx-background-color: #DDDDDD");
                                break;
                            case 2:
                                m1Button.setStyle("-fx-background-color: #DDDDDD");
                                m2Button.setStyle("-fx-background-color: #50C878");
                                break;
                        }

                        switch (NppuConnect.getNppuConnect().getAntChanel()){
                            case 1:
                                a1Button.setStyle("-fx-background-color: #50C878");
                                a2Button.setStyle("-fx-background-color: #DDDDDD");
                                break;
                            case 2:
                                a1Button.setStyle("-fx-background-color: #DDDDDD");
                                a2Button.setStyle("-fx-background-color: #50C878");
                                break;
                        }

                    }else{
                        m1Button.setStyle("-fx-background-color: #DDDDDD");
                        m2Button.setStyle("-fx-background-color: #DDDDDD");
                        
                        a1Button.setStyle("-fx-background-color: #DDDDDD");
                        a2Button.setStyle("-fx-background-color: #DDDDDD");

                    }
                }catch (Exception e){
                    // e.printStackTrace();
                }

            }
        });
        statusMassageWatcherThread.start();
    }

    @FXML
    private Button m1Button;

    @FXML
    private Button m2Button;

    @FXML
    private Button a1Button;

    @FXML
    private Button a2Button;

    @FXML
    private Text textStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showStatusMassage();
    }

    public void switchM1(ActionEvent event) {
        if(NppuConnect.getNppuConnect() != null){
            try {
                NppuConnect.getNppuConnect().setModem(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void switchM2(ActionEvent event) {
        if(NppuConnect.getNppuConnect() != null){
            try {
                NppuConnect.getNppuConnect().setModem(2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void switchA1(ActionEvent event) {
        if(NppuConnect.getNppuConnect() != null){
            try {
                NppuConnect.getNppuConnect().setAnt(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void switchA2(ActionEvent event) {
        if(NppuConnect.getNppuConnect() != null){
            try {
                NppuConnect.getNppuConnect().setAnt(2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

