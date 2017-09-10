package org.springwitz.statistics.model;

import java.util.List;

public class Product {

	private String name;

	private String salesTarget;

	private List<WeekSales> weekSales;
	
	public Product(String name, String salesTarget) {
		this.name = name;
		this.salesTarget = salesTarget;
	}

	public Product(String name, String salesTarget, List<WeekSales> weekSales) {
		this.name = name;
		this.salesTarget = salesTarget;
		this.weekSales = weekSales;
	}

	public String getName() {
		return name;
	}

	public String getSalesTarget() {
		return salesTarget;
	}

	public List<WeekSales> getWeekSales() {
		return weekSales;
	}
}
