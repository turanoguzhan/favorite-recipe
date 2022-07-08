package com.ouz.favoriterecipe.exception.recipe;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

/**
 * @author : OuZ
 * @date-time : 5.07.2022 - 01:22
 */
@RestControllerAdvice
public class RecipeException extends RuntimeException
{

    protected HttpStatus status;
    protected String desc;
    protected String message;

    protected final static Locale SYSTEM_LOCALESYSTEM_LOCALE = LocaleContextHolder.getLocale();

    public RecipeException() {
        desc = "This is an exception that throws when Recipe operations goes wrong  !";
    }

    private RecipeException(String dsc, HttpStatus status){
        super();
        this.message = "uncategorized.error";
        this.desc = dsc;
        this.status = status;
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RecipeException notFoundException(){
        return new RecipeNotFoundException();
    }

    @ExceptionHandler(RecipeNoContentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RecipeException noContentException(){
        return new RecipeNoContentException();
    }

    @ExceptionHandler(RecipeConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public RecipeException conflictException(){
        return new RecipeConflictException();
    }

    @ExceptionHandler(RecipeBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RecipeException badRequestException(){
        return new RecipeBadRequestException();
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public String getDesc(){
        return this.desc;
    }

}
