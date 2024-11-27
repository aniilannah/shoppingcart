package com.example.shoppingcart.service;

import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CartService {

    private final ProductRepository productRepository;
    private final double TAX_RATE = 22.0;

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String addProduct(Product product) {
        productRepository.save(product);
        return "Product added";
    }

    public List<Product> getCartItems() {
        return productRepository.findAll();
    }

    public String removeProduct(String name) {
        productRepository.deleteByName(name);

//        for (Product product : getCartItems()) {
//            if (product.getName().equals(name)) {
//                productRepository.delete(product);
//                return "Product removed successfully" + product;
//            }
//        }
//        Product product = productRepository.findByName(name);
//
//        if (product != null) {
//            productRepository.delete(product); // Remove the product
//            return "Product removed successfully: " + name;
//        } else {
//            return "Product not found: " + name;
//        }
        return "Product deleted";
    }

    public double calculateTax() {
        return 1 + TAX_RATE / 100;
    }

    public double calculateCartTotal() {
        double total = 0;
        for (Product product : productRepository.findAll()) {
            total += product.getPrice()*product.getQuantity();
        }
        return total * calculateTax();
    }

    public double applyDiscount (boolean hasMembership) {
        double discount = 0.10; // 10% discount
        if (hasMembership) { // Check if the user has a membership
            double discountAmount = calculateCartTotal() * discount; // Apply the discount to the cart total
            return calculateCartTotal() - discountAmount; // Return the new total after applying the discount
        }
        return calculateCartTotal();  // If the user does not have a membership, return the original cart total
    }
}

