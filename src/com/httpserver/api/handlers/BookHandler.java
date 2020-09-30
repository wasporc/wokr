package com.httpserver.api.handlers;

import com.httpserver.dao.BookDao;
import com.httpserver.dao.domain.Book;
import com.httpserver.settings.Format;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.postgresql.geometric.PGpoint;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class BookHandler extends HandlerHttp {

    @Override
    protected void deleteMethod(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Headers", "x-prototype-version, x-requested-with");
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Content-type", "charset=utf-8");
        try {
            Map<String, String> splitQuery = splitQuery(decodePostBody(exchange.getRequestBody()));
            //String response = BookDao.getBookDao().delete(Integer.valueOf(splitQuery.get("id"))) ? "{\"success\":true}" : "{\"success\":false}";
            String response = BookDao.getBookDao().delete(Integer.valueOf(
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
        Book book = null;
        try {
            Map<String, String> splitQuery = splitQuery(decodePostBody(exchange.getRequestBody()));
            book = fillRequestBook(splitQuery);
            if (post) {
                BookDao.getBookDao().insert(book);
            } else {
                book.setId(Integer.parseInt(splitQuery.get("id")));
                BookDao.getBookDao().update(book);
            }

            headers.add("Access-Control-Allow-Headers", "x-prototype-version, x-requested-with");
            headers.add("Access-Control-Allow-Origin", "*");
            Charset charset = StandardCharsets.UTF_8;
            headers.set("Content-Type", String.format("application/json; charset=%s", charset));
            String respText = Format.bookIdJsonFormat(book);
            final byte[] rawResponseBody = respText.getBytes(charset);
            exchange.sendResponseHeaders(200, rawResponseBody.length);
            exchange.getResponseBody().write(rawResponseBody);
            exchange.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void getMethod(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        String rawQuery = exchange.getRequestURI().getRawQuery();
        Map<String, String> splitQuery = splitQuery(rawQuery);
        String respText = "";
        try {//проверка есть ли id  в запросе, если нет то вернуть весь список
            if (splitQuery.containsKey("id")) {
                Book book = null;

                book = BookDao.getBookDao().findById(Integer.valueOf(splitQuery.get("id")));
                respText = Format.bookIdJsonFormat(book);

            } else {
                respText = Format.booksListJsonFormat(BookDao.getBookDao().findByAll());
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

    private Book fillRequestBook(Map<String, String> map) throws SQLException {
        Book book = new Book();
        book.setTitle(map.get("name"));
        book.setYear(Integer.parseInt(map.get("year")));
        book.setAuthor(map.get("author"));
        book.setPlace(new PGpoint(map.get("place")));
        if (map.containsKey("img")) {
            byte[] img = DatatypeConverter.parseBase64Binary(map.get("img"));
            book.setImage(img);
        }
        return book;
    }
}
