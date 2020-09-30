package com.httpserver.settings;

import java.util.HashMap;

public final class Text {
    private static HashMap<String, String> data = new HashMap<>();

    public static String get(String key) {
        if (data.containsKey(key)) return data.get(key);
        return "";
    }

    public static void init() {
        data.put("SQL_BOOK_SELECT_BY_ID", "select * from public.book where id = ?");
        data.put("SQL_BOOK_SELECT_ALL", "select * from public.book");
        data.put("SQL_BOOK_INSERT", "insert into public.book (title, author, year, place, image) values( ?, ?, ?, ?, ? )");
        data.put("SQL_BOOK_DELETE", "delete from public.book where id = ?");
        data.put("SQL_BOOK_UPDATE", "update public.book set title = ? , author = ? , year = ? , place = ? , image = ? where id = ?");
        data.put("SQL_PLANET_DELETE", "delete from public.planet where id = ?");
        data.put("SQL_PLANET_UPDATE", "update public.planet set name = ? , history = ? , radius = ? , photo = ?  where id = ?");
        data.put("SQL_PLANET_SELECT_ALL", "select * from public.planet");
        data.put("SQL_PLANET_SELECT_BY_ID", "select * from public.planet where id = ?");
        data.put("SQL_PLANET_INSERT", "insert into public.planet (name, history, radius, photo) values( ?, ?, ?, ? )");
    }
}
