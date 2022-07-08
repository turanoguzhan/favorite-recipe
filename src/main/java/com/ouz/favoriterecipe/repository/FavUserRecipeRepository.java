package com.ouz.favoriterecipe.repository;

import com.ouz.favoriterecipe.entity.FavoriteUserRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : OuZ
 * @date-time : 4.07.2022 - 01:08
 */
public interface FavUserRecipeRepository extends JpaRepository<FavoriteUserRecipe,Long>
{
}
