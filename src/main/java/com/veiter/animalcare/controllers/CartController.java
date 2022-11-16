package com.veiter.animalcare.controllers;

import com.veiter.animalcare.form.ItemForm;
import com.veiter.animalcare.models.AnimalInOrder;
import com.veiter.animalcare.models.Cart;
import com.veiter.animalcare.models.User;
import com.veiter.animalcare.repositories.AnimalInOrderRepository;
import com.veiter.animalcare.services.AnimalInOrderService;
import com.veiter.animalcare.services.AnimalService;
import com.veiter.animalcare.services.CartService;
import com.veiter.animalcare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @Autowired
    AnimalService animalService;
    @Autowired
    AnimalInOrderService animalInOrderService;
    @Autowired
    AnimalInOrderRepository animalInOrderRepository;

    @PostMapping("")
    public ResponseEntity<Cart> mergeCart(@RequestBody Collection<AnimalInOrder> animalInOrders, Principal principal) {
        User user = userService.findOne(principal.getName());
        try {
            cartService.mergeLocalCart(animalInOrders, user);
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Merge Cart Failed");
        }
        return ResponseEntity.ok(cartService.getCart(user));
    }

    @GetMapping("")
    public Cart getCart(Principal principal) {
        User user = userService.findOne(principal.getName());
        return cartService.getCart(user);
    }


    @PostMapping("/add")
    public boolean addToCart(@RequestBody ItemForm form, Principal principal) {
        var animalInfo = animalService.findOne(form.getAnimalId());
        try {
            mergeCart(Collections.singleton(new AnimalInOrder(animalInfo, form.getQuantity())), principal);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PutMapping("/{itemId}")
    public AnimalInOrder modifyItem(@PathVariable("itemId") String itemId, @RequestBody Integer quantity, Principal principal) {
        User user = userService.findOne(principal.getName());
        animalInOrderService.update(itemId, quantity, user);
        return animalInOrderService.findOne(itemId, user);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable("itemId") String itemId, Principal principal) {
        User user = userService.findOne(principal.getName());
        cartService.delete(itemId, user);
        // flush memory into DB
    }


    @PostMapping("/checkout")
    public ResponseEntity checkout(Principal principal) {
        User user = userService.findOne(principal.getName());// Email as username
        cartService.checkout(user);
        return ResponseEntity.ok(null);
    }
}
