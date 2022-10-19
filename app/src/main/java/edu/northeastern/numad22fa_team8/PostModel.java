package edu.northeastern.numad22fa_team8;

public class PostModel {
    private String catName;
    private String zipcode;
    private String description;

    public PostModel(String catName, String zipcode, String description) {
        this.catName = catName;
        this.zipcode = zipcode;
        this.description = description;
    }

    public String getCatName() {
        return catName;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getDescription() {
        return description;
    }
}
