package kay.golden.json.adapters;

import java.util.ArrayList;

import kay.golden.json.R;
import kay.golden.json.ZendeskFace;
import kay.golden.json.ZendeskFace.MyViewHolder;
import kay.golden.json.data.ZendeskData;
import kay.golden.json.tasks.ZendeskIconTask;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter for tickets to fill in the rows in list view
 * 
 * @author Kay Nag
 * 
 */
public class ZendeskDataAdapter extends BaseAdapter implements OnClickListener {

	private static final String debugTag = "ZendeskDataAdapter";
	private ZendeskFace activity;
	ImageView status ;
	private ZendeskIconTask imgFetcher;
	private LayoutInflater layoutInflater;
	private ArrayList<ZendeskData> ticketsdata;

	public ZendeskDataAdapter(ZendeskFace a, ZendeskIconTask i,
			LayoutInflater l, ArrayList<ZendeskData> data) {
		this.activity = a;
		this.imgFetcher = i;
		this.layoutInflater = l;
		this.ticketsdata = data;
		int aas = data.size();
	}

	@Override
	public int getCount() {
		return this.ticketsdata.size();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		MyViewHolder holder;
		
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.rowsgird, parent,
					false);
			holder = new MyViewHolder();
			
			holder.ticket_no = (TextView) convertView
					.findViewById(R.id.text);
			
			holder.icon = (ImageView) convertView.findViewById(R.id.image);
			status = (ImageView)convertView.findViewById(R.id.newbook);

			convertView.setTag(holder);
		} else {
			holder = (MyViewHolder) convertView.getTag();
		}

		convertView.setOnClickListener(this);

		ZendeskData position = ticketsdata.get(pos);
		holder.ticketsdata = position;
		if (position.getSubject() != null) {
			holder.ticket_no.setText(position.getTicketno());

		} else {
			holder.subject.setText("No Title");
		}

		
		if (position.getImageUrl() != null) {
			holder.icon.setTag(position.getImageUrl());
			Drawable dr = imgFetcher.loadImage(this, holder.icon);
			if (dr != null) {
				holder.icon.setImageDrawable(dr);
			}
		} else {
			holder.icon.setImageResource(R.drawable.filler_icon);
		}
		
			 
			 status.setVisibility(position.getTicketstatus().equals("TRUE") ? View.VISIBLE : View.INVISIBLE);
			 
	         

		
		return convertView;
	}

	@Override
	public void onClick(View v) {

		Log.d(debugTag, "OnClick pressed.");

	}

}