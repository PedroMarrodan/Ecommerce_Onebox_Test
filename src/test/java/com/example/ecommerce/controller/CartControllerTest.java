package com.example.ecommerce.controller;

import com.example.ecommerce.error.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.ecommerce.model.Product;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.ecommerce.controller.CartController.cartsList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.example.ecommerce.model.Cart;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
class CartControllerTest {
    @InjectMocks
    private CartController cartController;
    @Mock
    private List<Cart> testCartsList;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProducts() {
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(1, "Airpods PRO",1));
        expectedProducts.add(new Product(2,"Nike",2));
        expectedProducts.add(new Product(3,"Ticket",1));

        List<Product> actualProducts = cartController.getProducts();

        assertEquals(expectedProducts.size(), actualProducts.size());

        List<Product> notExpectedProducts = new ArrayList<>();
        notExpectedProducts.add(new Product(1, "Airpods PRO",1));
        notExpectedProducts.add(new Product(2,"Nike",2));

        assertFalse(notExpectedProducts.size() == actualProducts.size(), "The quantity of products must not match");
    }

    @Test
    void createCart() {
        Cart cart = new Cart();
        Cart createdCart = cartController.createCart(cart);

        assertEquals(cart, createdCart);
        assertEquals(1, cartsList.size());
    }

    @Test
    void getCart() throws NotFoundException {
        Cart cart = new Cart();
        cart.setId("1");
        testCartsList.add(cart);

        cartController.createCart(cart);
        Cart retrievedCart = cartController.getCart(1);

        assertEquals(cart, retrievedCart);
    }

    @Test
    void addProductToCart() throws NotFoundException {
        Cart cart = new Cart();
        Product product = new Product(1, "Airpods PRO",1);
        cart.getProducts().add(product);
        testCartsList.add(cart);

        cartController.addProductToCart(1, 1);

        assertEquals(1, cart.getProducts().size());
    }

    @Test
    void deleteCart() throws NotFoundException {
        Cart cart1 = new Cart();
        Cart cart2 = new Cart();
        testCartsList = new ArrayList<>();
        testCartsList.add(cart1);
        testCartsList.add(cart2);

        int cartIdToDelete = 1;
        cartController.deleteCart(cartIdToDelete);
        testCartsList.remove(cart1);

        assertEquals(1, cartsList.size());
        assertFalse(cartsList.contains(cart1), "The deleted cart should not be in the list");
    }
}