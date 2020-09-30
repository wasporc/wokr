package com.httpserver.dao;

import com.httpserver.dao.domain.Planet;
import com.httpserver.settings.Text;
import org.postgresql.geometric.PGcircle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class PlanetDao implements Dao<Planet, Integer>{

    private static PlanetDao planetDao;

    private PlanetDao(){}

    public static PlanetDao getPlanetDao(){
        if (planetDao == null){
            planetDao = new PlanetDao();
        }
        return planetDao;
    }

    @Override
    public Planet findById(Integer id) throws SQLException {
        Planet planet = null;
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(Text.get("SQL_PLANET_SELECT_BY_ID"));
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                planet = new Planet();
                planet.setId(resultSet.getInt("id"));
                planet.setName(resultSet.getString("name"));
                planet.setHistory(resultSet.getString("history"));
                planet.setRadius((PGcircle)resultSet.getObject("radius"));
                planet.setPhoto(resultSet.getBytes("photo"));
            }
        }
        return planet;
    }

    @Override
    public List<Planet> findByAll() throws SQLException {
        List<Planet> planetList = new ArrayList<>();
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(Text.get("SQL_PLANET_SELECT_ALL"));
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Planet planet = new Planet();
                planet.setId(resultSet.getInt("id"));
                planet.setName(resultSet.getString("name"));
                planet.setHistory(resultSet.getString("history"));
                planet.setRadius((PGcircle)resultSet.getObject("radius"));
                planet.setPhoto(resultSet.getBytes("photo"));
                planetList.add(planet);
            }
        }
        System.out.println(planetList);
        return planetList;
    }

    @Override
    public Planet insert(Planet planet) throws SQLException {
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(Text.get("SQL_PLANET_INSERT"), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, planet.getName());
            ps.setString(2, planet.getHistory());
            ps.setObject(3, planet.getRadius());
            ps.setBytes(4, planet.getPhoto());

            int ret = ps.executeUpdate();
            if (ret == 0) {
                throw new SQLException("insert failed");
            }
            try (ResultSet generatedKey = ps.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    planet.setId(generatedKey.getInt(1));
                } else throw new SQLException("after insert id not return");
            }
        }
        return planet;
    }

    @Override
    public Planet update(Planet planet) throws SQLException {
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(Text.get("SQL_PLANET_UPDATE"));
            ps.setString(1, planet.getName());
            ps.setString(2, planet.getHistory());
            ps.setObject(3, planet.getRadius());
            ps.setBytes(4, planet.getPhoto());
            ps.setInt(5, planet.getId());

            int ret = ps.executeUpdate();
            if (ret == 0) {
                throw new SQLException("insert failed");
            }
        }
        return planet;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        boolean isOk = false;
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(Text.get("SQL_PLANET_DELETE"));
            ps.setInt(1, id);

            isOk = ps.executeUpdate() != 0;
        }
        return isOk;
    }
}
