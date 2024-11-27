package com.example.shoppingcart.controller;

import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cart")
@CrossOrigin(origins = "*") // * kõik portid, mis käimas on, kui teab täpselt, siis paned selle nr
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add-product")
    public String addProduct(@RequestBody Product product) {
        return cartService.addProduct(product);
    }

    @GetMapping("/get-items")
    public List<Product> getCartItems() {
        return cartService.getCartItems();
    }

    @DeleteMapping("/remove-product/{name}")
    public String removeProduct(@PathVariable String name) {
        return cartService.removeProduct(name);
    }

    @GetMapping("/cart-total")
    public double calculateCartTotal() {
        return cartService.calculateCartTotal();
    }

    @PostMapping("/tax/{name}")
    public double calculateTax() {
        return cartService.calculateTax();
    }

    @PostMapping("/apply-discount")
    public ResponseEntity<Double> applyDiscount
            (@RequestParam boolean hasMembership) {
        double discountedTotal = cartService.applyDiscount(hasMembership);
        return ResponseEntity.ok(discountedTotal);
    }
}
