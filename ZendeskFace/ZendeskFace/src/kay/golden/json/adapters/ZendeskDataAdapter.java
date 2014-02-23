package kay.golden.json.adapters;

import java.util.ArrayList;

import kay.golden.json.List_view_selection;
import kay.golden.json.R;
import kay.golden.json.ZendeskFace;
import kay.golden.json.ZendeskFace.MyViewHolder;
import kay.golden.json.data.ZendeskData;
import kay.golden.json.tasks.ZendeskIconTask;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
	private ZendeskIconTask imgFetcher;
	private LayoutInflater layoutInflater;
	private ArrayList<ZendeskData> ticketsdata;
	
	MyViewHolder holder;
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
		
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.ticketdetail, parent,
					false);
			holder = new MyViewHolder();
		
			
			holder.subject = (TextView) convertView.findViewById(R.id.subject);
			holder.ticket_no = (TextView) convertView
					.findViewById(R.id.ticket_no);
			
			holder.ticket_description = (TextView) convertView
					.findViewById(R.id.ticket_description);
			holder.icon = (ImageView) convertView.findViewById(R.id.album_icon);

			convertView.setTag(holder);
		} else {
			holder = (MyViewHolder) convertView.getTag();
		}

		convertView.setOnClickListener(this);
		ZendeskData position  = ticketsdata.get(pos);
		
	
		holder.ticketsdata = position;
		if (position.getSubject() != null) {
			holder.subject.setText("Title : " + position.getSubject());

		} else {
			holder.subject.setText("Title : No Subject");
		}

		holder.ticket_no.setText("Id: " + position.getTicketno());
		
		holder.ticket_description.setText("Link : " + "http://assignment.gae.golgek.mobi"
				+ position.getDescription());

		if (position.getImageUrl() != null) {
			holder.icon.setTag(position.getImageUrl());
			Drawable dr = imgFetcher.loadImage(this, holder.icon);
			if (dr != null) {
				holder.icon.setImageDrawable(dr);
			}
		} else {
			holder.icon.setImageResource(R.drawable.filler_icon);
		}
		

		return convertView;
	}

	
	
	public void onClick(AdapterView<?> parent, View view,int position, long id) {
		
		Intent in = new Intent(this.activity,List_view_selection.class);
		
		String title = position.getSubject();
		String Id = position.getTicketno();
		String Link = position.getDescription();
		 in.putExtra("name",title);
         in.putExtra("email",Id);
         in.putExtra("link",Link);
         this.activity.startActivity(in);

	}

		
		
		

	}

