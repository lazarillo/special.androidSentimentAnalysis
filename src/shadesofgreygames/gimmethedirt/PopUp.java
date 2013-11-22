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
	private int iMessage;
	private String sMessage;
	private String sYes;
	private String sNo;
	private String sMaybe;
	private Bundle args;

	public PopUp()
	{
		this.iMessage = R.string.default_message;
	}
	
//	public PopUp newPopUp(String sMessage, String sYes, String sNo, String sMaybe, int iMessage) {
//	static PopUp newPopUp(int iMessage) {
	public PopUp newPopUp(String sMessage) {
		this.sMessage = sMessage;
		this.sYes = sYes;
		this.sNo = sNo;
		this.sMaybe = sMaybe;
		this.iMessage = iMessage;
		
		PopUp myPopUp = new PopUp();
		Bundle args = new Bundle();
		args.putString("title", sMessage);
		myPopUp.setArguments(args);
		this.args = args;
		return myPopUp;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		iMessage = getArguments().getInt("title");
		
		int style = DialogFragment.STYLE_NORMAL, theme= 0;
		setStyle(style, theme);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_dialog,  container, false);
		View tv = v.findViewById(R.id.tvPopUp);
		((TextView)tv).setText("Dialog " + this.getArguments().getString("title") + ": using style blah");
		
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
