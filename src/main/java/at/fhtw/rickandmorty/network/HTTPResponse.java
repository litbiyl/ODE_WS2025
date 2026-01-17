package at.fhtw.rickandmorty.network;

public class HTTPResponse {

    public static String extractJson(String response) {
        int index = response.indexOf("\r\n\r\n");
        int offset = 4;
        if (index == -1) {
            index = response.indexOf("\n\n");
            offset = 2;
        }

        if (index == -1) {
            return null;
        }

        return response.substring(index + offset).trim();
    }
}
