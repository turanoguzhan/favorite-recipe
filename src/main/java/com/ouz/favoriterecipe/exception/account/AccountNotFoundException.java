package com.ouz.favoriterecipe.exception.account;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class AccountNotFoundException extends AccountException implements Supplier<AccountNotFoundException> {
    private static final long serialVersionUID = 1L;

    public AccountNotFoundException() {
        super();
        this.message = "account.not.found";
        this.status = HttpStatus.NOT_FOUND;
    }

    @Override
    public AccountNotFoundException get() {
        return this;
    }

}
