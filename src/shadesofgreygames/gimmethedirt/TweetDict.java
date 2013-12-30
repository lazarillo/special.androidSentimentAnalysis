/**
 * 
 */
package shadesofgreygames.gimmethedirt;

import java.util.Date;
import java.util.Map;

import android.util.Log;



/**
 * @author MikeW
 *
 */

public class TweetDict
{
	/**
	 * Usage: create dictionary with all the essentials that are
	 *        needed when assembling tweet search results
	 *        
	 */
	private String screenName;
	private String userName;
	private Date date;
	private String tweet;
	private Integer score;
	private Map<String, Byte> mSentiments;
	
	public TweetDict(String screenName, String userName, Date date, String tweet,
			Map<String, Byte> mSentiments) {
		this.mSentiments = mSentiments;
		this.screenName = screenName;
		this.userName = userName;
		this.date = date;
		this.tweet = tweet.replaceAll("[\n\r]", "");
		this.calcScore();
	}
	
	// getters...
	public Date getDate() {
		return date;
	}
	public String getScreenName() {
		return screenName;
	}
	public String getName() {
		return userName;
	}
	public String getTweet() {
		return tweet;
	}
	public Integer getScore() {
		return score;
	}
	
	//setters
	public void setDate(Date date) {
		this.date = date;
		return;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
		return;
	}
	public void setName(String userName) {
		this.userName = userName;
		return;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
		return;
	}
	
	@Override public String toString() {
		return "tweet: " + tweet + "  score: " + score + "  time: " + date +
				"  screen name: " + screenName + "  name: " + userName;
	}
	
	public void calcScore() {
		String[] words = tweet.split("\\s");
		this.score = 0;
		for (String word : words) {
			word = word.replaceAll("[^A-Za-z]", "");
			if (this.mSentiments.containsKey(word) ) {
				this.score += this.mSentiments.get(word);
			}
		}
		Log.i("tweetDict", "(1) Score: " + this.score + ",    (2) Time: " + this.date + ",    (3) Tweet: " +
				this.tweet + ",    (4) Screen name: " + this.screenName + ",    (5) Name: " + this.userName);
	}
}

