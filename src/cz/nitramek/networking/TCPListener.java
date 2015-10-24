package cz.nitramek.networking;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPListener extends BasicListener {

    private ServerSocket serverSocket;
    private int port;

    public TCPListener() {
        listeningThread = new TCPThread();
    }

    @Override
    public void startListening() {
        if (!listeningThread.isRunning()) {
            try {
                serverSocket = new ServerSocket(port);
                listeningThread = new TCPThread();
                super.startListening();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void stopListening() {
        if (listeningThread.isRunning()) {
            try {
                listeningThread.setRunning(false);

                serverSocket.close();
                listeningThread.join();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    class TCPThread extends ServiceThread {

        @Override
        protected void doStuff() {
            try {
                Socket receivedSocket = serverSocket.accept();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(receivedSocket.getInputStream()))) {
                    fireMessageListeners(TCPListener.this, reader.readLine());
                }
                receivedSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
