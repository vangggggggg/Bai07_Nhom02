package Demo;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Service
public class UdpService {
    public void sendUdpMessage(String message, String ipAddress, int port) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte[] buffer = message.getBytes();
        InetAddress address = InetAddress.getByName(ipAddress);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
        socket.close();
    }
}
