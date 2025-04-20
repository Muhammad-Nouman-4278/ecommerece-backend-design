package com.commerece.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "carts")
public class Cart {

    @Id
    private String id;

    private String userId;

    // Initialize cartItems as an empty list to avoid null pointer exception
    private List<CartItem> cartItems = new ArrayList<>();

    // Default constructor
    public Cart() {
    }

    // Constructor with all fields
    public Cart(String id, String userId, List<CartItem> cartItems) {
        this.id = id;
        this.userId = userId;
        this.cartItems = cartItems;
    }

    // Constructor with userId and an empty list of cartItems
    public Cart(String userId) {
        this.userId = userId;
        this.cartItems = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    // Method to add an item to the cart
    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }
}
