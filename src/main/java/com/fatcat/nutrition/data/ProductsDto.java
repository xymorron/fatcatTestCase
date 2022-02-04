package com.fatcat.nutrition.data;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel(description = "view model for stored product entities")
public class ProductsDto {

    @ApiModelProperty(value = "total count of stored product entities", position = 1)
    private Integer count;

    @ApiModelProperty(value = "list of stored product entities", position = 2)
    private List<Product> products;

    public ProductsDto(List<Product> products) {
        this.products = products;
        count = products.size();
    }
}
