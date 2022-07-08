package com.ouz.favoriterecipe.repository.specification;

import com.ouz.favoriterecipe.entity.Recipe;
import com.ouz.favoriterecipe.repository.Query;
import org.springframework.data.jpa.domain.Specification;


import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

/**
 * @author : OuZ
 * @date-time : 7.07.2022 - 12:34
 */
public class RecipeSpecification
{

    public static Specification<Recipe> getSpecificationFromQueryParams(List<Query> queryList) {
        Specification<Recipe> specification = where(createSpecification(queryList.remove(0)));
        for(Query query : queryList){
            specification = specification.and(createSpecification(query));
        }
        return specification;
    }

    private static Specification<Recipe> createSpecification(Query input) {
        switch (input.getOperator()){
            case EQUALS:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(input.getField()),
                                castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case NOT_EQ:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.notEqual(root.get(input.getField()),
                                castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case GREATER_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.gt(root.get(input.getField()),
                                (Number) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case LESS_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.lt(root.get(input.getField()),
                                (Number) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case LIKE:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(input.getField()), "%"+input.getValue()+"%");
            default:
                throw new RuntimeException("Operation not supported yet");
        }
    }

    private static Object castToRequiredType(Class fieldType, String value) {
        if(fieldType.isAssignableFrom(String.class)){
            return String.valueOf(value);
        }else if(fieldType.isAssignableFrom(Integer.class)){
            return Integer.valueOf(value);
        }else if(fieldType.isAssignableFrom(Boolean.class)){
            return Boolean.valueOf(value);
        }else if(Enum.class.isAssignableFrom(fieldType)){
            return Enum.valueOf(fieldType, value);
        }
        return null;
    }
}
