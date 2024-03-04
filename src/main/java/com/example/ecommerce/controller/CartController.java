package com.example.ecommerce.controller;

import com.example.ecommerce.error.NotFoundException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartController {
    public static List<Cart> cartsList = new ArrayList<>();
    public static List<Product> productsList = new ArrayList<>();

    @PostConstruct
    public void initializeProducts() {
        // Add some predefined products
        productsList.add(new Product(1, "Airpods PRO",1));
        productsList.add(new Product(2,"Nike",2));
        productsList.add(new Product(3,"Ticket",1));
    }

    @GetMapping("ecommerce/products")
    public static List<Product> getProducts(){
        return productsList;
    }

    @PostMapping("ecommerce/cart")
    public static Cart createCart(@RequestBody Cart cart){
        cartsList.add(cart);
        return cart;
    }

    @GetMapping("ecommerce/cart/{id}")
    public static Cart getCart(@PathVariable("id") int id) throws NotFoundException {
        for(Cart cart : cartsList){
            if (Integer.parseInt(cart.getId()) == id){
                return cart;
            } else {
                throw new NotFoundException("Cart is not available");
            }
        }
        throw new NotFoundException("Cart is not available");
    }
    @PutMapping("ecommerce/cart/{cartId}/product/{productId}")
    public static Cart addProductToCart(@PathVariable("cartId") int cartId, @PathVariable("productId") int productId) throws NotFoundException {
        for(Cart cart : cartsList){
            if (Integer.parseInt(cart.getId()) == cartId){
                for(Product product : productsList){
                    if(product.getId() == productId) {
                        cart.getProducts().add(productsList.get(productId - 1));
                        return cart;
                    }else if (productId > productsList.size()){
                        throw new NotFoundException("Product is not available");
                    }
                }
            } else if (!(Integer.parseInt(cart.getId()) == cartId)){
                throw new NotFoundException("Cart is not available");
            }
        }
        return null;
    }

    @DeleteMapping("ecommerce/cart/{id}")
    public static void deleteCart(@PathVariable("id") int id) throws NotFoundException {
        if (id > 0 && id <= cartsList.size()) {
            cartsList.remove(id-1);
            System.out.println("Cart deleted.");
        } else {
            throw new NotFoundException("Cart is not available");
        }
    }

    @Scheduled(fixedRate = 600000)
    public void deleteInactiveCarts() {
        long currentTime = System.currentTimeMillis();
        for(Cart cart : cartsList) {
            long lastAccessedTime = cart.getLastAccessed();
            long inactiveDuration = currentTime - lastAccessedTime;
            if (inactiveDuration >= 10 * 60 * 1000) {
                cartsList.remove(cart.getId());
                System.out.println("Cart " + cart.getId() + " deleted due to inactivity.");
            }
        }
    }
}