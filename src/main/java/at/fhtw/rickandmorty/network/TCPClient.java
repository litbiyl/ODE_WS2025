package at.fhtw.rickandmorty.network;

import at.fhtw.rickandmorty.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

public class TCPClient {
    private static final int CONNECT_TIMEOUT = 5000;

    public static String get(String host, int port, String path) {
        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

            try (SSLSocket socket = (SSLSocket) factory.createSocket(host, port)) {

                //socket.connect(new InetSocketAddress(host, port), CONNECT_TIMEOUT);

                PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

                writer.println("GET " + path + " HTTP/1.1");
                writer.println("Host: " + host);
                writer.println("Accept: application/json");
                writer.println("Connection: close");
                writer.println();

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line).append("\n");
                }

                return response.toString();
            }
        } catch (Exception e) {
            Logger.log("ERROR", "HTTPS request failed: " + e.getMessage());
            throw new RuntimeException("HTTPS request failed", e);
        }
    }
}