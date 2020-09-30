package com.httpserver.dao.domain;

import org.postgresql.geometric.PGcircle;

import java.util.Arrays;

public class Planet {
    private int id;
    private String name;
    private String history;
    private PGcircle radius;
    private byte[] photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public PGcircle getRadius() {
        return radius;
    }

    public void setRadius(PGcircle radius) {
        this.radius = radius;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", history='" + history + '\'' +
                ", radius=" + radius +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
