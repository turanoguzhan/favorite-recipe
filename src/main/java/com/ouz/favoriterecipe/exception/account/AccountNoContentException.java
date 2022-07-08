package com.ouz.favoriterecipe.exception.account;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class AccountNoContentException extends AccountException implements Supplier<AccountNoContentException> {
    private static final long serialVersionUID = 1L;

    public AccountNoContentException() {
        super();
        this.message = "account.no.content";
        this.status = HttpStatus.NO_CONTENT;
    }

    @Override
    public AccountNoContentException get() {
        return this;
    }

}
