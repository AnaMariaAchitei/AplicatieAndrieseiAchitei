package application;

import java.util.ArrayList;

public class Playlist {
	private Integer id;
	private String playlistName;
	private ArrayList<Song> songs;
	


	public Playlist(Integer id, String playlistName, ArrayList<Song> songs) {
		this.id = id;
		this.playlistName = playlistName;
		this.songs = songs;
	}
	
	public Playlist(Integer id, String playlistName) {
		this.id = id;
		this.playlistName = playlistName;
	}

	@Override
	public String toString() {
		return "Playlist [id=" + id + ", playlistName=" + playlistName + ", songs=" + songs + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	public ArrayList<Song> getSongs() {
		return songs;
	}

	public void setSongs(ArrayList<Song> songs) {
		this.songs = songs;
	}
	
	
	
}
