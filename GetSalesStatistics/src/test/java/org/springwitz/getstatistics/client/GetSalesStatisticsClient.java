package org.springwitz.getstatistics.client;

import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springwitz.statistics.model.Product;

public class GetSalesStatisticsClient {
	
	public void getSalesByProductName() {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    RestTemplate restTemplate = new RestTemplate();
	    String url = "http://localhost:8080/getStatistics/{productName}";
	    HttpEntity<Product> requestEntity = new HttpEntity<Product>(headers);
	    ResponseEntity<Product> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Product.class, "PROD");
	    Product product = responseEntity.getBody();
	    Assert.assertNotNull(product);
	  }
	
	public static void main(String args[]) {
	    GetSalesStatisticsClient getSalesStatisticsClient = new GetSalesStatisticsClient();
	    getSalesStatisticsClient.getSalesByProductName();
	  }
}
