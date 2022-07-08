package com.ouz.favoriterecipe.controller.dto;

import com.ouz.favoriterecipe.entity.Account;
import com.ouz.favoriterecipe.entity.Recipe;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class RecipeSuccessMessageDTO
{
    private Recipe recipe;
    private Account account;
    private LocalDateTime dateTime;
    private String message;

    public RecipeSuccessMessageDTO()
    {
        this.dateTime = LocalDateTime.now();
        this.message = "Operation is successfuly completed !";
    }

    @Override
    public String toString()
    {
        return "RecipeSuccessMessageDTO{" +
                ", recipe =" + recipe +
                ", account =" + account +
                ", date-time =" + dateTime +
                ", message ='" + message + '\'' +
                '}';
    }
}
