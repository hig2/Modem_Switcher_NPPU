import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class NppuConnect {
    private static final String messageConnect_Connecting = "Подключение к НППУ...";
    private static final String messageConnect_Connected = "Подключен.";
    private static final String messageExchange_NotExchange = "Устанавливаем обмен.";
    private static final String messageConnect_NotConnected = "НППУ не доступна!";
    private static String messageCurrentStatus = messageConnect_Connecting;
    private final static String addressIP = "192.168.2.100";
    private final static int port_SetCommand = 1777;
    private final static int port_GetCommand = 1804;
    private static Socket server_SetConnect;
    private static SocketPostman server_GetConnect;
    private int modemChanel = 1;
    private DataOutputStream dataOutputStream;
    private final String version = "v1.0";
    private static NppuConnect nppuConnect;


    private NppuConnect() throws IOException {
        dataOutputStream = new DataOutputStream(server_SetConnect.getOutputStream());
        connectStatusWatcher();
        modemChanelWatcher();
        autoReconnect();
    }

    public static void connect() {
        Thread connectingThread = new Thread(() -> {
            do {
                try {
                    messageCurrentStatus = messageConnect_Connecting;
                    server_SetConnect = new Socket(addressIP, port_SetCommand);
                    server_GetConnect = new SocketPostman(addressIP, port_GetCommand, new short[15], new short[3], SocketPostmanTaskTypeList.READ_SYMBOL_ARRAY);

                } catch (Exception e) {
                    messageCurrentStatus = messageConnect_NotConnected;
                }
            } while (server_GetConnect == null || !server_GetConnect.isConnected());
            try {
                nppuConnect = new NppuConnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        connectingThread.start();
    }

    public final boolean isConnected() {
        return server_GetConnect.isConnected();
    }

    private void autoReconnect() {
        int delay = 100;

        Thread autoReconnectThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(delay);
                    if (!server_GetConnect.isConnected()) {
                        NppuConnect.connect();
                        break;
                    }
                }catch(NullPointerException e){
                    break;
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        autoReconnectThread.start();
    }


    private void connectStatusWatcher() {
        Thread connectStatusWatcherThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20);
                    if (server_GetConnect.isConnected()) {
                        if (server_GetConnect.isDataExchange()) {
                            messageCurrentStatus = messageConnect_Connected;
                        } else {
                            messageCurrentStatus = messageExchange_NotExchange;
                        }
                    } else {
                        messageCurrentStatus = messageConnect_NotConnected;
                    }
                } catch (NullPointerException e) {
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        connectStatusWatcherThread.start();
    }


    public static NppuConnect getNppuConnect() {
        return nppuConnect;
    }

    public String getVersion() {
        return version;
    }

    public static String getMessageCurrentStatus() {
        return messageCurrentStatus;
    }

    public final int getModemChanel() {
        return modemChanel;
    }

    private void modemChanelWatcher() {
        Thread modemChanelWatcherThread = new Thread(() -> {
            while (server_GetConnect.isConnected()) {
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
        switch (numModem) {
            case 1:
                dataOutputStream.writeBytes("{\"command\":\"WD_Directive\",\"value\":13}" + "\n");
                break;
            case 2:
                dataOutputStream.writeBytes("{\"command\":\"WD_Directive\",\"value\":14}" + "\n");
                break;
        }
    }
}
