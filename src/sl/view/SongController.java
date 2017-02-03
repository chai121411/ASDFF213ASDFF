package sl.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
	
	public void click(ActionEvent e) {
		 
		String str = null;
		Button b = (Button)e.getSource(); //each button has a unique fxml_id
		
		if (b == addButton) {
			str = "add, hi josh";	
		} else if (b == deleteButton) {
			str = "delete, hi josh";	
		} else if (b == editButton) {
			str = "edit, hi josh";
		} else if (b == cancelButton) {
			str = "cancel, hi josh";
		} else if (b == saveButton) {
			str = "save, hi josh";
		}
		
		songName.setText(str);
		artist.setText(str);
		album.setText(str);
		year.setText(str);
		
	}
	
}
