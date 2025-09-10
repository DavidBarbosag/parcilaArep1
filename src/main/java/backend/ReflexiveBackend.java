package backend;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ReflexiveBackend {

    private static LinkedList<String> linkedList = new LinkedList<>();
    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(45000);
            System.out.println("Backend escuchando en puerto 45000...");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 45000.");
            System.exit(1);
        }
        Socket clientSocket = null;

        boolean running = true;

        while (running) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine = "{\"error\":\"Sin respuesta\"}";

            String path = null;
            boolean firstline = true;
            URI requri = null;

            while ((inputLine = in.readLine()) != null) {
                if (firstline) {
                    requri = new URI(inputLine.split(" ")[1]);
                    System.out.println("Path: " + requri.getPath());
                    firstline = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            String query = requri.getQuery();
            System.out.println(query);
            if (query != null) {
                if (query.contains("=")) {
                    outputLine = appendValue(query);
                } else if (query.contains("list")){
                    outputLine = listValues();
                } else if (query.contains("clear")){
                    outputLine = deleteList();
                } else if (query.contains("stats")){
                    outputLine = stats();
                }
            } else {
                outputLine = "Consulta no valida";
            }

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: application/json");
            out.println("Connection: close");
            out.println("Content-Length: " + outputLine.length());
            out.println();
            out.println(outputLine);
            out.flush();
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    public static String appendValue(String query){

        String numero = query.substring(2);
        linkedList.add(numero);
        return "{\"status\":\"" + numero + "\"}";
    }

    public static String listValues(){
        StringBuilder sb = new StringBuilder("{\"values\":[");
        for (String s : linkedList) {
            sb.append("\"").append(s).append("\"");

        }
        sb.append("]}");

        return sb.toString();
    }


    public static String deleteList(){
        linkedList.clear();
        return "{\"list_cleared\":\" }";
    }

    public static String stats(){

        return "";
    }
}
