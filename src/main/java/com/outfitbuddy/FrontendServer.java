package com.outfitbuddy;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class FrontendServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(3000), 0);

        server.createContext("/", exchange -> {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) {
                path = "/index.html";
            }

            String resourcePath = "static" + path;
            InputStream is = FrontendServer.class.getClassLoader().getResourceAsStream(resourcePath);

            if (is == null) {
                String response = "Not Found";
                exchange.sendResponseHeaders(404, response.length());
                exchange.getResponseBody().write(response.getBytes());
                exchange.close();
                return;
            }

            String contentType = getContentType(path);
            byte[] content = is.readAllBytes();
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, content.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(content);
            }
        });

        server.setExecutor(null);
        server.start();
        System.out.println("Frontend-Server läuft auf http://localhost:3000");
    }

    private static String getContentType(String path) {
        if (path.endsWith(".html")) {
            return "text/html";
        }
        if (path.endsWith(".css")) {
            return "text/css";
        }
        if (path.endsWith(".js")) {
            return "application/javascript";
        }
        return "application/octet-stream";
    }
}
