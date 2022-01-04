package application;

import java.util.List;

import javafx.collections.ObservableList;

public interface SongDAO {


	public void addSong(Song s);
	public boolean update(Song s);
	public boolean delete(int id);//BUN
	public boolean deleteSongFromPlaylist(int id);//BUN
	public ObservableList<Song> getAllSongsByCriteria(String criteria, String equals);//BUN
	public  ObservableList<Song> getSongs(); //BUN
	public ObservableList<Playlist> getPlaylists();
	public List<Integer> getAllSongsInPlaylist(Integer playlistId);
	public Song getSongById(int id);
	public void addPlaylist(String playlistName);
	public boolean addSongToPlaylistByArtistTitle(int id, String title, String artist);
	public boolean CheckIfSongExist(int idPlaylist, int idSong );
	public void addAllSongsToPlaylistByArtist(int id, String artist);
	public ObservableList<Song> getSongsInPlaylist(int playlistid);
	
//	
//	
//	
//	
//	
//	public Song[] getAllSongs();
//	public Song[] getAllSongsAscOrDescByColumn(String column, String ascOrDesc);
//	public void createPlaylist(Playlist p);
////	public void addSongToPlaylistByArtistTitle(int id, String Title, String Artist);
//	public int getIdByArtistTitle(String title, String artist);
//	
//	
//	public ArrayList<Integer> getIdsByArtist(String artist);
//
//	
//	public Song[] getAllSongsByDuration(Double duration);
//
//	
//	public void PlaySongById(int id);
//	
////	public Song findById(int id);
	
	
	

	
}
