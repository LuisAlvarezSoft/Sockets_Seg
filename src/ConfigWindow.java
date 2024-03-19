import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
public class ConfigWindow extends JDialog{
    private Logger log = Logger.getLogger(String.valueOf(ConfigWindow.class));
    private JTextField tfUser;
    private JTextField tfHost;
    private JTextField tfPort;

    public ConfigWindow(JFrame padre) {
        super(padre, "Configuracion inicial", true);

        JLabel lbUser = new JLabel("User:");
        JLabel lbHost = new JLabel("Host:");
        JLabel lbPort = new JLabel("Port:");

        tfUser = new JTextField();
        tfHost = new JTextField("localhost");
        tfPort = new JTextField("5050");

        JButton btAccept = new JButton("Accept");
        btAccept.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        Container c = this.getContentPane();
        c.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(20, 20, 0, 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        c.add(lbUser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        c.add(lbHost, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        c.add(lbPort, gbc);

        gbc.ipadx = 100;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 1;
        gbc.gridy = 0;
        c.add(tfUser, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        c.add(tfHost, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        c.add(tfPort, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 20, 20);
        c.add(btAccept, gbc);

        this.pack(); // Le da a la ventana el minimo tamaño posible
        this.setLocation(450, 200); // Posicion de la ventana
        this.setResizable(false); // Evita que se pueda estirar la ventana
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Deshabilita el boton de cierre de la ventana
        this.setVisible(true);
    }


    public String getUser(){
        return this.tfUser.getText();
    }

    public String getHost(){
        return this.tfHost.getText();
    }

    public int getPort(){
        return Integer.parseInt(this.tfPort.getText());
    }

}

