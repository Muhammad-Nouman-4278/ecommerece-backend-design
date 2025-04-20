package com.commerece.backend.repository;


import com.commerece.backend.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {
    Cart findByUserId(String userId);  // Find cart by user ID
}
