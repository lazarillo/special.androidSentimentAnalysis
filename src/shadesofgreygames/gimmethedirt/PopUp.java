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
		return v;
	}
}


//android:textAppearance="@android:style/TextAppearance.DialogWindowTitle" />

