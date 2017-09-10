package org.springwitz.statistics.service;

import java.util.List;

import org.springwitz.statistics.model.Product;

public interface SalesStatisticsService {

	Product getSalesStatisticsOfProduct(String productName);

	List<Product> getSalesStatisticsOfProducts(List<String> productNames);

}
