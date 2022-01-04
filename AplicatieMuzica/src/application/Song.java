package application;

public class Song {
	int id;
	String title;
	String artistBand;
	Double duration;
	Object Type;
	String youtube_link;
	
	
	public Song(int id, String title, String artistBand, Double duration, Object Type, String youtube_link) {
		this.id = id;
		this.title = title;
		this.artistBand = artistBand;
		this.duration = duration;
		this.Type = Type;
		this.youtube_link = youtube_link;
	}
	
	public Song(String title, String artistBand, Double duration, Object Type, String youtube_link) {
		this.title = title;
		this.artistBand = artistBand;
		this.duration = duration;
		this.Type = Type;
		this.youtube_link = youtube_link;
	}
	
	public Song() {
	}
	



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getArtistBand() {
		return artistBand;
	}


	public void setArtistBand(String artistBand) {
		this.artistBand = artistBand;
	}


	public Double getDuration() {
		return duration;
	}


	public void setDuration(Double duration) {
		this.duration = duration;
	}


	public Object getType() {
		return Type;
	}


	public void setType(Object Type) {
		this.Type = Type;
	}


	public String getYoutube_link() {
		return youtube_link;
	}


	public void setYoutube_link(String youtube_link) {
		this.youtube_link = youtube_link;
	}


	@Override
	public String toString() {
		return "Song [id=" + id + ", title=" + title + ", artistBand=" + artistBand + ", duration=" + duration
				+ ", Type=" + Type + ", youtube_link=" + youtube_link + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
