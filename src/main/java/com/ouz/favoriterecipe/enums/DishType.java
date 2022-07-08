package com.ouz.favoriterecipe.enums;

public enum DishType
{
    BREAKFAST("BREAKFAST"),
    LUNCH("LUNCH"),
    DINNER("DINNER"),
    SALAD("SALAD"),
    MAIN_COURSE("MAIN_COURSE"),
    DESSERT("DESSERT"),
    SNACK("SNACK"),
    APPETIZER("APPETIZER"),
    VEGETARIAN("VEGETARIAN");

    public final String value;

    DishType(String value) {
        this.value = value;
    }
}
