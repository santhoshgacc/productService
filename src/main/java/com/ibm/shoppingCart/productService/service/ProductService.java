package com.ibm.shoppingCart.productService.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.ibm.shoppingCart.productService.messaging.ProductDLQProcessor;
import com.ibm.shoppingCart.productService.model.Product;
import com.ibm.shoppingCart.productService.repository.ProductRepository;
import com.ibm.shoppingCart.productService.ui.model.ProductModel;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private JmsTemplate jmsTemplate;

	private static boolean isDBUpInd = true;

	public Product saveProduct(Product prod) throws SQLException {

		try {
			Product product = productRepository.save(prod);
			if (!isDBUpInd) {
				isDBUpInd = true;
				new Thread(ProductDLQProcessor.getInstance()).start();
			}

			return product;

		} catch (Exception ex) {
			isDBUpInd = false;
			throw new SQLException();
		}

	}

	public List<ProductModel> fetchAllProducts() {
		List<ProductModel> products = new ArrayList<ProductModel>();
		productRepository.findAll().forEach(o -> {
			products.add(new ProductModel(o.getProdId(), o.getProdName(), o.getSellerId(), o.getPurchasedQty()));
		});
		return products;
	}

	public Integer getQueueSize(String queueName) {
		Integer totalPendingMessages = this.jmsTemplate.browse(queueName,
				(session, browser) -> Collections.list(browser.getEnumeration()).size());
		return totalPendingMessages == null ? 0 : totalPendingMessages;
	}

}
