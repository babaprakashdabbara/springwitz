package org.springwitz.statistics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springwitz.statistics.model.Product;
import org.springwitz.statistics.service.SalesStatisticsService;

@RestController
@RequestMapping(value = "/getStatistics")
public class SalesStatisticsController {

	private SalesStatisticsService salesStatisticsService;

	@Autowired
	public SalesStatisticsController(SalesStatisticsService salesStatisticsService) {
		this.salesStatisticsService = salesStatisticsService;
	}

	@GetMapping(value = "/{productName}")
	public ResponseEntity<Product> getProductSalesStatics(@PathVariable("productName") String productName) {
		return new ResponseEntity<>(salesStatisticsService.getSalesStatisticsOfProduct(productName), HttpStatus.OK);
	}

	@GetMapping(value = "/{productNames}")
	public ResponseEntity<List<Product>> getProductsSalesStatistics(@PathVariable("productNames") List<String> productNames) {
		return new ResponseEntity<>(salesStatisticsService.getSalesStatisticsOfProducts(productNames), HttpStatus.OK);
	}
}
