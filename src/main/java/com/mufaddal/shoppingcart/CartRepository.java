package com.mufaddal.shoppingcart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
    public Optional<Cart> findByName(String name);
}
