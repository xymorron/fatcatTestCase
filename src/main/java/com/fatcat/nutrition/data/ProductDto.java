package com.fatcat.nutrition.data;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class ProductDto {

    @Length(min = 3)
    private String name;

    private String description;
    private int kcal;
}
