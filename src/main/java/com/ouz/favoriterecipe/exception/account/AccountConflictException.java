package com.ouz.favoriterecipe.exception.account;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class AccountConflictException extends AccountException implements Supplier<AccountConflictException> {
    private static final long serialVersionUID = 1L;

    public AccountConflictException() {
        super();
        this.message = "account.is.already.exists";
        this.status = HttpStatus.CONFLICT;
    }

    @Override
    public AccountConflictException get() {
        return this;
    }

}
