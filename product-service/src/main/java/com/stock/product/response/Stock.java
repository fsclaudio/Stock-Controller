package com.stock.product.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

	private Long productId;
	private double price;
	private double exitPrice;
	private int stockQuantity;

}

