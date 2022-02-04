package com.fatcat.nutrition.repositories;

import com.fatcat.nutrition.data.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
