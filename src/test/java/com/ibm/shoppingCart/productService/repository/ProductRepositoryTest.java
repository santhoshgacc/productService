package com.ibm.shoppingCart.productService.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ibm.shoppingCart.productService.model.Product;

@DataJpaTest
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@Test
	public void dbObjectCreationCheck() {
		Product product = productRepository.save(new Product("P1", "Product Name -1", "S1", 10));
		assertThat(product).isNotNull();
	}

	@Test
	public void message_SaveCheck() {
		productRepository.save(new Product("P1", "Product Name -1", "S1", 10));
		productRepository.save(new Product("P2", "Product Name -2", "S2", 20));
		
		List<Product> products = new ArrayList<Product>();
		productRepository.findAll().forEach(products::add);
		
		assertEquals(2, products.size());		
	}

}
