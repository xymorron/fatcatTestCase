package com.fatcat.nutrition.repositories;

import com.fatcat.nutrition.data.ProductList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductListRepository extends MongoRepository<ProductList, String> {
}
