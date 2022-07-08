package com.ouz.favoriterecipe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author : OuZ
 * @date-time : 4.07.2022 - 00:43
 */
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="favorite_user_recipe")
public class FavoriteUserRecipe extends BaseEntity
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private Account user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recipe_id",referencedColumnName = "id")
    private Recipe recipe;

    @Column(name="is_private")
    private Boolean isPrivate;

    @Column(name="list_name")
    private String listName;
}
