package org.springwiz.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.Future;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springwiz.service.SalesStatisticsFileProcessorService;

@RestController
@RequestMapping(value = "/fileprocessor")
public class SalesFileStatisticsProcessorController {

	private static final String FILE_PATH = "c://springwitz//";
	private static final String FILE_NAME = "salesStatistics.xlsx";

	private final SalesStatisticsFileProcessorService salesStatisticsFileProcessorService;

	@Autowired
	public SalesFileStatisticsProcessorController(SalesStatisticsFileProcessorService salesStatisticsFileProcessorService) {
		this.salesStatisticsFileProcessorService = salesStatisticsFileProcessorService;
	}


	@PostMapping(value = "/upload")
	@Async
	public Future<String> upload(@PathVariable List<Attachment> attachments) throws IOException {
		for (Attachment attachment : attachments) {
			InputStream inputStream = attachment.getDataHandler().getInputStream();
			copyFile(inputStream);
		}
		return new AsyncResult<String>("OK");
	}

	@GetMapping(value = "/download/{fileName}")
	public Response download(@PathVariable String fileName) {
		File file = new File(FILE_PATH + fileName);
		salesStatisticsFileProcessorService.saveSalesStatisticsInCsvFormat(file);
		ResponseBuilder responseBuilder = Response.ok(file);
		responseBuilder.header("Content-Disposition", "attachment;filename=" + fileName);
		return responseBuilder.build();
	}

	private void copyFile(InputStream inputStream) throws FileNotFoundException, IOException {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];

		out = new FileOutputStream(new File(FILE_PATH+FILE_NAME));
		while ((read = inputStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}
}
