package com.ouz.favoriterecipe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ouz.favoriterecipe.controller.dto.UserDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @author : OuZ
 * @date-time : 4.07.2022 - 00:35
 */
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(name="account")
public class Account extends BaseEntity
{
    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private char[] password;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = {CascadeType.MERGE,CascadeType.PERSIST},orphanRemoval = true)
    private Set<Recipe> recipeSet;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = {CascadeType.MERGE,CascadeType.PERSIST},orphanRemoval = true)
    private Set<FavoriteUserRecipe> favoriteUserRecipeSet;

    public void mapDTOtoObject(UserDTO dto){
        this.name = dto.getName();
        this.lastname = dto.getLastname();
        this.username = dto.getUsername();
    }

    @Override
    public String toString()
    {
        return "Account{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
