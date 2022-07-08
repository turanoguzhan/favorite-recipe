package com.ouz.favoriterecipe.exception.recipe;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class RecipeConflictException extends RecipeException implements Supplier<RecipeConflictException> {
    private static final long serialVersionUID = 1L;

    public RecipeConflictException() {
        super();
        this.message = "recipe.is.already.exists";
        this.status = HttpStatus.CONFLICT;
    }

    @Override
    public RecipeConflictException get() {
        return this;
    }
}
