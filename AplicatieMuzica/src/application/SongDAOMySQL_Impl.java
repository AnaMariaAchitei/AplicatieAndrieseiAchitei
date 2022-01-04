package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class SongDAOMySQL_Impl implements SongDAO {

	public static final String CONNECTION_URL = "jdbc:mysql://localhost/aplicatiemuzica";
	

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(CONNECTION_URL, "root", "parolaP1!");
	}

	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

	public void addSong(Song song) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("insert into songs(title, artistBand, duration, type, youtube_link) values(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, song.getTitle());
			ps.setString(2, song.getArtistBand());
			ps.setDouble(3, song.getDuration());
			ps.setString(4, (song.getType()).toString());
			ps.setString(5, song.getYoutube_link());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				song.setId(rs.getInt(1));
			}
			System.out.println("Cantec adaugat cu succes in baza de date");
			closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean update(Song song) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("update songs set title = ? where id = ?");
			ps.setString(1, song.getTitle());
			ps.setInt(2, song.getId());
			int affectedRows = ps.executeUpdate();
			closeConnection(conn);
			
			return affectedRows == 1;
		} catch (SQLException e) {
			
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("delete from songs where id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
			closeConnection(conn);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	@Override
	public boolean deleteSongFromPlaylist(int id) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("delete from playlist where idSong = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
			closeConnection(conn);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public ObservableList<Song> getAllSongsByCriteria(String criteria, String equals) {
		try {
			Connection conn = getConnection();
			
			PreparedStatement ps = conn.prepareStatement("select * from songs where " +criteria +" = ?");
			ps.setString(1, equals);
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			System.out.println(rs);
			ObservableList<Song>songList =FXCollections.observableArrayList(); 
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String artistBand = rs.getString("artistBand");
				double duration=rs.getDouble("duration");
				String type = rs.getString("type");
				String youtube_link = rs.getString("youtube_link");
				songList.add(new Song(id, title, artistBand, duration, type, youtube_link));
			}
			closeConnection(conn);
			return songList;
		} catch (SQLException e) {
			return null;
		}
	}
	
	public ObservableList<Song> getSongs() {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from songs");
			ResultSet rs = ps.executeQuery();
			ObservableList<Song>songList =FXCollections.observableArrayList();  
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String artistBand = rs.getString("artistBand");
				double duration=rs.getDouble("duration");
				String type = rs.getString("type");
				String youtube_link = rs.getString("youtube_link");
				songList.add(new Song(id, title, artistBand, duration, type, youtube_link));
			}
			closeConnection(conn);
			return songList;
		} catch (SQLException e) {
			return null;
		}
	}
	
	
	
	public ObservableList<Playlist> getPlaylists() {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from playlistname");
			ResultSet rs = ps.executeQuery();
			ObservableList<Playlist> playList =FXCollections.observableArrayList();  
			while (rs.next()) {
				String PlaylistName = rs.getString("PlaylistName");
				int PlaylistId =rs.getInt("id");
				playList.add(new Playlist(PlaylistId, PlaylistName));
			}
			closeConnection(conn);
			return playList;
		} catch (SQLException e) {
			return null;
		}
	}


	public void createPlaylist(Playlist playlist) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("insert into playlistname(playlistname) value (?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, playlist.getPlaylistName());
			ps.executeUpdate();
			ps.getGeneratedKeys();
			
			System.out.println("Playlist creat cu succes");
			closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
	public int getIdByArtistTitle(String title, String artist) {
		try {
			Connection conn = getConnection();
			
			PreparedStatement ps = conn.prepareStatement("select id from songs where title = '" +title +"' and artistBand = '" +artist + "'");
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			int id = 0 ;
			 while (rs.next()) {
				id = rs.getInt(1);
			 }
			
			return id;
		} catch (SQLException e) {
			return 00000;
		}
	}
	
	public ArrayList<Integer> getIdsByArtist(String artist) {
		try {
			Connection conn = getConnection();
			
			PreparedStatement ps = conn.prepareStatement("select * from songs where artistBand = '" +artist+ "'");
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			ArrayList<Integer> ids = new ArrayList<>();
			 while (rs.next()) {
				int id = rs.getInt(1);
				ids.add(id);
			 }
			
			return ids;
		} catch (SQLException e) {
			return null;
		}
	}

	
	


	
	public void addAllSongsToPlaylistByArtist(int idPlaylist, String ArtistBand) {
		try {
			Connection conn = getConnection();
			ArrayList<Integer> ids = getIdsByArtist(ArtistBand);
			for(int idSong:ids){
				boolean CheckIfSongExist = CheckIfSongExist(idPlaylist, idSong);
				if(CheckIfSongExist ==false) {
					PreparedStatement ps = conn.prepareStatement("insert into playlist(idPlaylist, idSong) value ("+idPlaylist +", " + idSong +")",
							Statement.RETURN_GENERATED_KEYS);
					System.out.println(ps);
					ps.executeUpdate();
					ps.getGeneratedKeys();
				}
				else {
				System.out.println("Cantecul cu id " + idSong +" exista deja in playlistul "+ idPlaylist);
				}
			}
			
			System.out.println("Toate melodiile artistului " + ArtistBand+ " au fost adaugate cu succes la playlist");
			
			closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

	
		

		
		
		public List<Integer> getAllSongsInPlaylist(Integer playlistId) {
			try {
				Connection conn = getConnection();
				
				PreparedStatement ps = conn.prepareStatement("select * from playlist where idPlaylist = ?");
				ps.setInt(1, playlistId);
				System.out.println(ps);
				ResultSet rs = ps.executeQuery();
				List<Integer> SongsIds = new ArrayList<Integer>();
				while (rs.next()) {
					int id = rs.getInt("idSong");
					SongsIds.add(id);
				}
				closeConnection(conn);
				return SongsIds;
			} catch (SQLException e) {
				return null;
			}
		}
		
		public Song getSongById(int id) {
			try {
				Connection conn = getConnection();
				
				PreparedStatement ps = conn.prepareStatement("select * from songs where id = ?");
				ps.setInt(1, id);
				System.out.println(ps);
				ResultSet rs = ps.executeQuery();
				Song s1 =new Song();
				while (rs.next()) {
					int idbd = rs.getInt("id");
					String title = rs.getString("title");
					String artistBand = rs.getString("artistBand");
					double durationdb=rs.getDouble("duration");
					String type = rs.getString("type");
					String youtube_link = rs.getString("youtube_link");
					s1.setDuration(durationdb);
					s1.setArtistBand(artistBand);
					s1.setId(idbd);
					s1.setTitle(title);
					s1.setType(type);
					s1.setYoutube_link(youtube_link);
				}
				closeConnection(conn);
				return s1;
			} catch (SQLException e) {
				return null;
			}
		}
		
		
		public void addPlaylist(String playlistName) {
			try {
				Connection conn = getConnection();
				PreparedStatement ps = conn.prepareStatement("insert into playlistname(PlaylistName) values(?)",
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, playlistName);
				ps.executeUpdate();
				ps.getGeneratedKeys();
				closeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public boolean addSongToPlaylistByArtistTitle(int id, String Title, String Artist) {
			try {
				Connection conn = getConnection();
				int idSong = getIdByArtistTitle(Title, Artist);
				boolean CheckIfSongExist= CheckIfSongExist(id, idSong);
				System.out.println(CheckIfSongExist);
				if(CheckIfSongExist == false) {
				PreparedStatement ps = conn.prepareStatement("insert into playlist(idPlaylist, idSong) value (?, ?)");
				ps.setInt(1, id);
				ps.setInt(2, idSong);
				
				
				
				int affectedRows = ps.executeUpdate();
				closeConnection(conn);
				System.out.println("Melodie adaugata cu succes la playlist");
				return affectedRows == 1;
				}
				else {
					System.out.println("Melodie exista deja in playlist");
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			
		}
		
		public boolean CheckIfSongExist(int idPlaylist, int idSong) {
			try {
				Connection conn = getConnection();
				
				PreparedStatement ps = conn.prepareStatement("select * from playlist where idPlaylist = " +idPlaylist +" and idSong = " +idSong );
				System.out.println(ps);
				ResultSet rs = ps.executeQuery();
				ArrayList<Integer> listaCanteceCuAcelasiNumeSiTitlu = new ArrayList<>();
				 while (rs.next()) {
					int id = rs.getInt(1);
					listaCanteceCuAcelasiNumeSiTitlu.add(id);
				 }

				System.out.println(listaCanteceCuAcelasiNumeSiTitlu);
				closeConnection(conn);
				if(listaCanteceCuAcelasiNumeSiTitlu.isEmpty()) {
					return false;
				}
				else {
					return true;
				}

			} catch (SQLException e) {
				return false;
			}
			
		}
		
		public ObservableList<Song> getSongsInPlaylist(int playlistid) {
			try {
				Connection conn = getConnection();
				
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM aplicatiemuzica.playlist inner join songs on playlist.idSong=songs.id where idPlaylist =?" );
				ps.setInt(1, playlistid);
				System.out.println(ps);
				ResultSet rs = ps.executeQuery();
				System.out.println(rs);
				ObservableList<Song>songList =FXCollections.observableArrayList(); 
				while (rs.next()) {
					int id = rs.getInt("idSong");
					String title = rs.getString("title");
					String artistBand = rs.getString("artistBand");
					double duration=rs.getDouble("duration");
					String type = rs.getString("type");
					String youtube_link = rs.getString("youtube_link");
					songList.add(new Song(id, title, artistBand, duration, type, youtube_link));
				}
				closeConnection(conn);
				return songList;
			} catch (SQLException e) {
				return null;
			}
		}
		

			
		

}
