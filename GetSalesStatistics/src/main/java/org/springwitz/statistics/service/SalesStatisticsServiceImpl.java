package org.springwitz.statistics.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springwitz.statistics.model.Product;
import org.springwitz.statistics.model.WeekSales;

@Component
public class SalesStatisticsServiceImpl implements SalesStatisticsService {

	private static final Logger logger = LoggerFactory.getLogger(SalesStatisticsServiceImpl.class);

	private static final String STATISTICS_FILE_LOCATION = "c://springwitz//";
	private static final String PRODUCT_FILE_NAME = "product.csv";
	private static final String WEEK_SALES_FILE_NAME = "weeksales.csv";
	private static final String SEPERATOR = ",";

	@Override
	public Product getSalesStatisticsOfProduct(String productName) {
		try {
			List<Product> productsAndTargetSales = getListOfProductsAndTargetSales();
			return productsAndTargetSales.stream().filter(product -> productName.equalsIgnoreCase(product.getName())).findFirst().get();
		} catch (Exception e) {
			logger.error("Error While Constructing Sales Statistics Of Given Product Name"+ productName);
		}
		return null;
	}

	@Override
	public List<Product> getSalesStatisticsOfProducts(List<String> productNames) {
		List<Product> products  = new ArrayList<>();
		productNames.forEach(productName -> {
			Product product = getSalesStatisticsOfProduct(productName);
			products.add(product);
		});
		return products;
	}

	private List<Product> getListOfProductsAndTargetSales() throws Exception {
		List<Product> productsAndTargetSales = getProductsAndTargetSales();
		Map<String, List<WeekSales>> productWeekSales = getProductWeekSales();

		List<Product> productsWithWeekSalesList = new ArrayList<>();
		productsAndTargetSales.forEach(product -> {
			Product productWithWeekSales = new Product(product.getName(), product.getSalesTarget(),
					productWeekSales.get(product.getName()));
			productsWithWeekSalesList.add(productWithWeekSales);
		});
		return productsWithWeekSalesList;
	}

	private List<Product> getProductsAndTargetSales() throws Exception {
		List<Product> products = new ArrayList<>();

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(STATISTICS_FILE_LOCATION + PRODUCT_FILE_NAME))))) {
			bufferedReader.lines().skip(1).map(mapToProduct).collect(Collectors.toList());
		} catch (FileNotFoundException e) {
			logger.error("Error while reading CSV File" + PRODUCT_FILE_NAME);
			throw e;
		} catch (IOException e) {
			logger.error("Error while reading CSV File" + PRODUCT_FILE_NAME);
			throw e;
		}
		return products;
	}

	private Function<String, Product> mapToProduct = (line) -> {
		String[] lines = line.split(SEPERATOR);
		Product product = new Product(lines[0], lines[1]);
		return product;
	};

	private Function<String, Map<String, List<WeekSales>>> mapToProductAndWeekSales = (line) -> {
		String[] lines = line.split(SEPERATOR);
		Map<String, List<WeekSales>> mapOfProductAndWeekSales = new HashMap<String, List<WeekSales>>();

		if (mapOfProductAndWeekSales.get(lines[0]) == null) {
			List<WeekSales> listOfWeekSales = new ArrayList<>();
			WeekSales weekSales = new WeekSales(Integer.parseInt(lines[1]), lines[2], lines[3]);
			listOfWeekSales.add(weekSales);
			mapOfProductAndWeekSales.put(lines[0], listOfWeekSales);
		} else {
			WeekSales weekSales = new WeekSales(Integer.parseInt(lines[1]), lines[2], lines[3]);
			mapOfProductAndWeekSales.get(lines[0]).add(weekSales);
		}
		return mapOfProductAndWeekSales;
	};

	private Map<String, List<WeekSales>> getProductWeekSales() throws Exception {
		Map<String, List<WeekSales>> mapOfProductAndWeekSales = new HashMap<>();

		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(STATISTICS_FILE_LOCATION + WEEK_SALES_FILE_NAME))))) {

			bufferedReader.lines().skip(1).map(mapToProductAndWeekSales).collect(Collectors.toList());

		} catch (FileNotFoundException e) {
			logger.error("Error while reading CSV File" + PRODUCT_FILE_NAME);
			throw e;
		} catch (IOException e) {
			logger.error("Error while reading CSV File" + PRODUCT_FILE_NAME);
			throw e;
		}
		return mapOfProductAndWeekSales;
	}
}
