package kay.golden.json.data;

public class Data {

	private String title;
	private String id;
	private String imageUrl;
	private String authour;
	private String link;
	private String price;

	public Data(String title, String id,String imageUrl,String authour,String link,String price) {
		super();
		this.title = title;
		this.id = id;
		this.imageUrl = imageUrl;
		this.authour = authour;
		this.link = link;
		this.price = price;
	}

	public String gettitle() {
		return title;
	}

	public void settitle(String title) {
		this.title = title;
	}

	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getauthour() {
		return authour;
	}

	public void setauthour(String authour) {
		this.authour = authour;
	}

	public String getlink() {
		return link;
	}

	public void setlink(String link) {
		this.link = link;
	}

	public String getprice() {
		return price;
	}

	public void setprice(String price) {
		this.price = price;
	}
	
}
