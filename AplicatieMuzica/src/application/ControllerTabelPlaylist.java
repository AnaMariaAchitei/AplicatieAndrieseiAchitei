package application;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;



public class ControllerTabelPlaylist implements Initializable{

	@FXML
    private Button btn_addPlayList;

    @FXML
    private Button btn_addSong;

    @FXML
    private Button btn_addSongs;

    @FXML
    private TableColumn<?, ?> countSongs;

    @FXML
    private TableColumn<Playlist, Integer> id;

    @FXML
    private TableColumn<Playlist, String> playlistName;

    @FXML
    private TableView<Playlist> tablePlaylist;

    @FXML
    private TextField tf_2_artist;

    @FXML
    private TextField tf_2_namesong;

    @FXML
    private TextField tf_3_namesong;

    @FXML
    private TextField tf_newPlaylist;
    

    @FXML
    private Button btn_seeSongs;

    @FXML
    void handleBtnAddPlaylist() {
    	eDAO.addPlaylist(tf_newPlaylist.getText());
    	tf_newPlaylist.clear();
    	tablePlaylist.setItems(eDAO.getPlaylists());
    }
    
    
    @FXML
    private Label myLabel;
    @FXML
    void handleBtnAddSong() {
    	
    	 boolean error = false;
		 if(tf_2_namesong.getText().length()==0) {
			 tf_2_namesong.setStyle("-fx-border-color:#fa7169;fx-border-width:2px");
			 new animatefx.animation.Shake(tf_2_namesong).play();
			 error = true;
		 }
		 if(tf_2_artist.getText().length()==0) {
			 tf_2_artist.setStyle("-fx-border-color:#fa7169;fx-border-width:2px");
			 new animatefx.animation.Shake(tf_2_artist).play();
			 error = true;
		 }

	    ObservableList<Playlist> selectedPlaylist=tablePlaylist.getSelectionModel().getSelectedItems();
		 if(selectedPlaylist.isEmpty()) {
		        Alert a = new Alert(AlertType.ERROR);
		        a.setContentText("Nu ati selectat niciun playlist");
		        a.show();
		        error = true;
		 }
		if(error ==false) {
	    	int idPlaylist=-999;
	    	for(Playlist playlist : selectedPlaylist) {
	    		idPlaylist=playlist.getId();
	    	}

    	eDAO.addSongToPlaylistByArtistTitle(idPlaylist, tf_2_namesong.getText(), tf_2_artist.getText());
    	
    	handleBtnSeeSongs();
    	
    	tf_2_namesong.clear();tf_2_artist.clear();
    	 Alert a = new Alert(AlertType.CONFIRMATION);
		        a.setContentText("Melodie adaugata cu succes la playlistul selectat");
		        a.show();
		}
    }

    @FXML
    void handleBtnAddSongs() {
    	 boolean error = false;
		 if(tf_3_namesong.getText().length()==0) {
			 tf_3_namesong.setStyle("-fx-border-color:#fa7169;fx-border-width:2px");
			 new animatefx.animation.Shake(tf_3_namesong).play();
			 error = true;
		 }

		 ObservableList<Playlist> selectedPlaylist=tablePlaylist.getSelectionModel().getSelectedItems();
		 if(selectedPlaylist.isEmpty()) {
			 error=true;
			 Alert a = new Alert(AlertType.ERROR);
		     a.setContentText("Nu ati selectat niciun playlist");
		     a.show();
		 }
		 if(error == false) {
			 int idPlaylist=-999;
		    	for(Playlist playlist : selectedPlaylist) {
		    		idPlaylist=playlist.getId();
		    	}

	    	eDAO.addAllSongsToPlaylistByArtist(idPlaylist, tf_3_namesong.getText());
	    	
			 
			 handleBtnSeeSongs();
			 tf_3_namesong.clear();
			 Alert a = new Alert(AlertType.CONFIRMATION);
		     a.setContentText("Melodii adaugate cu succes la playlistul selectat");
		     a.show();
		
		 }
    }
    
    @FXML
    private  TableView<Song> tableSong;

    @FXML
    private TableColumn<Song, String> artistBand;

    @FXML
    private TableColumn<Song, String> title;

    @FXML
    private TableColumn<Song, String> type;

    @FXML
    private TableColumn<Song, String> youtube_link;
    
    @FXML
    private TableColumn<Song, Integer> idSong;

    @FXML
    private TableColumn<Song, Double> duration;

    
    SongDAOFactory songDAOFactory = new SongDAOFactory();
	SongDAO eDAO = songDAOFactory.createSongDAO();
	
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		playlistName.setCellValueFactory(new PropertyValueFactory<Playlist, String >("playlistName"));
		id.setCellValueFactory(new PropertyValueFactory<Playlist, Integer >("id"));
		tablePlaylist.setItems(eDAO.getPlaylists());
		
		idSong.setCellValueFactory(new PropertyValueFactory<Song, Integer>("id"));
		title.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
		artistBand.setCellValueFactory(new PropertyValueFactory<Song, String>("artistBand"));
		duration.setCellValueFactory(new PropertyValueFactory<Song, Double>("duration"));
		type.setCellValueFactory(new PropertyValueFactory<Song, String>("type"));
		youtube_link.setCellValueFactory(new PropertyValueFactory<Song, String>("youtube_link"));
		tableSong.setItems(eDAO.getSongs());
	}

    @FXML
    void handleBtnSeeSongs() {
    	ObservableList<Playlist> selectedPlaylist;
    	selectedPlaylist=tablePlaylist.getSelectionModel().getSelectedItems();
    	int id=-999;
    	
    	for (Playlist playlist : selectedPlaylist) {
            id = playlist.getId();
        }
    	List<Integer> listaId = eDAO.getAllSongsInPlaylist(id);
    	ObservableList<Song>melodiiDinPlaylist =FXCollections.observableArrayList(); 
    	for (Integer idd : listaId) {
    		Song song=eDAO.getSongById(idd);
    		melodiiDinPlaylist.add(song);
        }
    	
    	tableSong.setItems(melodiiDinPlaylist);
    }
   
    
    @FXML
    private Button btn_backToMenu;
    @FXML
    void handleBackToMenu() throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("Meniu.fxml"));
		Stage window = (Stage) btn_backToMenu.getScene().getWindow();
		window.setScene(new Scene(root, 1300, 800));
    }
    
    @FXML
    void handleRefreshAllSongs() {
    	tableSong.setItems(eDAO.getSongs());
    }
    


}
