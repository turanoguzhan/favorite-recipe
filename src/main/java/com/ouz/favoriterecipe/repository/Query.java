package com.ouz.favoriterecipe.repository;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author : OuZ
 * @date-time : 7.07.2022 - 13:07
 */
@Getter
@Setter
@Builder
public class Query {
    private String field;
    private String value;
    private QueryOperator operator;
    private List<String> values;
}
