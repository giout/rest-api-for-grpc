package com.grpc.rest.api.models;

public class ProductModel {
    private int id;
    private String description;

    public ProductModel(String description){
        this.description = description;
    }

    public ProductModel(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }
}
