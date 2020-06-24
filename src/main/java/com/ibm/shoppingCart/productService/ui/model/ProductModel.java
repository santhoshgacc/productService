package com.ibm.shoppingCart.productService.ui.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class ProductModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message="Product Id is Required")
	private String prodId;
	private String prodName;
	@NotBlank(message="Seller Id is Required")
	private String sellerId;
	//@NotBlank(message="Purchase Quantity is Required")
	private int purchasedQty;
	
	public ProductModel() {}
	
	public ProductModel(String prodId, String prodName, String sellerId, int purchasedQty) {
		super();
		this.prodId = prodId;
		this.prodName = prodName;
		this.sellerId =sellerId;
		this.purchasedQty = purchasedQty;
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
		return "Product : Product ID - " + this.prodId + " Product Name - " + 
				this.prodName + " Purchased Qunatity - " + this.purchasedQty + " Seller Id - " + this.sellerId;
	}
	
}
