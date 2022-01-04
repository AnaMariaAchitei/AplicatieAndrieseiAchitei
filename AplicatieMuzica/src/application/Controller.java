package application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller implements Initializable{
	
	//MENIU
	
	 @FXML
	    private Button btnPageAddSong;

	    @FXML
	    private Button btnPagePlaylist;

	    @FXML
	    private Button btnPagePlaylistPlay;

	   
	    
  
    
    @FXML
    public void PageAddSongs() throws IOException{
    	Parent root = FXMLLoader.load(getClass().getResource("AllSongs.fxml"));
		Stage window = (Stage) btnPageAddSong.getScene().getWindow();
		window.setScene(new Scene(root, 1300, 800));
    }

    @FXML
    public void pagePlaylist() throws IOException{
    	Parent root = FXMLLoader.load(getClass().getResource("AllPlaylists.fxml"));
		Stage window = (Stage) btnPagePlaylist.getScene().getWindow();
		window.setScene(new Scene(root, 1300, 800));
    }
    
    @FXML
    void pagePlaylistPlay() throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("PlaylistBrowser.fxml"));
		Stage window = (Stage) btnPagePlaylistPlay.getScene().getWindow();
		window.setScene(new Scene(root, 1300, 800));
    }
    
    @FXML
    private Button btn_addSong;
    @FXML
    void handleBtnAddSongPage() throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("AddSong.fxml"));
		Stage window = (Stage) btn_addSong.getScene().getWindow();
		window.setScene(new Scene(root, 1300, 800));
    }
    
    
    
    
    SongDAOFactory songDAOFactory = new SongDAOFactory();
	SongDAO eDAO = songDAOFactory.createSongDAO();
	
	
    @FXML
    void handleRandom() throws IOException, URISyntaxException {
    	
    	
    	ObservableList<Song> allSongs = eDAO.getSongs();
    	List<Integer> listaId = new ArrayList<Integer>();
    	for (Song song : allSongs) {
            listaId.add(song.getId());
        }
        Random rand = new Random();
        int randomElement = listaId.get(rand.nextInt(listaId.size()));
       Song cantecAles =  eDAO.getSongById(randomElement);
        Desktop.getDesktop().browse(new URI(cantecAles.getYoutube_link()));
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	


	
	

		

	


}
