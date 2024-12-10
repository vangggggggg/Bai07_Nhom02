package bai07_nhom02.Demo;

import org.springframework.stereotype.Service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Service
public class UdpNotificationService {

    public void sendUdpNotification(String message, String ipAddress, int port) {
        try {
            DatagramSocket socket = new DatagramSocket();
            byte[] buffer = message.getBytes();
            InetAddress address = InetAddress.getByName(ipAddress);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

            socket.send(packet);
            socket.close();

            System.out.println("UDP message sent: " + message);
        } catch (Exception e) {
            System.err.println("Error sending UDP message: " + e.getMessage());
        }
    }
}

