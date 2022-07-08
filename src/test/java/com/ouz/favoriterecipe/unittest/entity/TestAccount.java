package com.ouz.favoriterecipe.unittest.entity;

import com.ouz.favoriterecipe.controller.dto.UserDTO;
import com.ouz.favoriterecipe.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author : OuZ
 * @date-time : 8.07.2022 - 01:58
 */
public class TestAccount
{

    private Account account;

    @BeforeEach
    void beforeEach(){
        account = new Account();
        account.setID(1L);
        account.setName("test");
        account.setLastname("lastname");
        account.setUsername("username");
    }

    @Test
    void testMapDtoToObject(){
        account.mapDTOtoObject(UserDTO.builder().username("username").name("name").lastname("lastname").build());
        assertEquals(account.getUsername(),"username");
        assertEquals(account.getName(),"name");
        assertEquals(account.getLastname(),"lastname");
    }
}
