package com.example.cateringservice;

public class MyLunchDescription {

    private String LunchName;
    private String LunchDescription;
    private Integer LunchImage;
    private Integer Lunchprice;

    public Integer getLunchprice() {
        return Lunchprice;
    }

    public void setLunchprice(Integer lunchprice) {
        Lunchprice = lunchprice;
    }

    public MyLunchDescription(String lunchName, String lunchDescription, Integer lunchImage, Integer lunchprice) {
        this.LunchName = lunchName;
        this.LunchDescription = lunchDescription;
        this.LunchImage = lunchImage;
        this.Lunchprice = lunchprice;
    }

    public String getLunchName() {
        return LunchName;
    }

    public void setLunchName(String lunchName) {
        lunchName = lunchName;
    }

    public String getLunchDescription() {
        return LunchDescription;
    }

    public void setLunchDescription(String lunchDescription) {
        LunchDescription = lunchDescription;
    }

    public Integer getLunchImage() {
        return LunchImage;
    }

    public void setLunchImage(Integer drinksImage) {
        LunchImage = drinksImage;
    }
}
