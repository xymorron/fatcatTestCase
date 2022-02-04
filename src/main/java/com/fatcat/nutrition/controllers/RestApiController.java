package com.fatcat.nutrition.controllers;


import com.fatcat.nutrition.NutritionApiWrongParameterException;
import com.fatcat.nutrition.data.*;
import com.fatcat.nutrition.services.ProductListService;
import com.fatcat.nutrition.services.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@Api
public class RestApiController {

    private final Logger logger = Logger.getLogger(RestApiController.class.getName());
    private final ProductService productService;
    private final ProductListService productListService;

    @Autowired
    public RestApiController(ProductService productService, ProductListService productListService) {
        this.productService = productService;
        this.productListService = productListService;
    }

    @ApiOperation("method to add new product")
    @PostMapping("/product/add")
    public ResponseEntity<ApiResponseStatus> addProduct(@Valid ProductDto productDto) {
        String newId = productService.addProduct(new Product(productDto));
        ApiResponseStatus responseStatus = new ApiResponseStatus();
        responseStatus.setResult(true);
        responseStatus.setMessage("new product added: " + newId);
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);
    }

    @ApiOperation("method to add new productList")
    @PostMapping("/list/add")
    public ResponseEntity<ApiResponseStatus> addList(@RequestParam(name = "name") @Length(min = 3) String productListName) {
        String newId = productListService.addNewList(productListName);
        logger.info("list add request");
        ApiResponseStatus responseStatus = new ApiResponseStatus();
        responseStatus.setResult(true);
        responseStatus.setMessage("new product list added: " + newId);
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);
    }

    @ApiOperation("method to add product to product list")
    @PostMapping("/list/addProduct")
    public ResponseEntity<ApiResponseStatus> addProductToList(@RequestParam(name = "listId") String productListId,
                                                              @RequestParam(name = "productId") String productId) throws NutritionApiWrongParameterException {
        Product product = productService.getProductById(productId);
        productListService.addProductToProductList(productListId, product);
        logger.info("product to list add request");
        ApiResponseStatus responseStatus = new ApiResponseStatus();
        responseStatus.setResult(true);
        responseStatus.setMessage("product added to list");
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);
    }

    @ApiOperation("method to get all available products")
    @GetMapping("/product")
    public ProductsDto getProducts() {
        logger.info("products get request");
        return new ProductsDto(productService.getProducts());
    }

    @ApiOperation("method to get productList with it products")
    @GetMapping("/list")
    public ProductList getProductList(@RequestParam(name = "listId") String productListId) throws NutritionApiWrongParameterException {
        ProductList productList = productListService.getProductListById(productListId);
        logger.info("product list get request");
        return productList;
    }

    @ExceptionHandler(NutritionApiWrongParameterException.class)
    public ResponseEntity<ApiResponseStatus> handlerNutritionApiWrongParameterException(Exception exception) {
        return new ResponseEntity<>(
                new ApiResponseStatus(false,"Bad parameter value...", exception.getCause()),
                HttpStatus.OK);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponseStatus> handlerMissingServletRequestParameterException(Exception exception) {
        return new ResponseEntity<>(
                new ApiResponseStatus(false,"Missing required parameter", exception),
                HttpStatus.OK);
    }

}
