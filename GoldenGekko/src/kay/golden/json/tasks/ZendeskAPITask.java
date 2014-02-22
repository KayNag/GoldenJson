package kay.golden.json.tasks;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import kay.golden.json.R;
import kay.golden.json.ZendeskFace;
import kay.golden.json.data.ZendeskData;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * AsyncTask for fetching tickets from Zendesk (very important part of app,do
 * changes carefully )
 * 
 * @author Kay Nag
 */
public class ZendeskAPITask extends AsyncTask<String, Integer, String>  {
	private ProgressDialog progDialog;
	private Context context;
	private ZendeskFace activity;
	private Bitmap background;
	private static final String debugTag = "ZendeskAPITask";

	/**
	 * Construct a task
	 * 
	 * @param activity
	 */
	public ZendeskAPITask(ZendeskFace activity) {
		super();
		this.activity = activity;
		this.context = this.activity.getApplicationContext();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progDialog = ProgressDialog.show(this.activity, "Search", this.context
				.getResources().getString(R.string.looking_for_tickets), true,
				false);
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			Log.d(debugTag, "Background:" + Thread.currentThread().getName());
			String result = ZendeskJSONFetchHelper.downloadFromServer(params);
			return result;
		} catch (Exception e) {
			return new String();
		}
	}

	@Override
	protected void onPostExecute(String result) {

		ArrayList<ZendeskData> ticketsdata = new ArrayList<ZendeskData>(R.layout.check);
		String page = null;

		progDialog.dismiss();
		if (result.length() == 0) {
			this.activity.alert("Unable to find tickets. Try again later.");
			return;
		}

		Document doc = XMLfromString(result);
		int numResults = numResults(doc);
		 if((numResults >= 0)){
		    	Toast.makeText(this.context, "There is no data in the xml file", Toast.LENGTH_LONG).show();  
		    	
		    }
		 NodeList books = doc.getElementsByTagName("BOOK");
		
		
		for (int i = 0; i < books.getLength(); i++) {
			Element e = (Element)books.item(i);
			String ID = ZendeskJSONFetchHelper.getValue(e, "ID");
			String TITLE = ZendeskJSONFetchHelper.getValue(e, "TITLE");
			String imageUrl = ZendeskJSONFetchHelper.getValue(e,"THUMBNAIL");
			String STATUS = ZendeskJSONFetchHelper.getValue(e,"NEW");
			

			ticketsdata.add(new ZendeskData(ID, TITLE, imageUrl,STATUS));
		}

		this.activity.settickets(ticketsdata);
		this.activity.setpage(page);
		

		
	}
public final static Document XMLfromString(String xml){
		
		Document doc = null;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
        	
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(xml));
	        doc = db.parse(is); 
	        
		} catch (ParserConfigurationException e) {
			System.out.println("XML parse error: " + e.getMessage());
			return null;
		} catch (SAXException e) {
			System.out.println("Wrong XML file structure: " + e.getMessage());
            return null;
		} catch (IOException e) {
			System.out.println("I/O exeption: " + e.getMessage());
			return null;
		}
        return doc;
	}

public static int numResults(Document doc){		
	Node results = doc.getDocumentElement();
	int res = -1;
	try{
		res = Integer.valueOf(results.getAttributes().getNamedItem("count").getNodeValue());
	}catch(Exception e ){
		res = -1;
	}
	return res;
}
}