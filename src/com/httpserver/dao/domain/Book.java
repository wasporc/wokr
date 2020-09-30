package com.httpserver.dao.domain;

import org.postgresql.geometric.PGpoint;

import java.util.Arrays;

public class Book {
    private int id;
    private String title;
    private String author;
    private int year;
    private PGpoint place;
    private byte[] image;

    public Book(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public PGpoint getPlace() {
        return place;
    }

    public void setPlace(PGpoint place) {
        this.place = place;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", place=" + place +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
