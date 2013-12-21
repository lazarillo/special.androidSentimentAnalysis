/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package shadesofgreygames.gimmethedirt;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.DatePicker;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.7
 */
public class TweetSearch extends AsyncTask<String[], String, Map<String, TweetDict> >
{
    /**
     * Usage: java twitter4j.examples.search.SearchTweets [query]
     *
     * @param args
     */
	public static final int DIALOG_SEARCH_PROGRESS = 0;
	private ProgressDialog mProgressDialog;
	
	private Context context;
	private Map<String, Byte> mSentiments;
	private long queryMinID = Long.MAX_VALUE;
	private Date queryMinDate;
	private Date querySearchDate;
	private Calendar myCal = Calendar.getInstance();
	private int internalCounter = 0;
	private String searchOrder;
	private List<Integer> scoresOnly;
	
//	oauth.consumerKey= ""; private Map<String, Byte> mSentiments;

	public TweetSearch(Context context, Map<String, Byte> mSentiments)
	{
		this.context = context;
		this.mSentiments = mSentiments;
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setMessage("Searching tweets...");
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setMax(100);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setCancelable(true);
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey("5aQCEjgCZvVLZkZ1bE1Bg")
			.setOAuthConsumerSecret("TorZa4i7TjVsiHwqK8NoBCTMpQjd9lN9hPP63mug")
			.setOAuthAccessToken("1424910571-llST7MoEuC2ZtRhaOODaVwasxPgTA53D1iEYI2u")
			.setOAuthAccessTokenSecret("ECg2dKLtV4dkP8sgH0mbRyJFKXdxYHucoT9riWgVC8");
/*
 			.setOAuthConsumerKey("************************")
			.setOAuthConsumerSecret("*************************************")
			.setOAuthAccessToken("*************************************")
			.setOAuthAccessTokenSecret("********************************");
*/
		TwitterFactory tf = new TwitterFactory(cb.build() );
		Twitter twitter = tf.getInstance();
//		showTweets();
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressDialog.show();
	}
	
//	protected List<TweetDict> doInBackground(String[]... searchInfo) {
	@Override
	protected Map<String, TweetDict> doInBackground(String[]... searchInfo) {
        if (searchInfo[0].length < 1) {
//            System.out.println("java twitter4j.examples.search.SearchTweets [query]");
//            System.exit(-1);
        	Log.i("SearchTweets", "showTweets(String[]) - at least 1 word & the date need to be passed");
        	return null;
        }
        String searchWords = searchInfo[0][0];
        this.searchOrder = searchInfo[0][1];
        String searchDate = searchInfo[0][2];
        
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey("5aQCEjgCZvVLZkZ1bE1Bg")
			.setOAuthConsumerSecret("TorZa4i7TjVsiHwqK8NoBCTMpQjd9lN9hPP63mug")
			.setOAuthAccessToken("1424910571-llST7MoEuC2ZtRhaOODaVwasxPgTA53D1iEYI2u")
			.setOAuthAccessTokenSecret("ECg2dKLtV4dkP8sgH0mbRyJFKXdxYHucoT9riWgVC8");
		TwitterFactory tf = new TwitterFactory(cb.build() );
		Twitter twitter = tf.getInstance();
		
//        Twitter twitter = new TwitterFactory().getInstance();
        try {
        	Map <String, TweetDict> results = new HashMap<String, TweetDict>();
//        	Map <String, Integer> scores = new HashMap<String, Integer>();
        	this.scoresOnly = new ArrayList<Integer>();
//    		List<TweetDict> keptResults = new ArrayList<TweetDict>();
//        	Map <String, TweetDict> keptResults = new HashMap<String, TweetDict>();
//        	Integer[] scoresOnly[];
//    		DateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm");
    		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    		queryMinDate = new Date(); // this assumes no one will ever run a query for only this very moment
    		Log.i("SearchTweets", "I made it into the first \"try\"");
    		try {
        		Log.i("SearchTweets", "I made it into the second \"try\"");
    			myCal.setTime(df.parse(searchDate) ); // myCal is a nonsense variable needed to get "Calendar" object to work
        		Log.i("SearchTweets", "I made it past the calendar time setting");
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
    		querySearchDate = new Date(myCal.getTimeInMillis() ); // Calendar object is preferred when SETTING dates
        	do {
        		internalCounter++;
        		Query query = new Query(searchWords);
        		query.setMaxId(queryMinID - 1);
//        		query.since(searchDate);
        		query.setCount(100);
        		QueryResult result;
        		do {
        			Log.i("SearchTweets", "the words passed are " + searchWords);
        			Log.i("SearchTweets", "the internal counter is at " + internalCounter);
        			Log.i("SearchTweets", "the query min ID is at " + query.getMaxId() );
        			Log.i("SearchTweets", "the date passed is " + searchDate);
        			Log.i("SearchTweets", "the query passed is " + query);
        			Log.i("SearchTweets", "the getQuery() is " + query.getQuery() );
        			Log.i("SearchTweets", "the Twitter passed is " + twitter);
        			Log.i("SearchTweets", "the number of tweets to return per page is " + query.getCount() );
            	
        			result = twitter.search(query);
        			queryMinID = result.getMaxId();
        			Log.i("SearchTweets(2)", "the max ID on any of the tweets is  " + queryMinID );
                
        			List<twitter4j.Status> tweets = result.getTweets();
        			for (twitter4j.Status tweet : tweets) {
//                	if (tweet.isRetweeted() ) {
//                		break;
//                	}
        				TweetDict tweetDict = new TweetDict(tweet.getUser().getScreenName(), tweet.getUser().getName(),
        						tweet.getCreatedAt(), tweet.getText(), mSentiments);
        				if (!results.containsKey(tweetDict.getTweet() ) ) {
        					results.put(tweetDict.getTweet(), tweetDict);
//        					scores.put(tweetDict.getTweet(), tweetDict.getScore() );
        					this.scoresOnly.add(tweetDict.getScore() );
        				}
        				if (tweet.getId() < queryMinID)
        				{
        					queryMinID = tweet.getId();
        				}
        				if (tweet.getCreatedAt().before(queryMinDate) )
        				{
        					queryMinDate = tweet.getCreatedAt();
        				}
        			}
        		} while ( (query = result.nextQuery() ) != null);
//            System.exit(0);
    			Log.i("SearchTweets", "the internal counter is at " + internalCounter);
    			Log.i("SearchTweets", "the query min ID is at " + queryMinID );
    			Log.i("SearchTweets", "the internal counter is at " + internalCounter);
    			Log.i("SearchTweets", "the query min ID is at " + queryMinID );
    			Log.i("SearchTweets", "the internal counter is at " + internalCounter);
    			Log.i("SearchTweets", "the query min ID is at " + queryMinID );
    			Log.i("SearchTweets", "the internal counter is at " + internalCounter);
    			Log.i("SearchTweets", "the query min ID is at " + queryMinID );
        	} while (false);  // I couldn't seem to get the example below to work properly.
//        	} while (queryMinDate.after(querySearchDate) );

        	
        	
    		return results;
        } catch (TwitterException te) {
//            te.printStackTrace();
//            System.out.println("Failed to search tweets: " + te.getMessage() );
            Log.i("SearchTweets", "Failed to search tweets: " + te.getMessage() );
            return null;
//            System.exit(-1);
        }
    }

	@Override
	protected void onProgressUpdate(String... progress) {
		Log.d("ANDRO_ASYNC",progress[0]);
		mProgressDialog.setProgress(Integer.parseInt(progress[0]));
	}

	@Override
//	protected void onPostExecute(List<TweetDict> searchResults) {
	protected void onPostExecute(Map<String, TweetDict> searchResults) {
    	// Now capture only the "top" tweet information
    	if (this.searchOrder == "hate") {
        	Collections.sort(this.scoresOnly);
    	} else {
    		Collections.sort(this.scoresOnly, Collections.reverseOrder() );
    	}
    	List<TweetDict> keptResults = filterMap(this.scoresOnly, searchResults, 20);

		mProgressDialog.dismiss();
		( (MainActivity) this.context).showSearchResults(keptResults);
//		setContentView(R.layout.tweet_results);
		
	}
	

//	List<Integer> scoresOnly = new ArrayList<Integer>();

	
	private List<TweetDict> filterMap(List<Integer> sortedScores,
			Map<String, TweetDict> allTweets, int numResults)
	{
		// TODO Auto-generated method stub
		// Using the sorted "scoresOnly" list, return
//		List<Integer> scoresOfInterest = new ArrayList<Integer>();
//		scoresOfInterest = sortedScores.subList(0,  numResults);
		List<TweetDict> keptTweets = new ArrayList<TweetDict>();
		Log.i("filterMap", "I made it into the \"filterMap\" method" );
		for (Integer score : sortedScores)
		{
			Log.i("filterMap", "I made it into the \"filterMap\" loop... current score is " + score );
			if ( (keptTweets.size() > numResults) || (Math.abs(score) < 3) )
			{
				Log.i("filterMap", "I made it into the \"filterMap\" if... keptTweets size is " + keptTweets.size() +
						"  and numResults is " + numResults);
				break;
			}
			Log.i("filterMap", "I made it past the \"filterMap\" if" );
			Iterator iter = allTweets.entrySet().iterator();
			Log.i("filterMap", iter.toString() );
//			Log.i("filterMap", "allTweets keys: " + allTweets.keySet().toString() );
//			Log.i("filterMap", "allTweets values: " + allTweets.values().toString() );
			
			while (iter.hasNext() )
			{
				Log.i("filterMap", "I made it into the \"filterMap\" while loop" );
				Map.Entry<String, TweetDict> tweetEntry = (Map.Entry<String, TweetDict>) iter.next();
				Log.i("filterMap", "tweetEntry tweet: " + tweetEntry.getValue().getTweet() );
				Log.i("filterMap", "tweetEntry score: " + tweetEntry.getValue().getScore() + " (versus score: " + score + ")" );
				if ( (this.searchOrder == "love") && (tweetEntry.getValue().getScore() >= score) &&
						!keptTweets.contains(tweetEntry.getValue() ) ||
						( (this.searchOrder == "hate") && (tweetEntry.getValue().getScore() <= score) &&
								!keptTweets.contains(tweetEntry.getValue() ) ) )
				{
					keptTweets.add(tweetEntry.getValue() );
//					keptTweets.add(tweetEntry.getKey(), tweetEntry.getValue() );
					Log.i("filterMap", "The following tweet removed: " + tweetEntry.getValue() );
					
//					allTweets.remove(tweetEntry.getKey() );					
				}
				if ( (keptTweets.size() > numResults) || (Math.abs(score) < 3) )
				{
					Log.i("filterMap", "I made it into the \"filterMap\" if... keptTweets size is " + keptTweets.size() +
							"  and numResults is " + numResults);
					break;
				}
			}
			
		}
		return keptTweets;
	}
	
	
}


