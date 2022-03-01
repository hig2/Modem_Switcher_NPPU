import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NppuConnect {
    private static final String messageConnect_Connecting = "Подключение к НППУ...";
    private static final String messageConnect_Connected = "Подключен.";
    private static final String messageExchange_NotExchange = "Нет обмена.";
    private static final String messageConnect_NotConnected = "НППУ не доступна!";
    private static String messageCurrentStatus = messageConnect_Connecting;
    private final String addressIP = "192.168.2.100";
    private final int port_SetCommand = 1777;
    private final int port_GetCommand = 1804;
    private Socket server_SetConnect;
    private SocketPostman server_GetConnect;
    private int modemChanel = 1;
    private DataOutputStream dataOutputStream;
    private final String version = "v1.0";
    private static NppuConnect nppuConnect;


    private NppuConnect(){
        try {
            Socket server_SetConnect = new Socket(addressIP, port_SetCommand);
            server_GetConnect = new SocketPostman(addressIP, port_GetCommand, new short[15], new short[3], SocketPostmanTaskTypeList.READ_SYMBOL_ARRAY);
            connectStatusWatcher();
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

    public final boolean isConnected(){
        return server_GetConnect.isConnected();
    }

    private void connectStatusWatcher(){
        Thread connectStatusWatcherThread = new Thread(()->{
            while(true){
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }

                if(server_GetConnect.isConnected()){
                    if(server_GetConnect.isDataExchange()){
                        messageCurrentStatus = messageConnect_Connected;
                    }else{
                        messageCurrentStatus = messageExchange_NotExchange;
                    }
                }else{
                    messageCurrentStatus = messageConnect_NotConnected;
                }

            }
        });
        connectStatusWatcherThread.start();
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
