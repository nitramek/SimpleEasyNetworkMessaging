package cz.nitramek.networking;

import cz.nitramek.eventsupport.MessageListener;
import cz.nitramek.eventsupport.MessageListenerSupport;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class UDPListener extends AbstractListener {
    private DatagramSocket socket;
    private MessageListenerSupport messageListenerSupport = new MessageListenerSupport();

    public void removeMessageListener(MessageListener listener) {
        messageListenerSupport.removeMessageListener(listener);
    }

    public void addMessageListener(MessageListener listener) {
        messageListenerSupport.addMessageListener(listener);
    }

    Thread listeningThread = new Thread(this);

    public void start(int port) throws SocketException {
        socket = new DatagramSocket(port);
        setRunning(true);
        listeningThread.start();
    }

    public void stop() {
        if (isRunning()) {
            setRunning(false);

            try {
                socket.close();
                listeningThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void doStuff() {
        byte[] buffer = new byte[256];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(packet);
            messageListenerSupport.fireMessageEvent(this, new String(packet.getData(), packet.getOffset(), packet.getLength()));
        } catch (IOException e) {
            e.printStackTrace();
            setRunning(false);
        }
    }
}
