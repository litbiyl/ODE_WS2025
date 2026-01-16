package at.fhtw.rickandmorty.network;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class TCPClient {

    public static String get(String host, int port, String path) {
        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

            try (SSLSocket socket = (SSLSocket) factory.createSocket(host, port)) {
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
            throw new RuntimeException("HTTPS request failed", e);
        }
    }
}