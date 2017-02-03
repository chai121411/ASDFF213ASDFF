package sl.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import sl.model.Song;

public class SongController {
	@FXML Button addButton;
	@FXML Button deleteButton;
	@FXML Button editButton;
	@FXML Button cancelButton;
	@FXML Button saveButton;
	@FXML TextField songName;
	@FXML TextField artist;
	@FXML TextField album;
	@FXML TextField year;
	@FXML Label req1;//if user does not put first two column , then it will show up
	@FXML Label req2;
	@FXML Label opt1;
	@FXML Label opt2;


	ListView<Song> listView = new ListView<Song>();

	
	public void click(ActionEvent e) {
		 
		String str = null;
		Button b = (Button)e.getSource(); //each button has a unique fxml_id
		
		if (b == addButton) {
			songName.requestFocus();;
			listView.getSelectionModel().clearSelection();//no selection for any item
			clear4field();//clear the 4 fields on the right
			editable_4_fields(true);//set 4 fields editable






		} else if (b == deleteButton) {
			str = "delete, hi josh";	
		} else if (b == editButton) {
			str = "edit, hi josh";
		} else if (b == cancelButton) {
			str = "cancel, hi josh";
		} else if (b == saveButton) {
			checkEmptyLable();

		}
		

		
	}

	public void clear4field(){
		songName.clear();
		artist.clear();
		album.clear();
		year.clear();
	}

	public void editable_4_fields(boolean flag){
		songName.setEditable(flag);
		artist.setEditable(flag);
		album.setEditable(flag);
		year.setEditable(flag);
	}

	public boolean checkEmptyLable(){
		String songname = songName.getText();
		String artistName = artist.getText();
		String year_str = year.getText();
		String album_str = album.getText();

		if(songname.equals("") || artistName.equals("")){
			req1.setVisible(true);
			req2.setVisible(true);
		}

		if(!songname.equals("") && !artistName.equals("")){
			req1.setVisible(false);
			req2.setVisible(false);
		}

		return true;
	}
	
}
