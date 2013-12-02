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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.7
 */
public class TweetSearch extends AsyncTask<String[], String, String>
{
    /**
     * Usage: java twitter4j.examples.search.SearchTweets [query]
     *
     * @param args
     */
	public static final int DIALOG_SEARCH_PROGRESS = 0;
	private ProgressDialog mProgressDialog;
	
	private Context context;
	private long queryMinID;
	
//	oauth.consumerKey= "";
	public TweetSearch(Context context)
	{
		this.context = context;
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setMessage("Searching tweets...");
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setMax(100);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setCancelable(true);
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey("************************")
			.setOAuthConsumerSecret("*************************************")
			.setOAuthAccessToken("*************************************")
			.setOAuthAccessTokenSecret("********************************");
		TwitterFactory tf = new TwitterFactory(cb.build() );
		Twitter twitter = tf.getInstance();
//		showTweets();
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressDialog.show();
	}
	
	@Override
	protected String doInBackground(String[]... searchWordsDate) {
        if (searchWordsDate[0].length < 1) {
//            System.out.println("java twitter4j.examples.search.SearchTweets [query]");
//            System.exit(-1);
        	Log.i("SearchTweets", "showTweets(String[]) - at least 1 word & the date need to be passed");
        	return null;
        }
        
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
            Query query = new Query(searchWordsDate[0][0]);
            DateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm");

            query.since(searchWordsDate[0][1]);
            query.setCount(100);
            QueryResult result;
            do {
            	Log.i("SearchTweets", "the word passed is " + searchWordsDate[0][0]);
            	Log.i("SearchTweets", "the date passed is " + searchWordsDate[0][1]);
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
//                	Date myTmp = tweet.getCreatedAt();
                	Log.i("SearchTweets", "@" + tweet.getUser().getScreenName() + " (" + tweet.getUser().getName() +
                			") at " + df.format(tweet.getCreatedAt() ) + " tweeted:  " + tweet.getText() );
                	Log.i("SearchTweets(2)", " " + tweet.getId() + " ");
//                    Log.i("SearchTweets", "@" + tweet.getUser().getScreenName() + " - " + tweet.getText() );
//                    Log.i("SearchTweets", "(Name): " + tweet.getUser().getName() + " - " + tweet.getText() );
//                    Log.i("SearchTweets", "(Name): " + df.format(tweet.getCreatedAt() ) + " - " + tweet.getText() );
//                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText() );
                	if (tweet.getId() < queryMinID)
                	{
                		queryMinID = tweet.getId();
                	}
                }
            } while ( (query = result.nextQuery() ) != null);
            return null;
//            System.exit(0);
        } catch (TwitterException te) {
//            te.printStackTrace();
//            System.out.println("Failed to search tweets: " + te.getMessage() );
            Log.i("SearchTweets", "Failed to search tweets: " + te.getMessage() );
            return null;
//            System.exit(-1);
        }
    }
	
	
	protected void onProgressUpdate(String... progress) {
		Log.d("ANDRO_ASYNC",progress[0]);
		mProgressDialog.setProgress(Integer.parseInt(progress[0]));
	}

	@Override
	protected void onPostExecute(String unused) {
		mProgressDialog.dismiss();
	}	
	
}
