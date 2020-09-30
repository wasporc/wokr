package com.httpserver.settings;

import com.httpserver.dao.domain.Book;
import com.httpserver.dao.domain.Planet;
import org.postgresql.geometric.PGcircle;
import org.postgresql.geometric.PGpoint;

import javax.xml.bind.DatatypeConverter;
import java.util.List;

final public class Format {

    public static  String planetIdJsonFormat(Planet planet){
        String radius = planet.getRadius() == null ? "" : radiusConvert(planet.getRadius());
        return String.format("{\"success\": true,\"planets\": [{\"id\": %d, \"name\": '%s', \"history\": '%s'," +
                " \"radius\": '%s', \"photo\": '%s' }]}  ",
                planet.getId(),planet.getName(),planet.getHistory(),radius,getContentBase64(planet.getPhoto()));
    }

    public static String planetsListJsonFormat(List<Planet> planets) {
        StringBuilder form = new StringBuilder("{\"success\": true, \"planets\": [");

        for (Planet planet : planets) {
            String radius = planet.getRadius() == null ? "" : radiusConvert(planet.getRadius());
            form.append(String.format("{\"id\": %d, \"name\": '%s', \"history\": '%s'," +
                    " \"radius\": '%s', \"photo\": '%s' },",
                    planet.getId(),planet.getName(),planet.getHistory(),radius,getContentBase64(planet.getPhoto())));
        }
        form.deleteCharAt(form.lastIndexOf(","));
        form.append("]}");
        return form.toString();
    }

    private static String radiusConvert(PGcircle radius) {
        return String.valueOf(radius.radius);
    }

    public static String bookIdJsonFormat(Book book) {
        String place = book.getPlace() == null ? "" : pointConvert(book.getPlace());
        int year = book.getYear();
        return String.format("{" +
                "\"success\": true," +
                "\"books\": [" +
                "{\"id\": %d, \"name\": '%s', \"author\": '%s', \"year\": %d, \"place\": '%s', \"img\": '%s'}" +
                "   ]" +
                "}", book.getId(), book.getTitle(), book.getAuthor(), year, place, getContentBase64(book.getImage()));
    }

    public static String booksListJsonFormat(List<Book> books) {
        StringBuilder form = new StringBuilder("{\"success\": true, \"books\": [");
        for (Book book : books) {
            String place = "";
            if (book.getPlace() != null) {
                place = pointConvert(book.getPlace());
            }
            String format = String.format("{\"id\": %1d, \"name\": '%2s', \"author\": '%3s', \"year\": %4d, \"place\": '%5s', \"img\": '%6s'},",
                    book.getId(), book.getTitle(), book.getAuthor(), book.getYear(), place, getContentBase64(book.getImage()));
            form.append(format);
        }
        form.deleteCharAt(form.lastIndexOf(","));
        form.append("]}");
        return form.toString();
    }

    private static String getContentBase64(byte[] chars){
        return DatatypeConverter.printBase64Binary(chars);
    }

    public static String successJsonResponse(boolean ok) {
        return String.format("{\"success\": %s}", ok ? "true" : "false");
    }

    private static String pointConvert(PGpoint pGpoint) {
        return "" + (int)pGpoint.x + " , " + (int)pGpoint.y;
    }
}
