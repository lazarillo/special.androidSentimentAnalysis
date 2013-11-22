package shadesofgreygames.gimmethedirt;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;




public class PopUp extends DialogFragment {
	private String sMessage;	
	private Bundle args;
	private boolean bMessage = false;

//	public PopUp()
//	{
//		
//	}
	
//	public PopUp newPopUp(String sMessage, String sYes, String sNo, String sMaybe, int iMessage) {
//	static PopUp newPopUp(int iMessage) {
	
	public PopUp newPopUp() {
		PopUp myPopUp = new PopUp();
		Bundle args = new Bundle();
		args.putBoolean("generated_message", false);
		myPopUp.setArguments(args);
//		this.args = args;
		return myPopUp;
	}
	
	public PopUp newPopUp(String sMessage) {
//		this.sMessage = sMessage;
		
		PopUp myPopUp = new PopUp();
		Bundle args = new Bundle();
		args.putString("title", sMessage);
		args.putBoolean("generated_message", true);
		myPopUp.setArguments(args);
//		this.args = args;
		return myPopUp;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		if (getArguments().getBoolean("show_message") ) {
//			sMessage = getArguments().getString("title");
//		}
		int style = DialogFragment.STYLE_NO_TITLE, theme= android.R.style.Theme_Dialog;
		setStyle(style, theme);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_dialog,  container, false);
		View tv = v.findViewById(R.id.tvPopUp);
		if (getArguments().getBoolean("generated_message") ) {
			((TextView)tv).setText(this.getArguments().getString("title") );			
		}
		
		//Watch for button clicks
//		Button button = (Button)v.findViewById(R.id.btnShow);
//		button.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
				//when button is clicked, call up to owning activity.
//				((FragmentDialog)getActivity()).showDialog();
//			}
//		} );
		
		return v;
	}
}


//	@Override
//	public Builder onCreateDialog(Bundle savedInstanceState) {
//		int iMessage = getArguments().getInt("title");
//		
//		return new AlertDialog.Builder(getActivity())
//		.setTitle(iMessage)
//		.setNeutralButton(R.string.love,
//				new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int whichButton) {
//				((FragmentAlertDialog)getActivity()).doNeutralClick();
//			}
//		}
//	}
//	.create();
//}



//android:textAppearance="@android:style/TextAppearance.DialogWindowTitle" />

