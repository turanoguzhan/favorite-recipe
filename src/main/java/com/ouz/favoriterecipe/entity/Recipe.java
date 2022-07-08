package com.ouz.favoriterecipe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ouz.favoriterecipe.controller.dto.RecipeDTO;
import com.ouz.favoriterecipe.enums.DishType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author : OuZ
 * @date-time : 4.07.2022 - 00:33
 */
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Builder
@Table(name="recipe")
public class Recipe extends BaseEntity
{
    @Column(name="name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="dish_type")
    private DishType dishType;

    @Column(name="serving_num")
    private Integer servingNum;

    @Column(name="ingredients")
    private String ingredients;

    @Column(name="instructions")
    private String instructions;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private Account user;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "recipe",cascade = {CascadeType.MERGE,CascadeType.PERSIST},orphanRemoval = true)
    private Set<FavoriteUserRecipe> favoriteUserRecipeSet;


    public void mapToRecipe(RecipeDTO dto){
        this.name = dto.getName();
        this.dishType = dto.getDishType();
        this.servingNum = dto.getServingNum();
        this.ingredients = dto.getIngredients();
        this.instructions = dto.getInstructions();
        this.user = new Account();
        this.user.mapDTOtoObject(dto.getUserDTO());
        this.created_by = this.user.getUsername();
        this.created_date = LocalDateTime.now();
        this.version = 1;
    }

    public void updateValues(RecipeDTO dto)
    {
        mapToRecipe(dto);
        this.last_modified_date = LocalDateTime.now();
        this.last_modified_by = dto.getUserDTO().getUsername();
        this.version++;
    }

    @Override
    public String toString()
    {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", dishType=" + dishType +
                ", servingNum=" + servingNum +
                ", ingredients='" + ingredients + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}
