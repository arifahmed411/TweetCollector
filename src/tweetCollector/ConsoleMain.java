package tweetCollector;

public class ConsoleMain{
	
	public static void main(String[] args) {
		try {
			
			TwitterConnector tc = new TwitterConnector();

//			tc.updateStatus("Testing Twitter API with Twitter4J!!!");
//			tc.getHomeTimeLine();			
//			tc.getTweetsByID("a_ahmed880");
//			tc.getSearchResult("trump", 10000);
			
			showDisplayOptions();
			
			tc.read(System.in);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void showDisplayOptions(){
		
		System.out.println("\nWelcome to @Collector");
		System.out.println("");		
		System.out.println("Command Syntax:");
		System.out.println("");
		System.out.println("getPublicTweets twitterID");
		System.out.println("e.g. getPublicTweets POTUS");
		System.out.println("");
		System.out.println("getSearchResults [keyword] limit numberOfResults");
		System.out.println("e.g. getSearchResults [donald trump] limit 2000");
		System.out.println("");
		System.out.println("quit");
		System.out.println("");
		System.out.println("Type Command and Press Enter:\n");
	}

}
