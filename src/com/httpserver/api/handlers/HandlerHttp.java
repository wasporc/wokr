package com.httpserver.api.handlers;

import com.httpserver.settings.Settings;
import com.sun.net.httpserver.Headers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class HandlerHttp implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        final String method = exchange.getRequestMethod();
        System.out.println(method);
        switch (method) {
            case Settings.GET:
                getMethod(exchange);
                break;
            case Settings.POST:
                postMethod(exchange, true);
                break;
            case Settings.PUT:
                putMethod(exchange);
                break;
            case Settings.DELETE:
                deleteMethod(exchange);
                break;
            default:
                optionsMethod(exchange);
        }
    }

    protected void optionsMethod(HttpExchange exchange) {
        try {
            Headers headers = exchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Headers", "x-prototype-version, x-requested-with");
            headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            headers.add("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(204, -1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void deleteMethod(HttpExchange exchange);

    protected abstract void putMethod(HttpExchange exchange);

    protected abstract void postMethod(HttpExchange exchange, boolean post);

    protected abstract void getMethod(HttpExchange exchange);

    /**
     * разбор строки запроса на пары ключ=значение
     * @param query строка запроса
     * @return map ключ=значение
     */
    protected Map<String, String> splitQuery(String query) {
        if (query == null || "".equals(query)) return Collections.emptyMap();
        Map<String, String> query_pairs = new LinkedHashMap<>();
        String[] split = query.split("&");
        for (String s : split) {
            int idx = s.indexOf("=");
            query_pairs.put(s.substring(0, idx), s.substring(idx + 1));
        }
        return query_pairs;
    }

    /**
     * разбор закодированной строки в методе post put
     * @param is InputStream запроса
     * @return String раскодированная строка
     * @throws IOException
     */
    protected String decodePostBody(InputStream is) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return URLDecoder.decode(result.toString(), StandardCharsets.UTF_8.toString());
    }


}
