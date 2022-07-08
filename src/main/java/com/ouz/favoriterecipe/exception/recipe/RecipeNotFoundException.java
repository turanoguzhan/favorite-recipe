package com.ouz.favoriterecipe.exception.recipe;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class RecipeNotFoundException extends RecipeException implements Supplier<RecipeNotFoundException> {
    private static final long serialVersionUID = 1L;

    public RecipeNotFoundException() {
        super();
        this.message = "recipe.not.found";
        this.status = HttpStatus.NOT_FOUND;
    }

    @Override
    public RecipeNotFoundException get() {
        return this;
    }
}
