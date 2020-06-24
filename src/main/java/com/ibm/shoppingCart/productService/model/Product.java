package com.ibm.shoppingCart.productService.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String prodId;
	private String prodName;
	private String sellerId;
	private int purchasedQty;
	
	public Product() {}
	
	public Product(String prodId, String prodName, String sellerId, int purchasedQty) {
		super();
		this.prodId = prodId;
		this.prodName = prodName;
		this.sellerId =sellerId;
		this.purchasedQty = purchasedQty;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public int getPurchasedQty() {
		return purchasedQty;
	}

	public void setPurchasedQty(int purchasedQty) {
		this.purchasedQty = purchasedQty;
	}

	@Override
	public String toString() {
		return "Product : ID - " + this.id + " Product ID - " + this.prodId + " Product Name - " + 
				this.prodName + " Purchased Qunatity - " + this.purchasedQty + " Seller Id - " + this.sellerId;
	}
	
}
