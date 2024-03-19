import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class ClientChat extends JFrame {
    private Logger log = Logger.getLogger(String.valueOf(ClientChat.class));
    private JTextArea chatMessages;
    private Socket socket;
    private int port;
    private String host;
    private String user;

    public ClientChat() {
        super("Client Chat");
        chatMessages = new JTextArea();
        chatMessages.setEnabled(false);
        chatMessages.setLineWrap(true);
        chatMessages.setWrapStyleWord(true);
        JScrollPane scrollChatMessages = new JScrollPane(chatMessages);
        JTextField tfMessage = new JTextField("");
        JButton btSend = new JButton("Send");
        Container c = this.getContentPane();
        c.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        c.add(scrollChatMessages, gbc);
        gbc.gridwidth = 1;
        gbc.weighty = 0;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 20, 20);

        gbc.gridx = 0;
        gbc.gridy = 1;
        c.add(tfMessage, gbc);
        gbc.weightx = 0;

        gbc.gridx = 1;
        gbc.gridy = 1;
        c.add(btSend, gbc);

        this.setBounds(400, 100, 400, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ConfigWindow cw = new ConfigWindow(this);
        host = cw.getHost();
        port = cw.getPort();
        user = cw.getUser();

        log.info("You are trying to connect to " + host + " at port " + port + " using username: " + user + ".");

        try {
            socket = new Socket(host, port);
            btSend.addActionListener(new ServerConnection(socket, tfMessage, user));
            recieveServerMessages();
        } catch (UnknownHostException ex) {
            log.info("could not connect to the server (" + ex.getMessage() + ").");
        } catch (IOException ex) {
            log.info("could not connect with the server (" + ex.getMessage() + ").");
        }
    }

    public void recieveServerMessages() {
        DataInputStream dataEntry = null;
        String message;
        try {
            dataEntry = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            log.info("Error while creating input stream : " + ex.getMessage());
        } catch (NullPointerException ex) {
            log.info("The socket did not created succesfully.");
        }

        boolean connected = true;
        while (connected) {
            try {
                message = dataEntry.readUTF();
                chatMessages.append(message + System.lineSeparator());
            } catch (IOException ex) {
                log.info("Error while reading entry stream: " + ex.getMessage());
                connected = false;
            } catch (NullPointerException ex) {
                log.info("The socket did not create correctly. ");
                connected = false;
            }
        }
    }

    public static void main(String[] args) {
        ClientChat clientChat = new ClientChat();
        clientChat.recieveServerMessages();
    }
}
