/**
 * 
 */
package shadesofgreygames.gimmethedirt;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

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
	private String sStream;
	private Map<String, Byte> mDict = new HashMap<String, Byte>();
	private OnClickListener onClickListener;
	private Activity activity;

	SentimentTable(Activity activity)
//	SentimentTable(OnClickListener onClickListener)
	{
		this.activity = activity;
		try
		{
			this.sStream = loadFile("sentiment_dict", true);
			this.createDict();
		}
		catch (IOException e)
		{
			//TODO:  yo
		}
	}
	
	public void createDict()
	{
		BufferedReader bufReader = new BufferedReader(new StringReader(sStream) );
		String line = null;
		String[] words = null;
		Byte bScore = 0;
		try { // If unhandled, creates exception type IOException
			while ( (line = bufReader.readLine() ) != null)
			{
				words = line.split("\\t"); // not sure to use \t or \\t... check this.
				bScore = (Byte) Byte.parseByte(words[1] );
				this.mDict.put(words[0], bScore);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<String, Byte> getDict()
	{
		return this.mDict;
	}
	
	
	public String loadFile(String fileName, boolean loadFromRawFolder) throws IOException
	{
		//Create an InputSteam to read the file into
		InputStream isSent;
				
		if (loadFromRawFolder)
		{
			//get the resource id from the file name
			int iID = this.activity.getResources().getIdentifier("shadesofgreygames.gimmethedirt:raw/" + fileName, null, null);
			//get the file as a stream
			isSent = this.activity.getResources().openRawResource(iID);
		}
		else
		{
			//get the file as a stream
			isSent = this.activity.getResources().getAssets().open(fileName);
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




