package cz.nitramek.gui;

import cz.nitramek.networking.Listener;
import cz.nitramek.networking.NetworkTester;
import cz.nitramek.networking.TCPListener;
import cz.nitramek.networking.UDPListener;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by Martin on 24.10.2015.
 */
public class TesterGUI {
    private JPanel mainPanel;
    private JPanel udpPanel;
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
    private JTextField udpPortField;
    private JLabel udpListenerLabel;
    private JLabel udpPortLabel;
    private JButton updListenButton;
    private JTextArea messagesArea;
    private JScrollPane scrollPane;
    private JPanel messagesPanel;
    private JPanel tcpPanel;
    private JButton tcpButton;
    private JTextField tcpPortField;

    UDPListener udpListener;

    Listener tcpListener = new TCPListener();

    public TesterGUI() {
        udpListener = new UDPListener();
        sendButton.addActionListener(e -> {
            try {
                onSendButton();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        updListenButton.addActionListener(e -> {
            try {
                onUpdListenButton();
            } catch (SocketException e1) {
                e1.printStackTrace();
            }
        });
        udpListener.addMessageListener(message -> {
            String strMessage = message.getMessage();
            appendToMessages(String.format("Them: %s\n", strMessage));
        });
        tcpListener.addMessageListener(message -> {
            String strMessage = message.getMessage();
            appendToMessages(String.format("Them: %s\n", strMessage));
        });
        tcpButton.addActionListener(e -> {
            onTcpListenButton();
        });
    }

    private void onTcpListenButton() {
        if (tcpListener.isRunning()) {
            tcpListener.stopListening();
            tcpButton.setText("Start");
        } else {
            tcpListener.setPort(Integer.parseInt(tcpPortField.getText()));
            tcpListener.startListening();
            tcpButton.setText("Stop");
        }
    }

    private void appendToMessages(String message) {
        messagesArea.append(message);
        messagesArea.invalidate();
    }

    private void onUpdListenButton() throws SocketException {
        if (!udpListener.isRunning()) {
            udpListener.setPort(Integer.parseInt(udpPortField.getText()));
            udpListener.startListening();
            updListenButton.setText("Stop");
        } else {
            udpListener.stopListening();
            updListenButton.setText("Start");
        }
    }


    private void onSendButton() throws IOException {
        InetAddress targetAddress = InetAddress.getByName(addressField.getText());
        int targetPort = Integer.parseInt(portField.getText());
        String message = messageField.getText();
        appendToMessages(String.format("You: %s\n", messageField.getText()));
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
                gui.udpListener.stopListening();
                gui.tcpListener.stopListening();
            }
        });
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
