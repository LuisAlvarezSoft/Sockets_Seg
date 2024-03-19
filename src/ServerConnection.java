import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;
public class ServerConnection implements ActionListener {
    private Logger log = Logger.getLogger(String.valueOf(ServerConnection.class));
    private Socket socket;
    private JTextField tfMessage;
    private String user;
    private DataOutputStream outputData;

    public ServerConnection(Socket socket, JTextField tfMessage, String user) {
        this.socket = socket;
        this.tfMessage = tfMessage;
        this.user = user;

    try{
        this.outputData = new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {
        log.info("Error while creating the output stream :" + e.getMessage());
    } catch (NullPointerException e){
        log.info("The socket did not generate correctly.");
    }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            try {
                outputData.writeUTF(user + ": " + tfMessage.getText());
                tfMessage.setText("");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

    }}

