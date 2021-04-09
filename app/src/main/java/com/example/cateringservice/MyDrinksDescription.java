package com.example.cateringservice;

public class MyDrinksDescription {

    private String DrinksName;
    private String DrinksDescription;
    private Integer DrinksImage;
    private Integer Drinksprice;

    public Integer getDrinksprice() {
        return Drinksprice;
    }

    public void setDrinksprice(Integer drinksprice) {
        Drinksprice = drinksprice;
    }

    public MyDrinksDescription(String drinksName, String drinksDescription, Integer drinksImage, Integer drinksprice) {
       this.DrinksName = drinksName;
       this.DrinksDescription = drinksDescription;
       this.DrinksImage = drinksImage;
       this.Drinksprice = drinksprice;
    }

    public String getDrinksName() {
        return DrinksName;
    }

    public void setDrinksName(String drinksName) {
        DrinksName = drinksName;
    }

    public String getDrinksDescription() {
        return DrinksDescription;
    }

    public void setDrinksDescription(String drinksDescription) {
        DrinksDescription = drinksDescription;
    }

    public Integer getDrinksImage() {
        return DrinksImage;
    }

    public void setDrinksImage(Integer drinksImage) {
        DrinksImage = drinksImage;
    }
}




