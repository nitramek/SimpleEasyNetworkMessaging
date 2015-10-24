package cz.nitramek.networking;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Martin on 24.10.2015.
 */
public class NetworkTester {

    public static void sendTCPMessage(String message, InetAddress adress, int port) throws IOException {
        Socket socket = new Socket(adress, port);
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.println(message);
        writer.flush();
        writer.close();
        socket.close();
    }

    public static void sendUDPMessage(String message, InetAddress adress, int port) throws IOException {
        byte[] messageArray = message.getBytes().clone();
        DatagramPacket packet = new DatagramPacket(messageArray, messageArray.length);
        packet.setAddress(adress);
        packet.setPort(port);
        DatagramSocket datagramSocket = new DatagramSocket();
        datagramSocket.send(packet);
        datagramSocket.close();

    }
}
