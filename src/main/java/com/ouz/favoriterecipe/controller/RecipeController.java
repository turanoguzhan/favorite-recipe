package com.ouz.favoriterecipe.controller;

import com.ouz.favoriterecipe.controller.dto.RecipeDTO;
import com.ouz.favoriterecipe.controller.dto.RecipeSuccessMessageDTO;
import com.ouz.favoriterecipe.repository.Query;
import com.ouz.favoriterecipe.service.RecipeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author : OuZ
 * @date-time : 4.07.2022 - 00:55
 */
@RestController
@RequestMapping("/recipe")
public class RecipeController
{
    private final RecipeServiceImpl recipeService;

    public RecipeController(RecipeServiceImpl recipeService)
    {
        this.recipeService = recipeService;
    }

    @PostMapping
    @PreAuthorize(value="${ADMIN}")
    @Operation(description = "this method returns HttpStatus 200 with readable message when new recipe is added properly. " +
            "Otherwise throw related exception")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe's been added successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeSuccessMessageDTO.class)) }) })
    public ResponseEntity addRecipe(@Parameter(name="New recipe DTO",
            required = true,
            schema = @Schema(allOf = RecipeDTO.class),
            content = @Content(mediaType = "application/json")) @RequestBody RecipeDTO recipeDto){

        return recipeService.addRecipe(recipeDto);
    }

    @DeleteMapping
    @PreAuthorize(value="${ADMIN}")
    @Operation(description = "this method return ResponseEntity with 200 status code when delete operation succesfully completed."+
            "Otherwise mmeaningful status code and message return.")
    public ResponseEntity deleteRecipe(@Parameter(name="recipe Id that reference of wanted to deleted recipe",
            required = true,
            schema = @Schema(allOf = Long.class),
            content = @Content(mediaType = "application/json"))@RequestBody Long recipeId){
        return recipeService.deleteRecipe(recipeId);
    }

    @PatchMapping
    @PreAuthorize(value="${ADMIN}")
    @Operation(description = "this method return ResponseEntity with 200 status code when update operation succesfully completed."+
            "Otherwise mmeaningful status code and message will be return.")
    public ResponseEntity updateRecipe(@Parameter(name="recipe dto to update values existed recipe",
            required = true,
            schema = @Schema(allOf = RecipeDTO.class),
            content = @Content(mediaType = "application/json"))@RequestBody RecipeDTO recipeDto){

        return recipeService.updateRecipe(recipeDto);
    }

    @PreAuthorize(value="${ADMIN}")
    @Operation(description = "this method returns HttpStatus 200 with readable recipe datas " +
            "that is gathered by query params such IsVegetarian, servingNum, ingredients, instruction, ingredientFilterTpe ")
    @GetMapping
    public ResponseEntity filterRecipe(@Parameter(name="vegetarian",required = false,example="true") @RequestParam(required = false,name="vegetarian") Boolean vegetarian,
                                       @Parameter(name="servingNum",required = false,example="8") @RequestParam(required = false,name="servingNum") Integer servingNum,
                                       @Parameter(name="ingredients",required = false,example="eggs") @RequestParam(required = false,name="ingredients") String ingredients,
                                       @Parameter(name="ingredientsFilterType",required = false,example="INCLUDE") @RequestParam(required = false,name="ingredientsFilterType") String ingredientsFilterType,
                                       @Parameter(name="instruction",required = false,example="mix eggs with 2 couple milk") @RequestParam(required = false,name="instruction") String instruction){

        return recipeService.fetchRecipes(vegetarian, servingNum, ingredients, ingredientsFilterType, instruction);
    }
}
