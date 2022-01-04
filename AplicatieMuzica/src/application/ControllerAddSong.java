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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerAddSong implements Initializable{
	
	
	//Add SONG
	

    @FXML
    private Button btnAddInputComboBox;

    @FXML
    private Button btnAddSong;

    @FXML
    private TextField textFieldArtistBand;

    @FXML
    private TextField textFieldDuratie;

    @FXML
    private TextField textFieldNameSong;

    @FXML
    private Button btnRenunta;


    @FXML
    private TextField textFieldYoutubeLink;
    
    @FXML
    private ComboBox<String> comboBox =	new ComboBox<String>();
    
    @FXML
    private TextField textFieldType;
	
    
    ObservableList<String> Type = FXCollections.observableArrayList("Jazz", "Rock", "Pop", "Clasic", "Tehno");
    
    @FXML
    void addInputToComboBox() {
    	Type.add(textFieldType.getText());
    	textFieldType.clear();
    }
    

	@Override
	public void initialize(URL url, ResourceBundle resourceBoundle) {
		comboBox.setItems(Type);
		comboBox.getSelectionModel().selectFirst();
		
		
	}
	
    @FXML
    void handlebtnRenunta() throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("AllSongs.fxml"));
		Stage window = (Stage) btnRenunta.getScene().getWindow();
		window.setScene(new Scene(root, 1300, 800));
    }
    
		SongDAOFactory songDAOFactory = new SongDAOFactory();
		SongDAO eDAO = songDAOFactory.createSongDAO();

	 
		 @FXML
		 void handlebtnAddSong() throws IOException {
			 boolean error = false;
			 if(textFieldNameSong.getText().length()==0) {
				 textFieldNameSong.setStyle("-fx-border-color:#fa7169;fx-border-width:2px");
				 new animatefx.animation.Shake(textFieldNameSong).play();
				 error = true;
			 }
			 else {
				 textFieldNameSong.setStyle(null);
				 error = false;
			 }
			 
			 if(textFieldArtistBand.getText().length()==0) {
				 textFieldArtistBand.setStyle("-fx-border-color:#fa7169;fx-border-width:2px");
				 new animatefx.animation.Shake(textFieldArtistBand).play();
				 error = true;
			 }
			 else {
				 textFieldArtistBand.setStyle(null);
				 error = false;
			 }
			 
			 if(textFieldDuratie.getText().length()==0 || textFieldDuratie.getText().matches("^[0-9]+\\.?[0-9]*$")==false ) {
				 textFieldDuratie.setStyle("-fx-border-color:#fa7169;fx-border-width:2px");
				 new animatefx.animation.Shake(textFieldDuratie).play();
				 error = true;
			 }
			 else {
				 textFieldDuratie.setStyle(null);
				 error = false;
			 }
			 
			 if(textFieldYoutubeLink.getText().length()==0 || textFieldYoutubeLink.getText().matches("http(?:s?):\\/\\/(?:www\\.)?youtu(?:be\\.com\\/watch\\?v=|\\.be\\/)([\\w\\-\\_]*)(&(amp;)?‌​[\\w\\?‌​=]*)?")==false  ) {
				 textFieldYoutubeLink.setStyle("-fx-border-color:#fa7169;fx-border-width:2px");
				 new animatefx.animation.Shake(textFieldYoutubeLink).play();
				 error = true;
			 }
			 else {
				 textFieldYoutubeLink.setStyle(null);
				 error = false;
			 }
			 
			 if(error ==false) {
				Song s1=new Song(textFieldNameSong.getText(), textFieldArtistBand.getText() , Double.parseDouble(textFieldDuratie.getText()), comboBox.getValue().toString() , textFieldYoutubeLink.getText());
				   
				eDAO.addSong(s1);
				   
				
				
				// create a alert
		        Alert a = new Alert(AlertType.CONFIRMATION);
		        a.setContentText("Cantec adaugat cu succes în baza de date");
		        a.show();
		        
		        Parent root = FXMLLoader.load(getClass().getResource("AllSongs.fxml"));
				Stage window = (Stage) btnAddSong.getScene().getWindow();
				window.setScene(new Scene(root, 1300, 800));
				
			 }

		 }

		 
		

			

		


}
