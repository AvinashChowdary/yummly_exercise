package com.avinash.codingexercise.pojo;

public class Recipe {

    private String image;

    private String name;

    private String time;

    private String noOfIngredients;

    private String course;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getNoOfIngredients() {
        return noOfIngredients;
    }

    public void setNoOfIngredients(String noOfIngredients) {
        this.noOfIngredients = noOfIngredients;
    }
}
