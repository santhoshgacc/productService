package com.ibm.shoppingCart.productService.repository;

import org.springframework.data.repository.CrudRepository;

import com.ibm.shoppingCart.productService.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

}
