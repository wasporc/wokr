package com.httpserver.dao;

import com.httpserver.dao.domain.Book;
import com.httpserver.settings.Text;
import org.postgresql.geometric.PGpoint;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public final class BookDao implements Dao<Book, Integer> {
    private static BookDao bookDao;

    private BookDao() {
    }

    public static BookDao getBookDao() {
        if (bookDao == null) bookDao = new BookDao();
        return bookDao;
    }

    @Override
    public Book findById(Integer id) throws SQLException {
        Book book = null;
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(Text.get("SQL_BOOK_SELECT_BY_ID"));
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setAuthor(resultSet.getString("author"));
                book.setTitle(resultSet.getString("title"));
                book.setYear(resultSet.getInt("year"));
                book.setPlace((PGpoint) resultSet.getObject("place"));
                book.setImage(resultSet.getBytes("image"));
            }
        }
        return book;
    }

    @Override
    public List<Book> findByAll() throws SQLException {
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(Text.get("SQL_BOOK_SELECT_ALL"));
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setAuthor(resultSet.getString("author"));
                book.setTitle(resultSet.getString("title"));
                book.setYear(resultSet.getInt("year"));
                book.setPlace((PGpoint) resultSet.getObject("place"));
                book.setImage(resultSet.getBytes("image"));
                bookList.add(book);
            }
        }
        return bookList;
    }

    @Override
    public Book insert(Book book) throws SQLException {
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(Text.get("SQL_BOOK_INSERT"), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getYear());
            ps.setObject(4, book.getPlace());
            ps.setBytes(5, book.getImage());

            int ret = ps.executeUpdate();
            if (ret == 0) {
                throw new SQLException("insert failed");
            }
            try (ResultSet generatedKey = ps.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    book.setId(generatedKey.getInt(1));
                } else throw new SQLException("after insert id not return");
            }
        }
        return book;
    }

    @Override
    public Book update(Book book) throws SQLException {
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(Text.get("SQL_BOOK_UPDATE"));
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getYear());
            ps.setObject(4, book.getPlace());
            ps.setBytes(5, book.getImage());
            ps.setInt(6, book.getId());

            int ret = ps.executeUpdate();
            if (ret == 0) {
                throw new SQLException("insert failed");
            }
        }
        return book;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        boolean isOk = false;
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(Text.get("SQL_BOOK_DELETE"));
            ps.setInt(1, id);

            isOk = ps.executeUpdate() != 0;
        }
        return isOk;
    }
}
