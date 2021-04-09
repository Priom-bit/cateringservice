package com.example.cateringservice;

public class MyBreakfastDescription {

    private String BreakfastName;
    private String BreakfastDescription;
    private Integer BreakfastImage;
    private Integer Breakfastprice;

    public Integer getBreakfastprice() {
        return Breakfastprice;
    }

    public void setBreakfastprice(Integer breakfastprice) {
        Breakfastprice = breakfastprice;
    }

    public MyBreakfastDescription(String breakfastName, String breakfastDescription, Integer breakfastImage, Integer breakfastprice) {
        this.BreakfastName = breakfastName;
        this.BreakfastDescription = breakfastDescription;
        this.BreakfastImage = breakfastImage;
        this.Breakfastprice = breakfastprice;
    }

    public String getBreakfastName() {
        return BreakfastName;
    }

    public void setBreakfastName(String breakfastName) {
        BreakfastName = breakfastName;
    }

    public String getBreakfastDescription() {
        return BreakfastDescription;
    }

    public void setBreakfastDescription(String breakfastDescription) {
        BreakfastDescription = breakfastDescription;
    }

    public Integer getBreakfastImage() {
        return BreakfastImage;
    }

    public void setBreakfastImage(Integer drinksImage) {
        BreakfastImage = drinksImage;
    }
}

