package com.ibm.shoppingCart.productService.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.shoppingCart.productService.exceptions.InvalidRequestException;
import com.ibm.shoppingCart.productService.service.ProductService;
import com.ibm.shoppingCart.productService.ui.model.ProductModel;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private ProductService productService;

	@RequestMapping(method = RequestMethod.POST, value = "/send")
	public void sendProductMessage(@RequestBody @Valid ProductModel prod, BindingResult bindingResult) {
		if(!bindingResult.hasErrors()) {
			jmsTemplate.convertAndSend("ProductTransactionQueue", prod);
		} else {
			throw new InvalidRequestException(bindingResult.getFieldError().getDefaultMessage());
		}
		
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<ProductModel> fetchAllProducts() {
		return productService.fetchAllProducts();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/queueSize/{queueName}")
	public int getMessageCount(@PathVariable String queueName) {
		return productService.getQueueSize(queueName);
	}

}
