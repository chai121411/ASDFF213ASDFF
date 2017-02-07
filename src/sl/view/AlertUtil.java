package sl.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

/*
 * Min Chai
 * Josh Su
 */

/*
 * Utility class to handle errors/confirmations
 */
public class AlertUtil {
	private AlertUtil() {}
	
	public static void invalidSong() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Invalid Song");
		alert.setContentText("Cannot add another song with the same song name and artist.");
		alert.showAndWait();
	}
	
	public static void invalidYear(String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Invalid year entered.");
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public static void noSongSelected(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("No Selection");
		alert.setContentText("Unable to delete. Please select a song to delete.");
		alert.showAndWait();
	}
	
	public static boolean confirmEdit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("?");
		alert.setHeaderText("Delete Song");
		alert.setContentText("Are you sure you would like to edit this song from the library?"); 
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			 return true;
		}
		return false;
	}
	
	public static boolean confirmSave() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("?");
		alert.setHeaderText("Save Song");
		alert.setContentText("Are you sure you would like to save this song into the library?"); 
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			 return true;
		}
		return false;
	}
	
	public static boolean confirmDelete() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("?");
		alert.setHeaderText("Delete Song");
		alert.setContentText("Are you sure you would like to delete this song from the library?"); 
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			 return true;
		}
		return false;
	}
}
