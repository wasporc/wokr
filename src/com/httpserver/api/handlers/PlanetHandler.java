package com.httpserver.api.handlers;

import com.httpserver.dao.PlanetDao;
import com.httpserver.dao.domain.Planet;
import com.httpserver.settings.Format;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.postgresql.geometric.PGcircle;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class PlanetHandler extends HandlerHttp{

    @Override
    protected void deleteMethod(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Headers", "x-prototype-version, x-requested-with");
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Content-type", "charset=utf-8");
        try {
            Map<String, String> splitQuery = splitQuery(decodePostBody(exchange.getRequestBody()));
            String response = PlanetDao.getPlanetDao().delete(Integer.valueOf(
                    splitQuery.get("id"))) ? Format.successJsonResponse(true) : Format.successJsonResponse(false);
            final byte[] rawResponseBody = response.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, rawResponseBody.length);
            exchange.getResponseBody().write(rawResponseBody);
            exchange.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void putMethod(HttpExchange exchange) {
        postMethod(exchange, false);
    }

    @Override
    protected void postMethod(HttpExchange exchange, boolean post) {
        Headers headers = exchange.getResponseHeaders();
        Planet planet = null;
        try {
            Map<String, String> splitQuery = splitQuery(decodePostBody(exchange.getRequestBody()));
            planet = fillRequestPlanet(splitQuery);
            if (post) {
                PlanetDao.getPlanetDao().insert(planet);
            } else {
                planet.setId(Integer.parseInt(splitQuery.get("id")));
                PlanetDao.getPlanetDao().update(planet);
            }

            headers.add("Access-Control-Allow-Headers", "x-prototype-version, x-requested-with");
            headers.add("Access-Control-Allow-Origin", "*");
            Charset charset = StandardCharsets.UTF_8;
            headers.set("Content-Type", String.format("application/json; charset=%s", charset));
            String respText = Format.planetIdJsonFormat(planet);
            final byte[] rawResponseBody = respText.getBytes(charset);
            exchange.sendResponseHeaders(200, rawResponseBody.length);
            exchange.getResponseBody().write(rawResponseBody);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        exchange.close();
    }

    @Override
    protected void getMethod(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        String rawQuery = exchange.getRequestURI().getRawQuery();
        Map<String, String> splitQuery = splitQuery(rawQuery);
        String respText = "";
        try {//проверка есть ли id  в запросе, если нет то вернуть весь список
            if (splitQuery.containsKey("id")) {
                Planet planet = null;

                planet = PlanetDao.getPlanetDao().findById(Integer.valueOf(splitQuery.get("id")));
                respText = Format.planetIdJsonFormat(planet);

            } else {
                respText = Format.planetsListJsonFormat(PlanetDao.getPlanetDao().findByAll());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        headers.add("Access-Control-Allow-Headers", "x-prototype-version, x-requested-with");
        headers.add("Access-Control-Allow-Origin", "*");
        Charset charset = StandardCharsets.UTF_8;
        headers.set("Content-Type", String.format("application/json; charset=%s", charset));
        final byte[] rawResponseBody = respText.getBytes(charset);
        try {//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            exchange.sendResponseHeaders(200, rawResponseBody.length);
            exchange.getResponseBody().write(rawResponseBody);
            exchange.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Planet fillRequestPlanet(Map<String, String> splitQuery) throws SQLException {
        Planet planet = new Planet();
        planet.setName(splitQuery.get("name"));
        planet.setHistory(splitQuery.get("history"));
        planet.setRadius(new PGcircle(0,0, Double.parseDouble(splitQuery.get("radius"))));
        if (splitQuery.containsKey("photo")){
            byte[] img = DatatypeConverter.parseBase64Binary(splitQuery.get("photo"));
            planet.setPhoto(img);
        }
        return planet;
    }
}
