import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
public class ServerChat {
    public static void main(String[] args) {
        Logger log = Logger.getLogger(String.valueOf(ServerChat.class));
        int port = 5050;
        int max_conn = 10;
        ServerSocket server = null;
        Socket socket = null;
        ChatMessages messages = new ChatMessages();
        try {
            server = new ServerSocket(port, max_conn);
            while (true){
                log.info("server awaiting for connections");
                socket = server.accept();
                log.info("Client with IP: "+socket.getInetAddress().getHostName()+" connected.");

                ClientConnection cc = new ClientConnection(socket,messages);
                cc.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                socket.close();
                server.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
