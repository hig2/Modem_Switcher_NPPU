import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TestTCP_nppu_connect {
    public static void main(String[] args) throws IOException {
        Socket client = null;
        try {
            System.out.println("Подключение");
            System.out.println("Связь с сервером установлена !");
            client = new Socket("192.168.2.100", 1777);
        }catch (Exception e){
            System.out.println("Сервер недоступен!");
        }
        DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        while(true){
            switch (scanner.nextLine()){
                case "1":
                    try {
                        System.out.println("Модем1");
                        dataOutputStream.writeBytes("{\"command\":\"WD_Directive\",\"value\":13}" + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    try {
                        System.out.println("Модем2");
                        dataOutputStream.writeBytes("{\"command\":\"WD_Directive\",\"value\":14}" + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
