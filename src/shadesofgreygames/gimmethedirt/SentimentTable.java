/**
 * 
 */
package shadesofgreygames.gimmethedirt;

import java.io.*;

import android.app.Activity;
//import android.content.res.Resources;
//import android.util.Log;
//import android.widget.Toast;
import android.app.Dialog;
import android.content.ContextWrapper;
import android.view.View.OnClickListener;

/**
 * @author Mike Williamson
 *
 */
//public class SentimentTable extends Activity
public class SentimentTable extends Activity
{
	private String sDict;
	private OnClickListener onClickListener;
	private Activity activity;

	SentimentTable(Activity activity)
//	SentimentTable(OnClickListener onClickListener)
	{
		this.activity = activity;
		try
		{
			this.sDict = loadFile("sentiment_dict", true);
		}
		catch (IOException e)
		{
			//TODO:  yo
		}
	}
	
	public String getDict()
	{
		return this.sDict;
	}
	
//	private Resources rSent;
//	private String sOutput;
//	private static void readFile()
//	{
////		StringBuffer buffer = new StringBuffer();
////		BufferedReader reader = new BufferedReader(
////				new FileReader("")
//		Resources rSent = Resources.getResources();
//		InputStream sentSt = rSent.openRawResource(R.raw.sentiment_dict);
//		
//		try {
//			//Load the file from the raw folder - don't forget to OMIT the extension
//			sOutput = LoadFile("sentiment_dict", true);
//			Log.i("test", sOutput);
//			//
//		}
//		
//		
//		
//		
//		StringTokenizer st = new StringTokenizer("this is a test");
//		while (st.hasMoreTokens() ) {
//			System.out.println(st.nextToken() );
//		}
//	}

//	public void readFile()
//	{
//		rSent = getResources();
//		
//        try  
//        {
//            //Load the file from the raw folder - don't forget to OMIT the extension  
//            sOutput = loadFile("sentiment_dict", true);  
//            //output to LogCat  
//            Log.i("test", sOutput);  
//        }
//        catch (IOException e)  
//        {  
//            //display an error toast message  
//            Toast toast = Toast.makeText(this, "File: not found!", Toast.LENGTH_LONG);  
//            toast.show();  
//        }  
//	}
	
	public String loadFile(String fileName, boolean loadFromRawFolder) throws IOException
	{
		//Create an InputSteam to read the file into
		InputStream isSent;
				
		if (loadFromRawFolder)
		{
			//get the resource id from the file name
			int iID = this.activity.getResources().getIdentifier("shadesofgreygames.gimmethedirt:raw/" + fileName, null, null);
//			int iID = rSent.getIdentifier("shadesofgreygames.gimmethedirt:raw/" + fileName, null, null);
			//get the file as a stream
			isSent = this.activity.getResources().openRawResource(iID);
		}
		else
		{
			//get the file as a stream
			isSent = this.activity.getResources().getAssets().open(fileName);
//			isSent = rSent.getAssets().open(fileName);
		}
		//create a buffer with the same size as the InputStream
		byte[] bData = new byte[isSent.available() ];
		//read the text file as a stream, into the buffer
		isSent.read(bData);
		//create an output stream into which the buffer is written
		ByteArrayOutputStream baosData = new ByteArrayOutputStream();
		//write the buffer to the output stream
		baosData.write(bData);
		//Close both the input and output streams
		baosData.close();
		isSent.close();
		
		//return the output stream as a string
		return baosData.toString();
	}
	
}




