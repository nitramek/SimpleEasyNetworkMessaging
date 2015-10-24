package cz.nitramek.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class UDPListener extends BasicListener {
    private DatagramSocket socket;
    private int port;

    public UDPListener() {
        listeningThread = new UDPThread();
    }


    @Override
    public void startListening() {
        if (!listeningThread.isRunning()) {
            try {
                socket = new DatagramSocket(port);
                listeningThread = new UDPThread();
                super.startListening();
            } catch (SocketException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void stopListening() {
        if (listeningThread.isRunning()) {
            try {
                listeningThread.setRunning(false);
                socket.close();
                listeningThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    class UDPThread extends ServiceThread {

        @Override
        protected void doStuff() {
            try {
                byte[] buffer = new byte[256];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                fireMessageListeners(UDPListener.this, new String(packet.getData(), packet.getOffset(), packet.getLength()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
