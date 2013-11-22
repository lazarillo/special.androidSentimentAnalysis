package shadesofgreygames.gimmethedirt;

import java.util.Calendar;

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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView tvDisplayDate;
	private Button btnChangeDate;
	private Button btnGo;
	
	private int year;
	private int month;
	private int day;
	private int mStackLevel = 0;
	
	private String sDict;
	
	static final int DATE_DIALOG_ID = 101;
	static final int START_SEARCH_ID = 102;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setCurrentDateOnView();
		addListenerOnButton();
	}
	
	// constructor to make it easier to consistently print the date
	private String readableDate(int year, int month, int day) {
		return new StringBuilder().append(year).append("/").append(month + 1).append("/").append(day).toString();
	}

	// display current date
	public void setCurrentDateOnView() {
		tvDisplayDate = (TextView) findViewById(R.id.tvDate);
		
		final Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH) - 5;
		
		// set current date into the text view
		tvDisplayDate.setText(readableDate(year, month, day) );		// Month is 0 based, just add 1
	}
	
	public void addListenerOnButton() {
		
		btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
		btnGo = (Button) findViewById(R.id.btnGo);
		
		btnChangeDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showPopUp(DATE_DIALOG_ID);
//				showDialog(DATE_DIALOG_ID);
			}
		} );
		btnGo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Getting info...", Toast.LENGTH_LONG).show();
				showPopUp(START_SEARCH_ID);
//				showDialog(START_SEARCH_ID);
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
		case DATE_DIALOG_ID:
			new DatePickerDialog(this, datePickerListener, year, month, day).show();
			break;
		case START_SEARCH_ID:
//			DialogFragment newFragment1 = new PopUp().newPopUp("let's hit it!!").show(ft, "dialog");
//			new PopUp().newPopUp("let's hit it!!").show(ft, "dialog");
//			newFragment1.show(ft, "dialog");
			sDict = new SentimentTable(this).getDict();
			new PopUp().newPopUp(sDict).show(ft, "dialog");
//			new Dialog(this).setTitle(sDict);
			
//			new Dialog(this).setTitle(sDict);
			break;
		}
	}
	

			
	
	
	
//	private AlertDialog.Builder popUpDialog = new AlertDialog.Builder(this);
	
	
	
	
	
	
	/*				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					// TODO Auto-generated method stub
					
				}
			};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
*/
}


