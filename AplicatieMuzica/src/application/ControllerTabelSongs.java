package application;


import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ControllerTabelSongs implements Initializable{

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
	    private TableColumn<Song, Integer> id;

	    @FXML
	    private TableColumn<Song, Double> duration;
	    
	    @FXML
	    private ComboBox<String> ComboBox_filter =	new ComboBox<String>();

	    @FXML
	    private TextField TextField_filter;


	    @FXML
	    private Button btn_filter;
	    
	    

		SongDAOFactory songDAOFactory = new SongDAOFactory();
		SongDAO eDAO = songDAOFactory.createSongDAO();

		
		 ObservableList<String> CapeteTabel = FXCollections.observableArrayList("id", "title", "artistBand", "type", "youtube_link");
		    
		@Override
		public void initialize(URL url, ResourceBundle resourceBoundle) {
			
			id.setCellValueFactory(new PropertyValueFactory<Song, Integer>("id"));
			title.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
			artistBand.setCellValueFactory(new PropertyValueFactory<Song, String>("artistBand"));
			duration.setCellValueFactory(new PropertyValueFactory<Song, Double>("duration"));
			type.setCellValueFactory(new PropertyValueFactory<Song, String>("type"));
			youtube_link.setCellValueFactory(new PropertyValueFactory<Song, String>("youtube_link"));
			tableSong.setItems(eDAO.getSongs());
//			tableSong.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			ComboBox_filter.setItems(CapeteTabel);

		}

		    @FXML
		    private Button btn_deleteSong;

		
		    @FXML
		    private Button btn_addSong;
		    @FXML
		    void handleBtnAddSongPage() throws IOException {
		    	Parent root = FXMLLoader.load(getClass().getResource("AddSong.fxml"));
				Stage window = (Stage) btn_addSong.getScene().getWindow();
				window.setScene(new Scene(root, 1300, 800));
		    }
		    

		    @FXML
		    void handleBtnDeleteSong() {
		    	ObservableList<Song> allSongs, selectedSong;
		    	allSongs=tableSong.getItems();
		    	selectedSong=tableSong.getSelectionModel().getSelectedItems();
		    	int id=-999;

		    	for (Song song : selectedSong) {
		            id = song.getId();
		        }
		    	
		    	eDAO.deleteSongFromPlaylist(id);
		    	eDAO.delete(id);
		    	
		    	 Alert a = new Alert(AlertType.CONFIRMATION);
			     a.setContentText("Cantec sters cu succes din baza de date");
			     Image image = new Image("http://www.mirunaandriesei.ro/java/favicon.png");
			     ImageView imageView = new ImageView(image);
			     a.setGraphic(imageView);
			   
			     a.show();
		    	
		    	selectedSong.forEach(allSongs::remove);
		    }
		    
		    
		   
		    
		    @FXML
		    void handleBtnFilter() {
		    	
		    	 boolean error = false;
				 if(TextField_filter.getText().length()==0) {
					 TextField_filter.setStyle("-fx-border-color:#fa7169;fx-border-width:2px");
					 new animatefx.animation.Shake(TextField_filter).play();
					 error = true;
				 }
				 else if(ComboBox_filter.getValue().toString() == "Alege coloana") {
					 ComboBox_filter.setStyle("-fx-border-color:#fa7169;fx-border-width:2px");
					 new animatefx.animation.Shake(ComboBox_filter).play();
					 error = true;
				 }
				 if(error == false) {
					 String capTabel=ComboBox_filter.getValue().toString();
					 String value = TextField_filter.getText();
		    	
					 tableSong.setItems(eDAO.getAllSongsByCriteria(capTabel, value));
				 }
		    }
		    
		    @FXML
		    void handleRefreshAllSongs() {
		    	tableSong.setItems(eDAO.getSongs());
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
