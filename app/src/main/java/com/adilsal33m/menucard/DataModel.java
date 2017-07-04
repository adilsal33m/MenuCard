package com.adilsal33m.menucard;

/**
 * Created by Adil Saleem on 6/25/2017.
 */

public class DataModel {

    String name;
    String description;
    String price;
    String count;

    public DataModel(String name, String description, String price, String count ) {
        this.name=name;
        this.description=description;
        this.price=price;
        this.count=count;

    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}