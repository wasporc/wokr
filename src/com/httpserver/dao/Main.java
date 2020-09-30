package com.httpserver.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try(Connection connection = DaoFactory.getConnection()){
            PreparedStatement ps = connection.prepareStatement("update public.book set image = ?");
            File img = new File("C:\\Users\\Wasp\\IdeaProjects\\HttpServer\\resources\\leaves.png");
            ps.setBytes(1, Files.readAllBytes(img.toPath()));
            ps.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
}
