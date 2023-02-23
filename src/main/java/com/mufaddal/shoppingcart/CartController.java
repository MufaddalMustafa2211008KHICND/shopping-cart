package com.mufaddal.shoppingcart;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin
@RestController
@RequestMapping("/api/shopping-cart")
public class CartController {
    
    @Autowired
    private CartRepository repository;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Cart saveToCart(@RequestBody Cart cart) {
        Optional<Cart> savedRecord = repository.findByName(cart.getName());

        if(savedRecord.isPresent()) {
            Cart record = savedRecord.get();
            record.setQuantity(record.getQuantity()+cart.getQuantity());
            record.setTotalPrice(record.getTotalPrice()+cart.getTotalPrice());
            return repository.save(record);
        }
        else{
            return repository.save(cart);
        }
        
    }

    @GetMapping("")
    public List<Cart> getCartData() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRecordFromCart(@PathVariable Long id) {
        Optional<Cart> record = repository.findById(id);
        if(record.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void emptyWholeCart() {
        repository.deleteAll();
    }
}
