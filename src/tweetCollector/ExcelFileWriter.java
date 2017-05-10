package tweetCollector;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelFileWriter {

	public static void writeExcel(String excelFilePath, ArrayList<Tweet> tweets) throws IOException {
	    Workbook workbook = new HSSFWorkbook();
	    Sheet sheet = workbook.createSheet();
	 
	    int rowCount = 0;
	    Row row = sheet.createRow(rowCount++);
	 
	    Cell cell = row.createCell(0);
	    cell.setCellValue("Tweeter ID");

	    cell = row.createCell(1);
	    cell.setCellValue("Status");
	 
	    cell = row.createCell(2);
	    cell.setCellValue("Date");
	    
	    cell = row.createCell(3);
	    cell.setCellValue("Time");
	    
	    cell = row.createCell(4);
	    cell.setCellValue("Retweet Count");
	 
	    cell = row.createCell(5);
	    cell.setCellValue("Favorite Count");
	    
//	    cell = row.createCell(6);
//	    cell.setCellValue("Reply Count");
	    
	    cell = row.createCell(6);
	    cell.setCellValue("Is Retweet");
	    
	    cell = row.createCell(7);
	    cell.setCellValue("Retweeted From");
	    
	    cell = row.createCell(8);
	    cell.setCellValue("URL");
	    
	    for (Tweet tweet : tweets) {
	        row = sheet.createRow(rowCount++);
	        writeTweet(tweet, row);
	    }
	    
	    excelFilePath = excelFilePath.replace(" ", "_");
	 
	    try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
	        workbook.write(outputStream);
	        workbook.close();
	        System.out.println("Excel file was created successfully !!!");
	    }
	}

	private static void writeTweet(Tweet tweet, Row row) {
		
	    Cell cell = row.createCell(0);
	    cell.setCellValue(tweet.getTweeterID());

	    cell = row.createCell(1);
	    cell.setCellValue(tweet.getStatus());
	 
	    cell = row.createCell(2);
	    cell.setCellValue(tweet.getDate());
	    
	    cell = row.createCell(3);
	    cell.setCellValue(tweet.getTime());
	    
	    cell = row.createCell(4);
	    cell.setCellValue(String.valueOf(tweet.getRetweetCount()));
	 
	    cell = row.createCell(5);
	    cell.setCellValue(String.valueOf(tweet.getFavoriteCount()));
	    
//	    cell = row.createCell(6);
//	    cell.setCellValue(String.valueOf(tweet.getReplyCount()));
	    
	    cell = row.createCell(6);
	    cell.setCellValue(String.valueOf(tweet.isRetweet()));
	    
	    cell = row.createCell(7);
	    cell.setCellValue(tweet.getRetweetUserID());
	    
	    cell = row.createCell(8);
	    cell.setCellValue(tweet.getURL());
	   
	}
	
}
