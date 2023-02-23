package com.mufaddal.shoppingcart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShoppingCartApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void getAndSetCartId(){
		Cart cut = new Cart();
		Long myId = 1L;
		cut.setId(myId);
		assertEquals(myId, cut.getId());
	}

	@Test
	void getAndSetName(){
		Cart cut = new Cart();
		String myName = "golden toilet";
		cut.setName(myName);
		assertEquals(myName, cut.getName());
	}

	@Test
	void getAndSetImage(){
		Cart cut = new Cart();
		String myImage = "golden_toilet.png";
		cut.setImage(myImage);
		assertEquals(myImage, cut.getImage());
	}

	@Test
	void getAndSetQuantity(){
		Cart cut = new Cart();
		Integer myQuantity = 10;
		cut.setQuantity(myQuantity);
		assertEquals(myQuantity, cut.getQuantity());
	}

	@Test
	void getAndSetPrice(){
		Cart cut = new Cart();
		Long myPrice = 60004345L;
		cut.setPrice(myPrice);
		assertEquals(myPrice, cut.getPrice());
	}

	@Test
	void getAndSetTotalPrice(){
		Cart cut = new Cart();
		Long myTotalPrice = 83542503005L;
		cut.setTotalPrice(myTotalPrice);
		assertEquals(myTotalPrice, cut.getTotalPrice());
	}

	@Test
	void allArgsConstructorCart(){
		Long myId = 1L;
		String myName = "fish tank";
		String myImage = "TankofFish.jpg";
		Integer myQuantity = 5;
		Long myPrice = 100L;
		Long myTotalPrice = 500L;
		Cart cut = new Cart(myId, myName, myImage, myQuantity, myPrice, myTotalPrice);
		assertEquals(myId, cut.getId());
		assertEquals(myName, cut.getName());
		assertEquals(myImage, cut.getImage());
		assertEquals(myQuantity, cut.getQuantity());
		assertEquals(myPrice, cut.getPrice());
		assertEquals(myTotalPrice, cut.getTotalPrice());
	}

	@Test
	void builderCart(){
		Long myId = 1L;
		String myName = "table";
		String myImage = "table.png";
		Integer myQuantity = 10;
		Long myPrice = 70L;
		Long myTotalPrice = 700L;
		Cart cut = Cart.builder()
							.id(myId)
							.name(myName)
							.image(myImage)
							.quantity(myQuantity)
							.price(myPrice)
							.totalPrice(myTotalPrice)
							.build();
		assertEquals(myId, cut.getId());
		assertEquals(myName, cut.getName());
		assertEquals(myImage, cut.getImage());
		assertEquals(myQuantity, cut.getQuantity());
		assertEquals(myPrice, cut.getPrice());
		assertEquals(myTotalPrice, cut.getTotalPrice());
	}

}
