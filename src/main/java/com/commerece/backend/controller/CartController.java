package com.commerece.backend.controller;

import com.commerece.backend.model.Cart;
import com.commerece.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Get the current user's cart
    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable String userId) {
        return cartService.getCartByUserId(userId);
    }

    // Add a product to the cart
    @PostMapping("/add/{userId}/{productId}")
    public void addProductToCart(@PathVariable String userId,
                                 @PathVariable String productId,
                                 @RequestBody ProductAddRequest productAddRequest) {
        cartService.addProductToCart(userId, productId, productAddRequest.getQuantity());
    }

    // Remove a product from the cart
    @DeleteMapping("/remove/{userId}/{productId}")
    public void removeProductFromCart(@PathVariable String userId,
                                      @PathVariable String productId) {
        cartService.removeProductFromCart(userId, productId);
    }

    // Get the total price of the user's cart
    @GetMapping("/total/{userId}")
    public double getTotalPrice(@PathVariable String userId) {
        return cartService.getTotalPrice(userId);
    }

    // Clear the cart for the user
    @DeleteMapping("/clear/{userId}")
    public void clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
    }
}
