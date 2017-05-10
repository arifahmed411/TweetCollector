package tweetCollector;

import java.text.SimpleDateFormat;
import java.util.Date;
import twitter4j.Status;


public class Tweet {
	
//	private Twitter twitter;
	private String tweeterID;
	private String status;
	private Date date;
	private int retweetCount;
	private int favoriteCount;
//	private int replyCount = 0;
	private boolean isRetweet;
	private String retweetUserID = "";
	private long tweetID;
	
	public Tweet(Status status) {

//		this.twitter = twitter;
		tweeterID = status.getUser().getScreenName();
		this.status = status.getText(); 
		date = status.getCreatedAt(); 
		retweetCount = status.getRetweetCount(); 
		favoriteCount = status.getFavoriteCount();
		isRetweet = status.isRetweet();
		if(isRetweet) retweetUserID = status.getRetweetedStatus().getUser().getScreenName();
//		if(!isRetweet) replyCount = getDiscussion(status).size();
		tweetID = status.getId();	
		
	}
	
//	public ArrayList<Status> getDiscussion(Status status) {
//		ArrayList<Status> replies = new ArrayList<>();
//
//	    ArrayList<Status> all = null;
//
//	    try {
//	        long id = status.getId();
//	        String screenname = status.getUser().getScreenName();
//
//	        Query query = new Query("@" + screenname + " since_id:" + id);
//
//	        System.out.println("query string: " + query.getQuery());
//
//	        try {
//	            query.setCount(100);
//	        } catch (Throwable e) {
//	            // enlarge buffer error?
//	            query.setCount(30);
//	        }
//
//	        QueryResult result = twitter.search(query);
//	        System.out.println("result: " + result.getTweets().size());
//
//	        all = new ArrayList<Status>();
//
//	        do {
//	            System.out.println("do loop repetition");
//
//	            List<Status> tweets = result.getTweets();
//
//	            for (Status tweet : tweets)
//	                if (tweet.getInReplyToStatusId() == id)
//	                    all.add(tweet);
//
//	            if (all.size() > 0) {
//	                for (int i = all.size() - 1; i >= 0; i--)
//	                    replies.add(all.get(i));
//	                all.clear();
//	            }
//
//	            query = result.nextQuery();
//
//	            if (query != null)
//	                result = twitter.search(query);
//
//	        } while (query != null);
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    } catch (OutOfMemoryError e) {
//	        e.printStackTrace();
//	    }
//	    return replies;
//	}

	public String getTweeterID() {
		return tweeterID;
	}

	public void setTweeterID(String userID) {
		this.tweeterID = userID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormat.format(date);

	}
	
	public String getTime() {
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:s");
		return timeFormat.format(date);

	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(int retweetCount) {
		this.retweetCount = retweetCount;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	
//	public int getReplyCount() {
//		return replyCount;
//	}
//
//	public void setReplyCount(int replyCount) {
//		this.replyCount = replyCount;
//	}

	public boolean isRetweet() {
		return isRetweet;
	}

	public void setRetweet(boolean isRetweet) {
		this.isRetweet = isRetweet;
	}

	public String getRetweetUserID() {
		return retweetUserID;
	}

	public void setRetweetUserID(String retweetUserID) {
		this.retweetUserID = retweetUserID;
	}

	public long getTweetID() {
		return tweetID;
	}

	public void setTweetID(long tweetID) {
		this.tweetID = tweetID;
	}
	
	public String getURL() {
		String url= "https://twitter.com/" + tweeterID 
		    + "/status/" + tweetID;
		return url;
	}

	@Override
	public String toString() {
		return "Tweet [tweeterID=" + tweeterID + ", status=" + status + ", date=" + date + ", retweetCount="
				+ retweetCount + ", favoriteCount=" + favoriteCount + ", isRetweet=" + isRetweet + ", retweetUserID="
				+ retweetUserID + ", tweetID=" + tweetID + "]";
	}
	
}
