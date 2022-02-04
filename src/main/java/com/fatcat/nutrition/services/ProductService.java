package com.fatcat.nutrition.services;

import com.fatcat.nutrition.NutritionApiWrongParameterException;
import com.fatcat.nutrition.data.Product;
import com.fatcat.nutrition.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final Logger logger = Logger.getLogger(ProductService.class.getName());

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll(Sort.by("name"));
    }

    public String addProduct(Product product) {
        String id = productRepository.insert(product).getId();
        logger.info("new product added: " + id);
        return id;
    }

    public Product getProductById(String productId) throws NutritionApiWrongParameterException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NutritionApiWrongParameterException("There is no product with id: " + productId));
    }
}
