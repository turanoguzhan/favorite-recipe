package com.ouz.favoriterecipe.service;

import com.ouz.favoriterecipe.controller.dto.RecipeDTO;
import com.ouz.favoriterecipe.controller.dto.RecipeSuccessMessageDTO;
import com.ouz.favoriterecipe.entity.Account;
import com.ouz.favoriterecipe.entity.Recipe;
import com.ouz.favoriterecipe.enums.DishType;
import com.ouz.favoriterecipe.exception.account.AccountNoContentException;
import com.ouz.favoriterecipe.exception.account.AccountNotFoundException;
import com.ouz.favoriterecipe.exception.recipe.RecipeBadRequestException;
import com.ouz.favoriterecipe.exception.recipe.RecipeConflictException;
import com.ouz.favoriterecipe.exception.recipe.RecipeNoContentException;
import com.ouz.favoriterecipe.exception.recipe.RecipeNotFoundException;
import com.ouz.favoriterecipe.repository.Query;
import com.ouz.favoriterecipe.repository.QueryOperator;
import com.ouz.favoriterecipe.repository.RecipeRepository;
import com.ouz.favoriterecipe.repository.UserRepository;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.ouz.favoriterecipe.repository.specification.RecipeSpecification.getSpecificationFromQueryParams;

/**
 * @author : OuZ
 * @date-time : 5.07.2022 - 00:29
 */
@Service
public class RecipeServiceImpl implements RecipeService
{

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository, MessageSource messageSource)
    {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public ResponseEntity<List<RecipeSuccessMessageDTO>> fetchRecipes(Boolean vegetarian, Integer servingNum, String ingredients, String ingredientFilterType, String instruction)
    {
        List<Recipe> recipeList = recipeRepository.findAll(
                getSpecificationFromQueryParams(
                        getQueryListByParams(vegetarian,servingNum, ingredients, ingredientFilterType, instruction)
                ));

        List<RecipeSuccessMessageDTO> successMessageDTOList = new ArrayList<>();
        recipeList.forEach(t-> successMessageDTOList.add(
                RecipeSuccessMessageDTO.builder()
                        .message(messageSource.getMessage("operation.success.message",null, Locale.ENGLISH))
                        .dateTime(LocalDateTime.now())
                        .recipe(t)
                        .build()
        ) );

        return ResponseEntity.ok(successMessageDTOList);
    }

    @Override
    public ResponseEntity<RecipeSuccessMessageDTO> addRecipe(RecipeDTO recipeDto)
    {
        try
        {
            checkRecipeDTOIsNull(recipeDto);

            if (Objects.isNull(recipeDto.getId()))
            {
                throw new RecipeNoContentException();
            }
            Recipe recipeDb = recipeRepository.findByNameAndDishType(recipeDto.getName(), recipeDto.getDishType());
            if (!Objects.isNull(recipeDb))
            {
                throw new RecipeConflictException();
            }

            checkUserAccount(recipeDto);

            Recipe recipe = Recipe.builder().build();
            recipe.mapToRecipe(recipeDto);

            recipeRepository.save(recipe);

            return ResponseEntity.ok(RecipeSuccessMessageDTO.builder()
                    .message(messageSource.getMessage("operation.success.message",null,Locale.ENGLISH))
                    .dateTime(LocalDateTime.now())
                    .recipe(recipe)
                    .build());

        }catch(RecipeBadRequestException rbx){
            return ResponseEntity.status(rbx.getStatus()).body(RecipeSuccessMessageDTO.builder()
                    .message(messageSource.getMessage("recipe.bad.request",null,Locale.ENGLISH))
                    .dateTime(LocalDateTime.now())
                    .build());
        }catch(RecipeNoContentException rncx){
            return ResponseEntity.status(rncx.getStatus()).body(RecipeSuccessMessageDTO.builder()
                    .message(messageSource.getMessage("recipe.no.content",null,Locale.ENGLISH))
                    .dateTime(LocalDateTime.now())
                    .recipe(Recipe.builder()
                            .name(recipeDto.getName())
                            .dishType(recipeDto.getDishType())
                            .servingNum(recipeDto.getServingNum())
                            .build())
                    .build());
        }catch(RecipeConflictException rcx){
            return ResponseEntity.status(rcx.getStatus()).body(RecipeSuccessMessageDTO.builder()
                    .message(messageSource.getMessage("recipe.is.already.exists",null,Locale.ENGLISH))
                    .dateTime(LocalDateTime.now())
                    .recipe(Recipe.builder()
                            .name(recipeDto.getName())
                            .dishType(recipeDto.getDishType())
                            .servingNum(recipeDto.getServingNum())
                            .build())
                    .build());
        }catch(AccountNoContentException ancx){
            return ResponseEntity.status(ancx.getStatus()).body(RecipeSuccessMessageDTO.builder()
                    .message(messageSource.getMessage("account.no.content",null,Locale.ENGLISH))
                    .dateTime(LocalDateTime.now())
                    .recipe(Recipe.builder()
                            .name(recipeDto.getName())
                            .dishType(recipeDto.getDishType())
                            .servingNum(recipeDto.getServingNum())
                            .build())
                    .build());
        }catch(AccountNotFoundException anfx){
            return ResponseEntity.status(anfx.getStatus()).body(RecipeSuccessMessageDTO.builder()
                    .message(messageSource.getMessage("account.not.found",null,Locale.ENGLISH))
                    .dateTime(LocalDateTime.now())
                    .account(Account.builder().name(recipeDto.getUserDTO().getName())
                            .lastname(recipeDto.getUserDTO().getLastname())
                            .username(recipeDto.getUserDTO().getUsername())
                            .build())
                    .build());
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RecipeSuccessMessageDTO.builder()
                    .message(messageSource.getMessage("uncategorized.error"+ex.getLocalizedMessage(),null,Locale.ENGLISH))
                    .dateTime(LocalDateTime.now())
                    .build());
        }


    }

    @Override
    public ResponseEntity updateRecipe(RecipeDTO recipeDto)
    {
        try
        {
            checkRecipeDTOIsNull(recipeDto);
            Recipe recipeDb = recipeRepository.findById(recipeDto.getId()).orElseThrow(new RecipeNotFoundException());

            recipeDb.updateValues(recipeDto);

            recipeRepository.save(recipeDb);

            return ResponseEntity.ok(RecipeSuccessMessageDTO.builder()
                    .message(messageSource.getMessage("operation.success.message",null,Locale.ENGLISH))
                    .dateTime(LocalDateTime.now())
                    .recipe(recipeDb)
                    .build());

        }catch(RecipeBadRequestException rbx){
            return ResponseEntity.status(rbx.getStatus()).body(RecipeSuccessMessageDTO.builder()
                    .message(messageSource.getMessage("recipe.bad.request",null,Locale.ENGLISH))
                    .dateTime(LocalDateTime.now())
                    .build());
        }catch(RecipeNotFoundException rnfx){
            return ResponseEntity.status(rnfx.getStatus()).body(RecipeSuccessMessageDTO.builder()
                    .message(messageSource.getMessage("recipe.not.found",null,Locale.ENGLISH))
                    .dateTime(LocalDateTime.now())
                    .recipe(Recipe.builder()
                            .name(recipeDto.getName())
                            .dishType(recipeDto.getDishType())
                            .servingNum(recipeDto.getServingNum())
                            .build())
                    .build());
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RecipeSuccessMessageDTO.builder()
                    .message(messageSource.getMessage("uncategorized.error"+ex.getLocalizedMessage(),null,Locale.ENGLISH))
                    .dateTime(LocalDateTime.now())
                    .build());
        }
    }

    @Override
    public ResponseEntity deleteRecipe(Long recipeId)
    {
        try
        {
            Recipe recipeDb = recipeRepository.findById(recipeId).orElseThrow(new RecipeNotFoundException());

            recipeRepository.delete(recipeDb);
            return ResponseEntity.ok(new RecipeSuccessMessageDTO(recipeDb, recipeDb.getUser(), LocalDateTime.now(),
                    messageSource.getMessage("operation.success.message", null, Locale.ENGLISH)));
        }catch(RecipeNotFoundException rnfx){
            return ResponseEntity.status(rnfx.getStatus()).body(RecipeSuccessMessageDTO.builder()
                    .message(messageSource.getMessage("recipe.not.found",null,Locale.ENGLISH)+" Recipe Id : "+recipeId)
                    .dateTime(LocalDateTime.now())
                    .build());
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RecipeSuccessMessageDTO.builder()
                    .message(messageSource.getMessage("uncategorized.error"+ex.getLocalizedMessage(),null,Locale.ENGLISH))
                    .dateTime(LocalDateTime.now())
                    .build());
        }
    }

    private void checkUserAccount(RecipeDTO dto){

        if(Objects.isNull(dto.getUserDTO())){
            throw new AccountNoContentException();
        }else if(Objects.isNull(dto.getUserDTO().getId())){
            throw new AccountNotFoundException();
        }
        userRepository.findById(dto.getUserDTO().getId()).orElseThrow(new AccountNotFoundException());
    }

    private void checkRecipeDTOIsNull(RecipeDTO dto){
        if(Objects.isNull(dto)){
            throw new RecipeBadRequestException();
        }
    }

    private List<Query> getQueryListByParams(Boolean vegetarian, Integer servingNum, String ingredients, String ingredientFilterType, String instruction)
    {
        List<Query> queryList = new ArrayList<>();

        if(!Objects.isNull(vegetarian) && vegetarian){
            queryList.add(Query.builder()
                    .field("dishType")
                    .operator(QueryOperator.EQUALS)
                    .value(DishType.VEGETARIAN.value)
                    .build());
        }
        if(!Objects.isNull(servingNum))
        {
            queryList.add(Query.builder()
                    .field("servingNum")
                    .operator(QueryOperator.EQUALS)
                    .value(String.valueOf(servingNum))
                    .build());
        }

        if(!Objects.isNull(ingredientFilterType) && "INCLUDE".equalsIgnoreCase(ingredientFilterType)){
            queryList.add(Query.builder()
                    .field("ingredients")
                    .operator(QueryOperator.LIKE)
                    .value(ingredients)
                    .build());
        }else if(!Objects.isNull(ingredientFilterType) && "EXCLUDE".equalsIgnoreCase(ingredientFilterType)){
            queryList.add(Query.builder()
                    .field("ingredients")
                    .operator(QueryOperator.NOT_LIKE)
                    .value(ingredients)
                    .build());
        }

        if(!Objects.isNull(instruction))
        {
            queryList.add(Query.builder()
                    .field("instructions")
                    .operator(QueryOperator.LIKE)
                    .value(instruction)
                    .build());
        }

        return queryList;
    }
}
