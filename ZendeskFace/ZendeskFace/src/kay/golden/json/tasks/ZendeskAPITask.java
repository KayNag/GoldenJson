package kay.golden.json.tasks;

import java.util.ArrayList;

import kay.golden.json.R;
import kay.golden.json.ZendeskFace;
import kay.golden.json.data.ZendeskData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * AsyncTask for fetching tickets from Zendesk (very important part of app,do
 * changes carefully )
 * 
 * @author Kay Nag
 */
public class ZendeskAPITask extends AsyncTask<String, Integer, String> {
	private ProgressDialog progDialog;
	private Context context;
	private ZendeskFace activity;
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

		ArrayList<ZendeskData> ticketsdata = new ArrayList<ZendeskData>();

		progDialog.dismiss();
		if (result.length() == 0) {
			this.activity.alert("Unable to find tickets. Try again later.");
			return;
		}

		try {
			JSONArray tickets = new JSONArray(result);

			
			for (int i = 0; i < tickets.length(); i++) {
				String authour,price,image = null;
				JSONObject ticketsdetail = tickets.getJSONObject(i);
				String subject = ticketsdetail.getString("title");
				String ticketno = ticketsdetail.getString("id");
				String ticketdescription = ticketsdetail.getString("link");
				for(int j = 0 ; j<ticketdescription.length();j++)
				{
					String insidejson = null;
					
					try {
						
						 insidejson = ZendeskJSONFetchHelper.downloadFromServer("http://assignment.gae.golgek.mobi" + ticketdescription);
						
					} catch (Exception e) {
						this.cancel(true);
						
					}					
					if (insidejson.length() == 0) {
						this.activity.alert("Unable to find tickets. Try again later.");
						return;
					}
					String a = insidejson;
					JSONObject details = new JSONObject(insidejson);
					
					 authour = details.getString("author");
					 price = details.getString("price");
					 image = details.getString("image");
					
										
				}
				
				ticketsdata.add(new ZendeskData(subject, ticketno,image,ticketdescription));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.activity.settickets(ticketsdata);

	}
}