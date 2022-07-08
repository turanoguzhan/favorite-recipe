package com.ouz.favoriterecipe.controller.dto;

import lombok.*;

/**
 * @author : OuZ
 * @date-time : 5.07.2022 - 01:10
 */
@Data
@Builder
@AllArgsConstructor
public class UserDTO
{
    private Long id;
    private String name;
    private String lastname;
    private String username;
}
