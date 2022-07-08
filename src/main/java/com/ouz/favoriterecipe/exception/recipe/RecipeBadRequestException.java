package com.ouz.favoriterecipe.exception.recipe;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class RecipeBadRequestException extends RecipeException implements Supplier<RecipeBadRequestException> {
    private static final long serialVersionUID = 1L;

    public RecipeBadRequestException() {
        super();
        this.message = "recipe.bad.request";
        this.status = HttpStatus.BAD_REQUEST;
    }

    @Override
    public RecipeBadRequestException get() {
        return this;
    }
}
