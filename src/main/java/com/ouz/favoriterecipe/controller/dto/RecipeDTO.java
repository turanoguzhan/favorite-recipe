package com.ouz.favoriterecipe.controller.dto;

import com.ouz.favoriterecipe.enums.DishType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author : OuZ
 * @date-time : 4.07.2022 - 00:56
 */

@Data
@Builder
@AllArgsConstructor
public class RecipeDTO
{
    private Long id;
    private String name;
    private DishType dishType;
    private Integer servingNum;
    private String ingredients;
    private String instructions;
    private UserDTO userDTO;
}
