package com.discut.pocket.bean;

import java.io.Serializable;

public class Tag implements Serializable {
    private static final long serialVersionUID = 2L;
    private String name;
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
