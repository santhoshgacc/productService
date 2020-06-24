package com.ibm.shoppingCart.productService.mapper;

import org.springframework.stereotype.Component;

import com.ibm.shoppingCart.productService.model.Product;
import com.ibm.shoppingCart.productService.ui.model.ProductModel;

@Component
public class ProductMapper {

	public Product getProductEntity(ProductModel productMdl) {

		return new Product(productMdl.getProdId(), productMdl.getProdName(), productMdl.getSellerId(),
				productMdl.getPurchasedQty());

	}

}
