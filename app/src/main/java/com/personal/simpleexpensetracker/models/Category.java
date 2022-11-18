package com.personal.simpleexpensetracker.models;

import java.util.ArrayList;

public class Category {

    String category;
    private static ArrayList<Category> categoryList = new ArrayList<>();

    public Category(String category) {

        this.category = category;
    }

    public Category() {
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {

        this.category = category;
    }

    public void initCategory(){
        Category category1 = new Category("Transport");
        categoryList.add(category1);

        Category category2 = new Category("Food");
        categoryList.add(category2);

        Category category3 = new Category("House");
        categoryList.add(category3);

        Category category4 = new Category("Entertainment");
        categoryList.add(category4);

        Category category5 = new Category("Education");
        categoryList.add(category5);

        Category category6 = new Category("Charity");
        categoryList.add(category6);

        Category category7 = new Category("Apparel");
        categoryList.add(category7);

        Category category8 = new Category("Health");
        categoryList.add(category8);

        Category category9 = new Category("Personal");
        categoryList.add(category9);

        Category category10 = new Category("Other");
        categoryList.add(category10);





    }

    public ArrayList<Category> getCategoryList() {

        return categoryList;
    }
}
