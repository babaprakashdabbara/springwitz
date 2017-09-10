package org.springwiz.service;

import java.io.File;

public interface SalesStatisticsFileProcessorService {
	
	void saveSalesStatisticsInCsvFormat(File file);
}
