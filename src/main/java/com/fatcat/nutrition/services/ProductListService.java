package com.fatcat.nutrition.services;

import com.fatcat.nutrition.NutritionApiWrongParameterException;
import com.fatcat.nutrition.data.Product;
import com.fatcat.nutrition.data.ProductList;
import com.fatcat.nutrition.repositories.ProductListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;


@Service
public class ProductListService {

    private final ProductListRepository productListRepository;
    private final Logger logger = Logger.getLogger(ProductListService.class.getName());


    @Autowired
    public ProductListService(ProductListRepository productListRepository) {
        this.productListRepository = productListRepository;
    }

    public String addNewList(String productListName) {
        String id = productListRepository.insert(new ProductList(productListName)).getId();
        logger.info("new productLIst added: " + id);
        return id;
    }

    public ProductList getProductListById(String productListId) throws NutritionApiWrongParameterException {
        return productListRepository.findById(productListId)
                .orElseThrow(() -> new NutritionApiWrongParameterException("There is no product with id: " + productListId));
    }

    public void addProductToProductList(String productListId, Product product) throws NutritionApiWrongParameterException {
        ProductList productList = getProductListById(productListId);
        productList.addProduct(product);
        productListRepository.save(productList);
    }
}
