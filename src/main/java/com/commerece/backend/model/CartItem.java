package com.commerece.backend.model;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class CartItem {

    private String productId;  // The product ID
    private int quantity;      // Quantity of the product in the cart

    public double getTotalPrice(double productPrice) {
        return productPrice * this.quantity; // Calculates the total price for this product
    }


    public CartItem() {
    }

    public CartItem(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
