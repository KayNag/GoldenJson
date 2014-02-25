package kay.golden.json;

import java.io.InputStream;

import kay.golden.json.tasks.IconTask.ImageTask;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class List_view_selection extends Activity {
	private ImageTask imgFetcher;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view_selection);

		// getting intent data
		Intent in = getIntent();

		// Get JSON values from previous intent
		String title = in.getStringExtra("name");
		String authour = in.getStringExtra("writer");
		String image = in.getStringExtra("image");
		String price = in.getStringExtra("rate");
		String id = in.getStringExtra("id");
		// Displaying all values on the screen
		TextView lbltitle = (TextView) findViewById(R.id.title);
		TextView lblauthour = (TextView) findViewById(R.id.authour);
		TextView lblprice = (TextView) findViewById(R.id.price);
		TextView lblid = (TextView) findViewById(R.id.id);
		TextView lbltitlehead = (TextView) findViewById(R.id.titlehead);
		
		new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
        .execute(image);
		lbltitle.setText(title);
		lblid.setText	  	  (id);
		lbltitlehead.setText  (title);
		lblauthour.setText    (authour);
		lblprice.setText      (price);
	}
}
class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
