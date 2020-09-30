package com.httpserver.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<DOMAIN, ID> {
    DOMAIN findById(ID id) throws SQLException;
    List<DOMAIN> findByAll() throws SQLException;
    DOMAIN insert(DOMAIN domain) throws SQLException;
    DOMAIN update(DOMAIN domain) throws SQLException;
    boolean delete(ID id) throws SQLException;
}
