package com.ibm.shoppingCart.productService.messaging;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.shoppingCart.productService.mapper.ProductMapper;
import com.ibm.shoppingCart.productService.service.ProductService;
import com.ibm.shoppingCart.productService.ui.model.ProductModel;

@Component
@EnableJms
public class ProductQueueListener {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductMapper productMapper;
	
	@Transactional(rollbackFor = SQLException.class)
	@JmsListener(destination = "ProductTransactionQueue")
	public void processReceivedProduct(ProductModel prod) throws SQLException {
		System.out.println("Product Received : " + prod);
		productService.saveProduct(productMapper.getProductEntity(prod));
	}

}
