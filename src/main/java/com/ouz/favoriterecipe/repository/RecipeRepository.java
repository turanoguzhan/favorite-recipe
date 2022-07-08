package com.ouz.favoriterecipe.repository;

import com.ouz.favoriterecipe.entity.Recipe;
import com.ouz.favoriterecipe.enums.DishType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * @author : OuZ
 * @date-time : 4.07.2022 - 01:08
 */
public interface RecipeRepository extends JpaRepository<Recipe,Long>, JpaSpecificationExecutor<Recipe>
{
    Recipe findByNameAndDishType(String name, DishType dishType);
}
