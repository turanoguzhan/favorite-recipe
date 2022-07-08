package com.ouz.favoriterecipe.service;

import com.ouz.favoriterecipe.controller.dto.RecipeDTO;
import com.ouz.favoriterecipe.controller.dto.RecipeSuccessMessageDTO;
import com.ouz.favoriterecipe.entity.Recipe;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author : OuZ
 * @date-time : 5.07.2022 - 00:36
 */
public interface RecipeService
{
    ResponseEntity<List<RecipeSuccessMessageDTO>> fetchRecipes(Boolean vegetarian, Integer servingNum, String ingredients, String ingredientFilterType, String instruction);

    ResponseEntity addRecipe(RecipeDTO recipeDto);

    ResponseEntity updateRecipe(RecipeDTO recipeDTO);

    ResponseEntity deleteRecipe(Long recipeId);

}
