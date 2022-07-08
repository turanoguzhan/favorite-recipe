package com.ouz.favoriterecipe.unittest.entity;

import com.ouz.favoriterecipe.controller.dto.RecipeDTO;
import com.ouz.favoriterecipe.controller.dto.UserDTO;
import com.ouz.favoriterecipe.entity.Account;
import com.ouz.favoriterecipe.entity.Recipe;
import com.ouz.favoriterecipe.enums.DishType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author : OuZ
 * @date-time : 8.07.2022 - 01:58
 */
public class TestRecipe
{
    private Recipe recipe;

    @BeforeEach
    void beforeEach(){
        recipe = new Recipe();
        recipe.setID(1L);
        recipe.setName("test");
        recipe.setDishType(DishType.BREAKFAST);
        recipe.setServingNum(8);
        recipe.setIngredients("test-ingredients");
        recipe.setInstructions("test-instructions");
        recipe.setUser(Account.builder().name("test").lastname("lastname").username("username").build());

    }

    @Test
    void testMapDtoToObject(){
        recipe.mapToRecipe(getRecipeDto());
        assertEquals(recipe.getName(),"recipe-dto");
        assertEquals(recipe.getDishType(),DishType.MAIN_COURSE);
        assertEquals(recipe.getServingNum(),6);
        assertEquals(recipe.getIngredients(),"ingredients-dto");
        assertEquals(recipe.getInstructions(),"instructions-dto");
    }

    @Test
    void testUpdateValues()
    {
        recipe.updateValues(getRecipeDto());
        assertEquals(recipe.getName(),"recipe-dto");
        assertEquals(recipe.getDishType(),DishType.MAIN_COURSE);
        assertEquals(recipe.getServingNum(),6);
        assertEquals(recipe.getIngredients(),"ingredients-dto");
        assertEquals(recipe.getInstructions(),"instructions-dto");
        assertEquals(recipe.getLast_modified_by(),"username-dto");
        assertEquals(recipe.getVersion(),2);
    }

    private RecipeDTO getRecipeDto(){
        return RecipeDTO.builder().name("recipe-dto")
                .dishType(DishType.MAIN_COURSE)
                .servingNum(6)
                .ingredients("ingredients-dto")
                .instructions("instructions-dto")
                .userDTO(UserDTO.builder().name("user-dto").username("username-dto").lastname("lastname-dto").build())
                .build();
    }
}
