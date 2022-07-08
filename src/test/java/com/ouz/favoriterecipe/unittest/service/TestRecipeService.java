package com.ouz.favoriterecipe.unittest.service;

import com.ouz.favoriterecipe.controller.dto.RecipeDTO;
import com.ouz.favoriterecipe.controller.dto.UserDTO;
import com.ouz.favoriterecipe.entity.Account;
import com.ouz.favoriterecipe.entity.Recipe;
import com.ouz.favoriterecipe.enums.DishType;
import com.ouz.favoriterecipe.exception.account.AccountNotFoundException;
import com.ouz.favoriterecipe.exception.recipe.RecipeBadRequestException;
import com.ouz.favoriterecipe.repository.RecipeRepository;
import com.ouz.favoriterecipe.repository.UserRepository;
import com.ouz.favoriterecipe.service.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class TestRecipeService
{

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageSource messageSource;


    private Recipe recipe;

    private RecipeDTO recipeDTO;

    private Account user;

    private UserDTO userDTO;


    private ResponseEntity response;

    @BeforeEach
    void beforeEach(){

        user = Account.builder()
                .username("test-username")
                .name("test-name")
                .lastname("test-lastname")
                .build();

        recipe = Recipe.builder().name("test-name")
                .servingNum(6)
                .ingredients("test-ingredients")
                .instructions("test-instructions")
                .dishType(DishType.LUNCH)
                .user(user)
                .build();


        userDTO = UserDTO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .lastname(user.getLastname())
                .build();

        recipeDTO = RecipeDTO.builder()
                .name(recipe.getName())
                .servingNum(recipe.getServingNum())
                .ingredients(recipe.getIngredients())
                .instructions(recipe.getInstructions())
                .dishType(recipe.getDishType())
                .userDTO(userDTO).build();

        response = ResponseEntity.ok().build();
    }

    @Test
    void testAddRecipe(){

        // given
        recipeDTO.setId(10L);
        recipeDTO.getUserDTO().setId(1L);

        // when
        when(recipeRepository.findByNameAndDishType(recipeDTO.getName(),recipeDTO.getDishType())).thenReturn(null);
        when(userRepository.findById(recipeDTO.getUserDTO().getId())).thenReturn(Optional.ofNullable(user));
        when(messageSource.getMessage("operation.success.message",null,Locale.ENGLISH)).thenReturn("Operation successfully completed !");

        // then
        ResponseEntity responseService = recipeService.addRecipe(recipeDTO);

        assertEquals(HttpStatus.OK, responseService.getStatusCode());

        // verify
        verify(recipeRepository).findByNameAndDishType(recipeDTO.getName(),recipeDTO.getDishType());
        verify(userRepository).findById(recipeDTO.getUserDTO().getId());
        verify(messageSource).getMessage("operation.success.message",null,Locale.ENGLISH);

        verifyNoMoreInteractions(userRepository,messageSource);
    }

    @Test
    void testAddRecipe_RecipeBadRequestException(){

        ResponseEntity responseService = recipeService.addRecipe(null);

        assertEquals(HttpStatus.BAD_REQUEST, responseService.getStatusCode());

        verify(messageSource).getMessage("recipe.bad.request",null,Locale.ENGLISH);
    }

    @Test
    void testAddRecipe_RecipeNoContentException(){

        ResponseEntity responseService = recipeService.addRecipe(recipeDTO);

        assertEquals(HttpStatus.NO_CONTENT, responseService.getStatusCode());

        verify(messageSource).getMessage("recipe.no.content",null,Locale.ENGLISH);
    }

    @Test
    void testAddRecipe_RecipeConflictException(){

        // given
        recipeDTO.setId(10L);
        recipeDTO.getUserDTO().setId(1L);

        // when
        when(recipeRepository.findByNameAndDishType(recipeDTO.getName(),recipeDTO.getDishType())).thenReturn(recipe);

        // then
        ResponseEntity responseService = recipeService.addRecipe(recipeDTO);

        assertEquals(HttpStatus.CONFLICT, responseService.getStatusCode());

        // verify
        verify(recipeRepository).findByNameAndDishType(recipeDTO.getName(),recipeDTO.getDishType());
        verify(messageSource).getMessage("recipe.is.already.exists",null,Locale.ENGLISH);

        verifyNoMoreInteractions(recipeRepository,messageSource);

    }

    @Test
    void testAddRecipe_AccountNoContentException(){

        // given
        recipeDTO.setId(10L);
        recipeDTO.setUserDTO(null);

        // when
        when(recipeRepository.findByNameAndDishType(recipeDTO.getName(),recipeDTO.getDishType())).thenReturn(null);

        // then
        ResponseEntity responseService = recipeService.addRecipe(recipeDTO);

        assertEquals(HttpStatus.NO_CONTENT, responseService.getStatusCode());

        // verify
        verify(recipeRepository).findByNameAndDishType(recipeDTO.getName(),recipeDTO.getDishType());
        verify(messageSource).getMessage("account.no.content",null,Locale.ENGLISH);

        verifyNoMoreInteractions(recipeRepository,messageSource);

    }

    @Test
    void testAddRecipe_AccountNotFoundException(){

        // given
        recipeDTO.setId(10L);

        // when
        when(recipeRepository.findByNameAndDishType(recipeDTO.getName(),recipeDTO.getDishType())).thenReturn(null);

        // then
        ResponseEntity responseService = recipeService.addRecipe(recipeDTO);

        assertEquals(HttpStatus.NOT_FOUND, responseService.getStatusCode());

        // verify
        verify(recipeRepository).findByNameAndDishType(recipeDTO.getName(),recipeDTO.getDishType());
        verify(messageSource).getMessage("account.not.found",null,Locale.ENGLISH);

        verifyNoMoreInteractions(recipeRepository,messageSource);

    }

    @Test
    void testUpdateRecipe(){

        // given
        recipeDTO.setId(1L);
        recipeDTO.getUserDTO().setId(1L);

        // when
        when(recipeRepository.findById(recipeDTO.getId())).thenReturn(Optional.ofNullable(recipe));
        when(messageSource.getMessage("operation.success.message",null,Locale.ENGLISH)).thenReturn("Operation successfully completed !");

        // then
        ResponseEntity responseService = recipeService.updateRecipe(recipeDTO);

        assertEquals(HttpStatus.OK, responseService.getStatusCode());

        // verify
        verify(recipeRepository).findById(recipeDTO.getId());
        verify(messageSource).getMessage("operation.success.message",null,Locale.ENGLISH);

        verifyNoMoreInteractions(userRepository,messageSource);
    }

    @Test
    void testUpdateRecipe_RecipeBadRequestException(){

        // then
        ResponseEntity responseService = recipeService.updateRecipe(null);

        assertEquals(HttpStatus.BAD_REQUEST, responseService.getStatusCode());

        // verify
        verify(messageSource).getMessage("recipe.bad.request",null,Locale.ENGLISH);

        verifyNoMoreInteractions(messageSource);
    }

    @Test
    void testUpdateRecipe_RecipeNotFoundException(){

        recipeDTO.setId(10L);
        // then
        ResponseEntity responseService = recipeService.updateRecipe(recipeDTO);

        assertEquals(HttpStatus.NOT_FOUND, responseService.getStatusCode());

        // verify
        verify(messageSource).getMessage("recipe.not.found",null,Locale.ENGLISH);

        verifyNoMoreInteractions(messageSource);
    }

    @Test
    void testDeleteRecipe(){

        when(recipeRepository.findById(1L)).thenReturn(Optional.ofNullable(recipe));

        // then
        ResponseEntity responseService = recipeService.deleteRecipe(1L);

        assertEquals(HttpStatus.OK, responseService.getStatusCode());

        // verify
        verify(recipeRepository).findById(1L);
        verify(messageSource).getMessage("operation.success.message",null,Locale.ENGLISH);
    }

    @Test
    void testDeleteRecipe_RecipeNotFoundException(){


        // then
        ResponseEntity responseService = recipeService.deleteRecipe(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseService.getStatusCode());

        // verify
        verify(messageSource).getMessage("recipe.not.found",null,Locale.ENGLISH);
    }

}
