package tweetCollector;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class TwitterConnector {

	public void updateStatus(String status){

		try
		{
			Twitter twitter = getTwitter();
			twitter.updateStatus(status);
		}
		catch(TwitterException e) {

			e.printStackTrace();
			System.out.println("Failed to update status: " + e.getMessage());
		}

	}
	
	public void getHomeTimeLine() {
		Twitter twitter = getTwitter();
		List<Status> statuses;
		try {
			statuses = twitter.getHomeTimeline();
			
			ArrayList<Tweet> tweets = new ArrayList<>();
			
			for (Status status : statuses) {		
				
				Tweet tweet = new Tweet(status);
				tweets.add(tweet);
				
			}
			
			String fileName = "hometimeline";
			

			ExcelFileWriter.writeExcel(fileName + ".xls", tweets);		
//			CsvFileWriter.writeCsvFile(fileName + ".csv", tweets);

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to get home timeline: " + e.getMessage());
		}

	}
	
	public void getSearchResult(String keyword, int numberOfResults) {
		try {
			Twitter twitter = getTwitter();
			Query query = new Query(keyword);

			int numberOfTweets = numberOfResults;
			long lastID = Long.MAX_VALUE;
			ArrayList<Status> statuses = new ArrayList<Status>();
			while (statuses.size () < numberOfTweets) {
				if (numberOfTweets - statuses.size() > 100)
					query.setCount(100);
				else 
					query.setCount(numberOfTweets - statuses.size());

					QueryResult result = twitter.search(query);
					statuses.addAll(result.getTweets());
					for (Status t: statuses) 
						if(t.getId() < lastID) lastID = t.getId();

				query.setMaxId(lastID-1);			
			}

			ArrayList<Tweet> tweets = new ArrayList<>();
			
			for (Status status : statuses) {		
				
				Tweet tweet = new Tweet(status);
				tweets.add(tweet);
				
			}
			
			String fileName = "searchResultFor_" + keyword;
			

			ExcelFileWriter.writeExcel(fileName + ".xls", tweets);		
//			CsvFileWriter.writeCsvFile(fileName + ".csv", tweets);

		} catch (Exception te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
		}
	}
	
	public void getTweetsByID(String user) {
		
		try {

			Twitter twitter = getTwitter();

			int pageno = 1;
			ArrayList<Status> statuses = new ArrayList<Status>();

			while (true) {

				Paging page = new Paging(pageno++, 200);
				List<Status> temp = twitter.getUserTimeline(user, page);
				statuses.addAll(temp);

				if (temp.size() == 0)
					break;
			}

			ArrayList<Tweet> tweets = new ArrayList<>();

			for (Status status : statuses) {		
//				if(status.isRetweet())
//				{
//					status = status.getRetweetedStatus();
//				} 

				Tweet tweet = new Tweet(status);
				tweets.add(tweet);
			}

			String fileName = "publicTweetsBy_" + user;

			ExcelFileWriter.writeExcel(fileName + ".xls", tweets);		
//			CsvFileWriter.writeCsvFile(fileName + ".csv", tweets);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Failed to get public tweets: " + e.getMessage());
		}
	}
	
	public Twitter getTwitter() {

		String consumerKey = null, consumerSecret = null, 
					accessToken = null, accessTokenSecret = null;

		try {
			File configFile = new File("config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(configFile);

			Node configNode = doc.getElementsByTagName("config").item(0);
			NodeList configChilds = configNode.getChildNodes();
			
			for (int temp = 0; temp < configChilds.getLength(); temp++) {
				
				Node nNode = configChilds.item(temp);
				
				switch (nNode.getNodeName()) {
				case "ConsumerKey" :
					consumerKey = nNode.getTextContent();
					break;
				case "ConsumerSecret" :
					consumerSecret = nNode.getTextContent();
					break;
				case "AccessToken" :
					accessToken = nNode.getTextContent();
					break;
				case "AccessTokenSecret" :
					accessTokenSecret = nNode.getTextContent();
					break;
				default : break;
				}
				
			}
			
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			.setOAuthConsumerKey(consumerKey)
			.setOAuthConsumerSecret(consumerSecret)
			.setOAuthAccessToken(accessToken)
			.setOAuthAccessTokenSecret(accessTokenSecret);

			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();
			
			return twitter;
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	
	public void read(InputStream inStream){
		Scanner input;
		String inputString;
		input = new Scanner(inStream);
		do{
//			System.out.print(">> ");
			inputString = input.nextLine().trim();
//			System.out.println(inputString);
//			ArrayList<String> commandList = getCommandList(inputString);
			try {
				executeCommand(inputString);	
//				executeCommand(commandList);			
			}catch (NumberFormatException e) {
				System.out.println("\nCould not parse string to double\n");
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}	
		}while(input.hasNext());
		input.close();
	}
	
//	public ArrayList<String> getCommandList(String inputString){
//		String commands[] = inputString.split(" ");
//
//		ArrayList<String> commandList = new ArrayList<String>();
//
//		for (String command : commands){
//			if(!command.isEmpty()){ 
//				commandList.add(command);
//			}
//		}
//		return commandList;
//	}	
	
	public void executeCommand(String inputString) throws Exception{
		
		String commands[] = inputString.split(" ");

		ArrayList<String> commandList = new ArrayList<String>();

		for (String command : commands){
			if(!command.isEmpty()){ 
				commandList.add(command);
			}
		}
		
		if(commandList.isEmpty()){
			return;
		}else if(commandList.get(0).equalsIgnoreCase("quit")){
			System.exit(0); 
		}else{
			switch(commandList.get(0)){
			case "getPublicTweets":
				String tweeterID = commandList.get(1);
				System.out.println("Searching for public tweets by @" + tweeterID);
				getTweetsByID(tweeterID);
				break;
			case "getSearchResults":
								
				String keyword = inputString.substring(inputString.indexOf("[") + 1, 
														inputString.indexOf("]"));
				int maxResults = Integer.valueOf(commandList.get(commandList.size()-1));
				
				System.out.println("Searching for " + keyword 
								+ " - Saving " + maxResults + " results");
				
				getSearchResult(keyword, maxResults);
				break;
			default:
				throw new Exception("\nUnknown Command\n");
			}
		}
	}
	
}
