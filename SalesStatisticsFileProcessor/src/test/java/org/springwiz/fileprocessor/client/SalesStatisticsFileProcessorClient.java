package org.springwiz.fileprocessor.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;

public class SalesStatisticsFileProcessorClient {
	
	private static final String FILE_PATH = "c://springwitz//";
	private static final String FILE_NAME = "salesStatistics.xlsx";

	public static void main(String[] args) throws FileNotFoundException {

		WebClient client = WebClient.create("http://localhost:8080/fileprocessor/upload");
		client.type("multipart/form-data");
		ContentDisposition cd = new ContentDisposition("attachment;filename="+FILE_NAME);
		Attachment attachment = new Attachment("root", new FileInputStream(new File(FILE_PATH+FILE_NAME)), cd);
		client.post(attachment);
	}
}
