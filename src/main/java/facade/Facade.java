package facade;

import java.io.*;
import java.net.*;

public class Facade {

    public static void main(String[] args) throws Exception {
        new Facade();
    }

    public Facade() throws Exception {
        ServerSocket serverSocket = new ServerSocket(35000);
        System.out.println("Facade escuchando en puerto 35000...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream out = clientSocket.getOutputStream();

            String line = in.readLine();
            if (line == null) continue;

            if (line.contains("/cliente")) {
                String html = getClientHtml();
                sendResponse(out, "text/html", html);
            } else if (line.contains("/")){
                String option = line.split("/")[1];
                System.out.println(option);
                String backendResp = callBackend(option);
                sendResponse(out, "application/json", backendResp);
            }

            clientSocket.close();
        }
    }

    private static String callBackend(String option) throws Exception {
        URL url = new URL("http://localhost:45000/" + option);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        return br.readLine();
    }

    private static void sendResponse(OutputStream out, String contentType, String body) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "\r\n" +
                body;
        out.write(response.getBytes());
    }


    private static String getClientHtml() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Parcial 1</title>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Interactuar con linkedList</h1>\n" +
                "<input type=\"text\" id=\"num\" placeholder=\"añadir a cola...\">\n" +
                "<button onclick=\"sendCommand()\">Enviar</button>\n" +
                "\n" +
                "<pre id=\"response\"></pre>\n" +
                "\n" +
                "<button onclick=\"send()\">list</button>\n" +
                "\n" +
                "<pre id=\"responseList\"></pre>\n" +
                "\n" +
                "<button onclick=\"clearList()\">clear</button>\n" +
                "\n" +
                "<pre id=\"responseClear\"></pre>\n" +
                "\n" +
                "<script>\n" +
                "    function sendCommand(){\n" +
                "        let url = \"/add?x=\" + document.getElementById(\"num\").value;\n" +
                "        fetch(url)\n" +
                "            .then(x => x.text())\n" +
                "            .then(y => document.getElementById(\"response\").innerHTML = y);\n" +
                "    }\n" +
                "    function send(){\n" +
                "        let url = \"/list\";\n" +
                "        fetch(url)\n" +
                "            .then(x => x.text())\n" +
                "            .then(y => document.getElementById(\"responseList\").innerHTML = y);\n" +
                "    }\n" +
                "    function clearList(){\n" +
                "        let url = \"/clear\";\n" +
                "        fetch(url)\n" +
                "            .then(x => x.text())\n" +
                "            .then(y => {\n" +
                "                document.getElementById(\"responseClear\").innerHTML = y;\n" +
                "                document.getElementById(\"responseList\").innerHTML = \"\";\n" +
                "                document.getElementById(\"response\").innerHTML = \"\";\n" +
                "            });\n" +
                "    }\n" +
                "</script>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
    }
}
