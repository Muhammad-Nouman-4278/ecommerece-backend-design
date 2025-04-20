package com.commerece.backend.service;

import com.commerece.backend.model.Cart;
import com.commerece.backend.model.CartItem;
import com.commerece.backend.model.Product;
import com.commerece.backend.repository.CartRepository;
import com.commerece.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // Get cart by user ID
    public Cart getCartByUserId(String userId) {
        return cartRepository.findByUserId(userId);
    }

    // Add a product to the user's cart
    public void addProductToCart(String userId, String productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
        }

        // Create a CartItem with the specified quantity
        CartItem cartItem = new CartItem(productId, quantity);
        cart.getCartItems().add(cartItem);
        cartRepository.save(cart);
    }

    // Remove product from the cart
    public void removeProductFromCart(String userId, String productId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            cart.getCartItems().removeIf(item -> item.getProductId().equals(productId));
            cartRepository.save(cart);
        }
    }

    // Get total price of the user's cart
    public double getTotalPrice(String userId) {
        Cart cart = cartRepository.findByUserId(userId);
        double totalPrice = 0;
        if (cart != null) {
            for (CartItem item : cart.getCartItems()) {
                Product product = productRepository.findById(item.getProductId()).orElse(null);
                if (product != null) {
                    totalPrice += item.getTotalPrice(product.getPrice());
                }
            }
        }
        return totalPrice;
    }

    // Clear the cart for the user
    public void clearCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            cartRepository.delete(cart);
        }
    }
}
