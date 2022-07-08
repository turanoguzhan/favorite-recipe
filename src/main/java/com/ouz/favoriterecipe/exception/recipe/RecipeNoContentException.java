package com.ouz.favoriterecipe.exception.recipe;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class RecipeNoContentException extends RecipeException implements Supplier<RecipeNoContentException> {
    private static final long serialVersionUID = 1L;

    public RecipeNoContentException() {
        super();
        this.message = "recipe.no.content";
        this.status = HttpStatus.NO_CONTENT;
    }

    @Override
    public RecipeNoContentException get() {
        return this;
    }
}
