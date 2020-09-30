package com.httpserver.api;

import com.httpserver.api.handlers.BookHandler;
import com.httpserver.api.handlers.PlanetHandler;
import com.httpserver.settings.Settings;
import com.httpserver.settings.Text;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Application {
    public static void main(String[] args) throws IOException {
        Settings.init();
        Text.init();
        int serverPort = Integer.parseInt(Settings.data.get("PORTS"));
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/api/books", new BookHandler());
        server.createContext("/api/planets", new PlanetHandler());
        ExecutorService executor = Executors.newFixedThreadPool(5);
        server.setExecutor(executor); // creates a default executor
        server.start();
    }


}
