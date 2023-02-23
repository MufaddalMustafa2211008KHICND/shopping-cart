package com.mufaddal.shoppingcart;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class CartControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartRepository cartRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void postIntoCart() throws Exception {
        Cart record = Cart.builder()
                .id(1L)
                .name("slippers")
                .image("slippers.jpg")
                .quantity(40)
                .price(10L)
                .totalPrice(400L)
                .build();
        given(cartRepository.save(any(Cart.class))).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/api/shopping-cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(record)));
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(record.getName())))
                .andExpect(jsonPath("$.image", is(record.getImage())))
                .andExpect(jsonPath("$.quantity", is(record.getQuantity().intValue())))
                .andExpect(jsonPath("$.price", is(record.getPrice().intValue())))
                .andExpect(jsonPath("$.totalPrice", is(record.getTotalPrice().intValue())));
        // TODO figure how to make this work with LONGS!
    }

    @Test
    public void getAllTheThingsFromCart() throws Exception {
        Cart item1 = Cart.builder()
                .id(1L)
                .name("slippers")
                .image("slippers.jpg")
                .quantity(40)
                .price(10L)
                .totalPrice(400L)
                .build();
        Cart item2 = Cart.builder()
                .id(1L)
                .name("trousers")
                .image("trousers.jpg")
                .quantity(40)
                .price(100L)
                .totalPrice(4000L)
                .build();
        List<Cart> cart = List.of(item1, item2);
        given(cartRepository.findAll()).willReturn(cart);

        ResultActions response = mockMvc.perform(get("/api/shopping-cart"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(cart.size())));
    }

    @Test
    public void canDeleteAnItemFromCart() throws Exception{
        Long cartId = 1L;
        given(cartRepository.findById(cartId)).willReturn(Optional.of(new Cart()));
        willDoNothing().given(cartRepository).deleteById(cartId);

        ResultActions response = mockMvc.perform(delete("/api/shopping-cart/{id}", cartId));

        response.andExpect(status().isNoContent()).andDo(print());
        verify(cartRepository,times(1)).deleteById(cartId);
    }

    @Test
    public void cantDeleteAnItemFromCartWhenItDoesNotExist() throws Exception{
        Long cartId = 5L;
        given(cartRepository.findById(cartId)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(delete("/api/shopping-cart/{id}", cartId));

        response.andExpect(status().isNotFound()).andDo(print());
        verify(cartRepository,times(0)).deleteById(cartId);
    }

    @Test
    public void canDeleteAllItemsFromCart() throws Exception{
        willDoNothing().given(cartRepository).deleteAll();

        ResultActions response = mockMvc.perform(delete("/api/shopping-cart"));

        response.andExpect(status().isNoContent()).andDo(print());
        verify(cartRepository,times(1)).deleteAll();
    }
}
