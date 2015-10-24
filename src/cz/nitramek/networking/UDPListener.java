package cz.nitramek.networking;

import cz.nitramek.eventsupport.MessageListenerSupport;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class UDPListener extends AbstractListener {
    private DatagramSocket socket;
    MessageListenerSupport messageListenerSupport = new MessageListenerSupport();

    Thread listeningThread = new Thread(this);

    public void start(int port) throws SocketException {
        socket = new DatagramSocket(port);
        setRunning(true);
        listeningThread.start();
    }

    public void stop() {
        if (listeningThread.getState().equals(Thread.State.RUNNABLE)) {
            setRunning(false);
            try {
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
            messageListenerSupport.fireMessageEvent(this, new String(packet.getData()));
        } catch (IOException e) {
            e.printStackTrace();
            setRunning(false);
        }
    }
}
