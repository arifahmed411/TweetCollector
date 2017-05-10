package tweetCollector;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class CsvFileWriter {
	
	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	//CSV file header
	private static final String FILE_HEADER = "UserID,Status,Date,Retweet Count,Favorite Count";

	public static void writeCsvFile(String fileName, ArrayList<Tweet> tweets) {
		
		FileWriter fileWriter = null;
				
		try {
			fileWriter = new FileWriter(fileName);

			//Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());
			
			//Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			//Write a new tweet object list to the CSV file
			for (Tweet tweet : tweets) {
				fileWriter.append(tweet.getTweeterID());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(tweet.getStatus());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(tweet.getDate().toString());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(tweet.getRetweetCount()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(tweet.getFavoriteCount()));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}	
			
			System.out.println("CSV file was created successfully !!!");
			
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
			}
			
		}
	}
}
