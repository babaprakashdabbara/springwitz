package org.springwitz.statistics.model;

public class WeekSales {
	
	private final int weekNumber;

	private final String actualSales;

	private final String acheivementPercentage;

	public WeekSales(int weekNumber, String actualSales, String acheivementPercentage) {
		this.weekNumber = weekNumber;
		this.actualSales = actualSales;
		this.acheivementPercentage = acheivementPercentage;
	}

	public String getActualSales() {
		return actualSales;
	}

	public String getAchievementPercentage() {
		return acheivementPercentage;
	}

	public int getWeekNumber() {
		return weekNumber;
	}
}
