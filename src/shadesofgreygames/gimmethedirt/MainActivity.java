package shadesofgreygames.gimmethedirt;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView tvDisplayDate;
	private TextView tvTweetResult;
	private Button btnChangeDate;
	private Button btnGo;
	private RadioGroup rgLoveHate;
	private RadioButton rLoveHate;
	private EditText etSubject;
	private LinearLayout llTweetResults;
	
	private int year;
	private int month;
	private int day;
	private int mStackLevel = 0;
	
	private String sDict;
	private String sortOrder;
	private Map<String, Byte> mSentiments;
	
	static final int START_SEARCH_ID = 101;
	static final int COMPLETE_SEARCH_ID = 102;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setCurrentDateOnView();
		addListenerOnButton();
	}
	
	// constructor to make it easier to consistently print the date
	private String readableDate(int year, int month, int day) {
		return new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).toString();
	}

	// display current date
	public void setCurrentDateOnView() {
		tvDisplayDate = (TextView) findViewById(R.id.tvDate);
		
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -5);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
		
		// set current date into the text view
		tvDisplayDate.setText(readableDate(year, month, day) );		// Month is 0 based, just add 1
	}
	
	public void addListenerOnButton() {
		
		btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
		btnGo = (Button) findViewById(R.id.btnGo);
		etSubject = (EditText) findViewById(R.id.etSubject);
		rgLoveHate = (RadioGroup) findViewById(R.id.rgLoveHate);
		
		btnChangeDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDatePicker();
			}
		} );
		btnGo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(MainActivity.this, "Getting info...", Toast.LENGTH_LONG).show();
//				showPopUp(START_SEARCH_ID);
				int selectedButton = rgLoveHate.getCheckedRadioButtonId();
				rLoveHate = (RadioButton) findViewById(selectedButton);
				Toast.makeText(MainActivity.this, rLoveHate.getText(),
						Toast.LENGTH_LONG).show();
				MainActivity.this.mSentiments = new SentimentTable(MainActivity.this).getDict();
//				Log.i("MainActivity", MainActivity.this.mSentiments.toString() );
//				Integer tempSize;
//				tempSize = MainActivity.this.mSentiments.size();
//				Log.i("MainActivity", tempSize.toString() );
//				String[] myInput = {"Obama"};
//				new SearchTweets().showTweets(myInput);
				startTweetSearch(etSubject.getText().toString(), rLoveHate,
						year, month, day);
//				startTweetSearch(etSubject.getText().toString(), rLoveHate.getText().toString(),
//						year, month, day);
//				showPopUp(COMPLETE_SEARCH_ID);
			}
		} );
	}
		
	private DatePickerDialog.OnDateSetListener datePickerListener =
			new DatePickerDialog.OnDateSetListener() {
		
		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth,
				int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			
			//set selected date into TextView
			tvDisplayDate.setText(readableDate(year, month, day) );
		}
	};
	
	void showPopUp(int id) {
		mStackLevel++;
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		
		//Create and show dialog.
		switch(id) {
		case START_SEARCH_ID:
			new PopUp().newPopUp().show(ft, "dialog");
			break;
		case COMPLETE_SEARCH_ID:
//			new PopUp().newPopUp(this.sDict).show(ft, "dialog");
			new PopUp().newPopUp("done!").show(ft, "dialog");
			break;
			
		}
	}
	
	void showDatePicker()
	{
		mStackLevel++;
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		new DatePickerDialog(this, datePickerListener, year, month, day).show();
	}
	
	private void startTweetSearch(String subject, RadioButton loveHate, int year, int month, int day)
	{
		if (loveHate == (RadioButton) findViewById(R.id.rHate) ) {
			this.sortOrder = "hate";
		} else {
			this.sortOrder = "love";
		}
		if (subject == null) {
			subject = getString(R.string.ask_hint1);
		}
//		searchResults = 
		new TweetSearch(this, this.mSentiments).execute(new String[] {subject, this.sortOrder,
				readableDate(year, month, day) } );
	}
	
	void showSearchResults(List<TweetDict> searchResults) {
//		Iterator iter = searchResults.entrySet().iterator();
//		while (iter.hasNext() ) {
//			Map.Entry<String, TweetDict> 
//		}
		Log.i("showSearchResults", "I made it into the \"showSearchResults\" section" );
		setContentView(R.layout.tweet_results);
		llTweetResults = (LinearLayout) findViewById(R.id.llMain);
//		tvTweetResult = new TextView(this);
		tvTweetResult = (TextView) findViewById(R.id.tvResultsHeader);
		if (this.sortOrder == "love")
		{
			tvTweetResult.setText("Feelin' the Love!");
		} else {
			tvTweetResult.setText("Hatin' on 'em!");
		}
//		llTweetResults.addView(tvTweetResult);
		for (TweetDict searchResult : searchResults) {
			Log.i("showSearchResults", "The following tweet should have been printed: " + searchResult.getTweet() );
			tvTweetResult = new TextView(this);
			tvTweetResult.setText(searchResult.getScreenName() + "(a.k.a. " + searchResult.getName() + ") says:   " +
					searchResult.getTweet() );
			tvTweetResult.setTextSize(20);
			tvTweetResult.setPadding(1, 1, 15, 15);
			llTweetResults.addView(tvTweetResult);
			
		}		
//		tvDisplayDate = (TextView) findViewById(R.id.tvDate);


	}
	
}


