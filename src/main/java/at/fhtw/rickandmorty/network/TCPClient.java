package at.fhtw.rickandmorty.network;

import at.fhtw.rickandmorty.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;

public class TCPClient {
    private static final int READ_TIMEOUT = 10000;
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 1000;

    public static String get(String host, int port, String path) {
        IOException lastException = null;

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                return executeRequest(host, port, path);
            } catch (SocketTimeoutException e) {
                lastException = e;
                Logger.log("ERROR", "Request timed out (attempt " + attempt + "/" + MAX_RETRIES + "): " + path);
            } catch (IOException e) {
                lastException = e;
                Logger.log("ERROR", "Request failed (attempt " + attempt + "/" + MAX_RETRIES + "): " + e.getMessage());
            }

            if (attempt < MAX_RETRIES) {
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        Logger.log("ERROR", "HTTPS request failed after " + MAX_RETRIES + " attempts: " + path);
        throw new RuntimeException("HTTPS request failed after " + MAX_RETRIES + " attempts", lastException);
    }

    private static String executeRequest(String host, int port, String path) throws IOException {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

        try (SSLSocket socket = (SSLSocket) factory.createSocket(host, port)) {
            socket.setSoTimeout(READ_TIMEOUT);

            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

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
    }
}