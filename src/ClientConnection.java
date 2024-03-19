import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
public class ClientConnection extends Thread implements Observer {
    private Logger log = Logger.getLogger(String.valueOf(ClientConnection.class));
    private Socket socket;
    private ChatMessages message;
    private DataInputStream entradaDatos;
    private DataOutputStream salidaDatos;

    public ClientConnection (Socket socket, ChatMessages mensajes){
        this.socket = socket;
        this.message = message;

        try {
            entradaDatos = new DataInputStream(socket.getInputStream());
            salidaDatos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            log.info("Error while closing and opening stream : " + ex.getMessage());
        }
    }

    @Override
    public void run(){
        String mensajeRecibido;
        boolean conn = true;
        // Se apunta a la lista de observadores de mensajes
        message.addObserver(this);

        while (conn) {
            try {
                // Lee un mensaje enviado por el cliente
                mensajeRecibido = entradaDatos.readUTF();
                // Pone el mensaje recibido en mensajes para que se notifique
                // a sus observadores que hay un nuevo mensaje.
                message.setMessage(mensajeRecibido);
            } catch (IOException ex) {
                log.info("Client with IP " + socket.getInetAddress().getHostName() + " disconnected.");
                conn = false;
                try {
                    entradaDatos.close();
                    salidaDatos.close();
                } catch (IOException ex2) {
                    log.info("Error while closing and opening stream : " + ex2.getMessage());
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            salidaDatos.writeUTF(arg.toString());
        } catch (IOException ex) {
            log.info("Error while sending the message (" + ex.getMessage() + ").");
        }
    }
}
