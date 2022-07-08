package com.ouz.favoriterecipe.exception.account;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Optional;

/**
 * @author : OuZ
 * @date-time : 5.07.2022 - 01:16
 */
@RestControllerAdvice
public class AccountException extends RuntimeException
{

    protected HttpStatus status;
    protected String desc;
    protected String message;

    protected final static Locale SYSTEM_LOCALESYSTEM_LOCALE = LocaleContextHolder.getLocale();

    public AccountException() {
        desc = "This is an exception that throws when Account operations goes wrong  !";
    }

    private AccountException(String dsc,HttpStatus status){
        super();
        this.message = "uncategorized.error";
        this.desc = dsc;
        this.status = status;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AccountException notFoundException(){
        return new AccountNotFoundException();
    }

    @ExceptionHandler(AccountNoContentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public AccountException noContentException(){
        return new AccountNoContentException();
    }

    @ExceptionHandler(AccountConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public AccountException conflictException(){
        return new AccountConflictException();
    }

    @ExceptionHandler(AccountBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AccountException badRequestException(){
        return new AccountBadRequestException();
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public String getDesc(){
        return this.desc;
    }

}
