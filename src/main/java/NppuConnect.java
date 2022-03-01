import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NppuConnect {
    private static String messageConnect_Connecting = "Подключение к НППУ...";
    private static String messageConnect_Connected = "Подключен.";
    private static String messageConnect_NotConnected = "НППУ не доступна!";
    private static String messageCurrentStatus = messageConnect_Connecting;
    private String addressIP = "192.168.2.100";
    private int port_SetCommand = 1777;
    private int port_GetCommand = 1804;
    private Socket server_SetConnect;
    private SocketPostman server_GetConnect;
    private int modemChanel = 1;
    private DataOutputStream dataOutputStream;
    private String version = "v1.0";
    private static NppuConnect nppuConnect;
    private NppuConnect(){
        try {
            messageCurrentStatus = messageConnect_Connecting;
            Socket server_SetConnect = new Socket(addressIP, port_SetCommand);
            SocketPostman server_GetConnect = new SocketPostman(addressIP, port_GetCommand, new short[15], new short[3], SocketPostmanTaskTypeList.READ_SYMBOL_ARRAY);
            messageCurrentStatus = messageConnect_Connected;
            dataOutputStream = new DataOutputStream(server_SetConnect.getOutputStream());
            modemChanelWatcher();
        }catch (Exception e){
            messageCurrentStatus = messageConnect_NotConnected;
        }
    }

    public static void connect(){
        Thread connectingThread = new Thread(()->{
            if(nppuConnect == null){
                nppuConnect = new NppuConnect();
            }
        });
        connectingThread.start();
    }

    public static NppuConnect getNppuConnect(){
        return nppuConnect;
    }

    public String getVersion(){
        return version;
    }

    public static String getMessageCurrentStatus(){
        return messageCurrentStatus;
    }
    public final int getModemChanel(){
        return modemChanel;
    }

    private void modemChanelWatcher(){
        Thread modemChanelWatcherThread = new Thread(()->{
            while (true){
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                modemChanel = (server_GetConnect.getInArrayLink()[10] + 1);
            }
        });
        modemChanelWatcherThread.start();
    }

    public final void setModem(int numModem) throws IOException {
        switch (numModem){
            case 1: dataOutputStream.writeBytes("{\"command\":\"WD_Directive\",\"value\":13}" + "\n");
            break;
            case 2: dataOutputStream.writeBytes("{\"command\":\"WD_Directive\",\"value\":14}" + "\n");
            break;
        }
    }
}
