package com.example.skibslogapp.model;


public class Togt {

    private long id = -1;
    private String name;

    public Togt(String name) {
        this.name = name;
    }

    public static void addLogPost(LogInstans nyeste) {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
