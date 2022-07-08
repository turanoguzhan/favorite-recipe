package com.ouz.favoriterecipe.unittest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ouz.favoriterecipe.controller.RecipeController;
import com.ouz.favoriterecipe.controller.dto.RecipeDTO;
import com.ouz.favoriterecipe.controller.dto.UserDTO;
import com.ouz.favoriterecipe.enums.DishType;
import com.ouz.favoriterecipe.service.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.net.URI;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RecipeController.class)
@ContextConfiguration(classes = RecipeController.class)
public class TestRecipeController
{

    @MockBean
    private RecipeServiceImpl recipeService;

    protected MockMvc mockMvc;

    private RecipeDTO recipeDTO;

    private UserDTO userDTO;

    private String requestJson;

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) throws JsonProcessingException
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        userDTO = UserDTO.builder()
                .username("test-username-dto")
                .name("test-name-dto")
                .lastname("test-lastname-dto")
                .build();

       recipeDTO = RecipeDTO.builder().name("test-name-dto")
                .servingNum(6)
                .ingredients("test-ingredients-dto")
                .instructions("test-instructions-dto")
                .dishType(DishType.LUNCH)
                .userDTO(userDTO)
               .build();

        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        requestJson = ow.writeValueAsString(recipeDTO);
    }

    @Test
    public void testAddRecipe() throws Exception
    {
        this.mockMvc.perform(post("/recipe",recipeDTO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        verify(recipeService).addRecipe(recipeDTO);
    }


    @Test
    public void testUpdateRecipe() throws Exception
    {

        this.mockMvc.perform(patch("/recipe",recipeDTO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        verify(recipeService).updateRecipe(recipeDTO);
    }

    @Test
    public void testDeleteRecipe() throws Exception
    {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        requestJson = ow.writeValueAsString(1L);

        this.mockMvc.perform(delete("/recipe",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        verify(recipeService).deleteRecipe(1L);
    }

    @Test
    public void testFetchRecipe() throws Exception
    {
        URI uri = URI.create("/recipe?vegetarian=true&servingNum=8");

        this.mockMvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk());

        verify(recipeService).fetchRecipes(true,8,null,null,null);
    }

}
