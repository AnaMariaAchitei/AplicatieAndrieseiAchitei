package application;


import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ControllerBrowserPlaylist implements Initializable{

	


    @FXML
    private TableColumn<Playlist, Integer> id;

    @FXML
    private TableColumn<Playlist, String> playlistName;

    @FXML
    private TableView<Playlist> tablePlaylist;

    
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
    
    @FXML
    private WebView webView;
    private WebEngine engine;
    
    @FXML
    private Button btn_playSongExtern;

    @FXML
    private Button btn_playSongIntern;

    
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
		
		engine = webView.getEngine();
		textFieldBrowser.setText("https://www.youtube.com/embed/_4kHxtiuML0");
		engine.load("https://www.youtube.com/embed/_4kHxtiuML0");
	}
	
	 @FXML
	    void handleBtnPlaySong() {
		 
	    	ObservableList<Song> selectedSong=tableSong.getSelectionModel().getSelectedItems();
			 
			 boolean error=false;
		    	 if(selectedSong.isEmpty() ) {
					 error = true;
						// create a alert
				        Alert a = new Alert(AlertType.ERROR);
				        a.setContentText("Nu ati selectat niciun cantec");
				        a.show();
				 }
				 if(error ==false) {
					 String youtube_link ="";
					 for (Song song : selectedSong) {
				            youtube_link = song.getYoutube_link();
				        }
			            engine.load(youtube_link);
				 }
			
	    }

	    @FXML
	    void handleBtnPlaySongEntern() throws IOException, URISyntaxException {
	    	ObservableList<Song> selectedSong=tableSong.getSelectionModel().getSelectedItems();
			 
			 boolean error=false;
		    	 if(selectedSong.isEmpty() ) {
					 error = true;
						// create a alert
				        Alert a = new Alert(AlertType.ERROR);
				        a.setContentText("Nu ati selectat niciun cantec");
				        a.show();
				 }
				 if(error ==false) {
					 String youtube_link ="";
					 for (Song song : selectedSong) {
				            youtube_link = song.getYoutube_link();
				        }
			    		Desktop.getDesktop().browse(new URI(youtube_link));
				 }
	    }
	    

	@FXML
	void handleRefreshAllSongs() {
	  tableSong.setItems(eDAO.getSongs());
	}
	
    @FXML
    private Button btn_Play;
    
    @FXML
    private TextField textFieldBrowser;
	
    @FXML 
    void handleBtnPlayPlaylist() throws InterruptedException {
        	boolean error=false;
        	ObservableList<Playlist> selectedPlaylist;
        	selectedPlaylist=tablePlaylist.getSelectionModel().getSelectedItems();
        	 if(selectedPlaylist.isEmpty() ) {
    			 error = true;
    		        
    			 Alert a = new Alert(AlertType.ERROR);
    		     a.setContentText("Nu ati selectat niciun playlist");
    		     a.show();
    		 }
    		 if(error ==false) {
    			 
    		   int idPlaylist=-999;
    		    	
    		    for (Playlist playlist : selectedPlaylist) {
    		      idPlaylist = playlist.getId();
    		    }
    		    	
    		   List<Integer> listaId = eDAO.getAllSongsInPlaylist(idPlaylist);
    		   ObservableList<Song> SongsInPlaylist = eDAO.getSongsInPlaylist(idPlaylist);
    		   
    		  
    	    	for (Integer idd : listaId) {
    	    		Song song=eDAO.getSongById(idd);
    	    		SongsInPlaylist.add(song);
    	        }
    	    	
    	    	tableSong.setItems(SongsInPlaylist);
    	    	
    		   double durationDelay =0;
    		   
    		   
    		   for (Song song : SongsInPlaylist) {
    		    		String URL = song.getYoutube_link();
    		    		engine.load(URL);
    		    		textFieldBrowser.setText(URL);
    					double number= song.getDuration();
    					int minute=(int) number ;
    					double secunde= (number - minute);
    					
    					durationDelay = (minute+secunde)*60000;
//    					Thread.sleep( (long) durationDelay);
    			
//    			 new java.util.Timer().schedule(new TimerTask(){
//    			        @Override
//    			        public void run() {
////    			            String URL = song.getYoutube_link();
////        		    		textFieldBrowser.setText(URL);
//    			        }
//    			    },(long) 5000); 
    		   }
    		 }
        }


    

    
    
   
    @FXML
    private Label myLabel;	
	

    @FXML
    void handlePLay() {
		engine.load(textFieldBrowser.getText());
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
    void handleBtnPlayPlaylistBrowser() throws IOException, URISyntaxException, InterruptedException {
    	boolean error=false;
    	ObservableList<Playlist> selectedPlaylist;
    	selectedPlaylist=tablePlaylist.getSelectionModel().getSelectedItems();
    	 if(selectedPlaylist.isEmpty() ) {
			 error = true;
		        
			 Alert a = new Alert(AlertType.ERROR);
		     a.setContentText("Nu ati selectat niciun playlist");
		     a.show();
		 }
		 if(error ==false) {
			 
		   int idPlaylist=-999;
		    	
		    for (Playlist playlist : selectedPlaylist) {
		      idPlaylist = playlist.getId();
		    }
		    	
		   List<Integer> listaId = eDAO.getAllSongsInPlaylist(idPlaylist);
		   ObservableList<Song> SongsInPlaylist = eDAO.getSongsInPlaylist(idPlaylist);
	    	for (Integer idd : listaId) {
	    		Song song=eDAO.getSongById(idd);
	    		SongsInPlaylist.add(song);
	        }
	    	
	    	tableSong.setItems(SongsInPlaylist);
	    	
		   double durationDelay =0;
		   for (Song song : SongsInPlaylist) {
		    		String URL = song.getYoutube_link();
		    		Desktop.getDesktop().browse(new URI(URL));
			double number= song.getDuration();
			int minute=(int) number ;
			double secunde= (number - minute);
					
			durationDelay = (minute+secunde)*60000 +5000; //5 secunde in plus pentru play si inchidere
			System.out.println(duration);
			Thread.sleep( (long) durationDelay);
			
			
		   }
		 }
    }
    
    @FXML
    private Button btn_backToMenu;
    @FXML
    void handleBackToMenu() throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("Meniu.fxml"));
		Stage window = (Stage) btn_backToMenu.getScene().getWindow();
		window.setScene(new Scene(root, 1300, 800));
    }
    
    
}
