package cz.nitramek.gui;

import cz.nitramek.networking.NetworkTester;
import cz.nitramek.networking.UDPListener;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by Martin on 24.10.2015.
 */
public class TesterGUI {
    private JPanel mainPanel;
    private JPanel serversPanel;
    private JPanel messagePanel;
    private JTextField messageField;
    private JComboBox protocolBox;
    private JLabel protocolLabel;
    private JLabel messageLabel;
    private JButton sendButton;
    private JLabel adressLabel;
    private JTextField addressField;
    private JLabel portLabel;
    private JTextField portField;

    UDPListener udpListener;

    public TesterGUI() {
        udpListener = new UDPListener();
        sendButton.addActionListener(e -> {
            try {
                onSendButton();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void onSendButton() throws IOException {
        InetAddress targetAddress = InetAddress.getByName(addressField.getText());
        int targetPort = Integer.parseInt(portField.getText());
        String message = messageField.getText();
        if (protocolBox.getSelectedItem().equals("UDP")) {
            NetworkTester.sendUDPMessage(
                    message,
                    targetAddress,
                    targetPort
            );
        } else if (protocolBox.getSelectedItem().equals("TCP")) {
            NetworkTester.sendTCPMessage(message, targetAddress, targetPort);
        }

    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("TesterGUI");
        TesterGUI gui = new TesterGUI();
        frame.setContentPane(gui.mainPanel);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gui.udpListener.stop();
            }
        });
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}