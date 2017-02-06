package sl.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import sl.model.Song;

//Cannot add same song(same song name and same artist)
     //Implement a way to look inside list
//When you a delete a song, highlight next song in the list, otherwise highlight previous
//Make a utility method that highlights a song.
//Once a song is added, the newly added song should be selected
//Persistence

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
	@FXML Label labelSongName;//if user does not put first two column , then it will show up
	@FXML Label labelArtist;
	@FXML Label labelAlbum;
	@FXML Label labelYear;
	

	@FXML ListView<Song> listView = new ListView<Song>();
	ArrayList<Song> songArrayList = new ArrayList<>();
	private int index = 0;
	private int in = 0;
	public ObservableList <Song> observableList;

	public void put_list_to_view() {

		observableList = FXCollections.observableList(songArrayList);
		listView.setItems(observableList);

		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
			@Override
			public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
				show();
				in = listView.getSelectionModel().getSelectedIndex();
				if (in >= 0)
					index = in;
			}
		});

		listView.setCellFactory(param -> new ListCell<Song>() {
			protected void updateItem(Song newSong , boolean flag) {
				super.updateItem(newSong, flag);

				if (flag || newSong == null || newSong.getArtist() == null) {
					setText(null);
				} else {
					setText(newSong.getSongName());
				}
			}
		});

		if (!songArrayList.isEmpty())
			listView.getSelectionModel().select(0);
	}
	
	public void click(ActionEvent e) {
		 
		String str = null;
		Button b = (Button)e.getSource(); //each button has a unique fxml_id
		
		if (b == addButton) {
			songName.requestFocus();;
			listView.getSelectionModel().clearSelection();//no selection for any item
			clear4field();//clear the 4 fields on the right
			editable_4_fields(true);//set 4 fields editable
			showCancelSave();
			
		} else if (b == deleteButton) {
			if (confirmDelete()) {
				int n = getSelectedIndex();
				songArrayList.remove(n);
				clear4field();
				up();
				listView.getSelectionModel().select(n-1);
			}

		} else if (b == editButton) {
			str = "edit, hi josh";
		} else if (b == cancelButton) {
			listView.getSelectionModel().clearSelection();//no selection for any item
			disableRightPane();
		} else if (b == saveButton) {
			if (!checkEmptyLable()) {
				return;
			} else {
				addSong2ListView();
			}
		}
	}

	public void addSong2ListView() {
		String songname = songName.getText();
		String artistName = artist.getText();
		String year_str = year.getText();
		String album_str = album.getText();
		
		int k = 0;
		Song newSong = null;
		
		try {
			if (!year_str.equals("")) {
				k = Integer.parseInt(year_str);
				if (k < 0) {
				    invalidYearAlert("You entered a negative number");
				    return;
				}
			}
		} catch (NumberFormatException e) {
			invalidYearAlert("You did not enter a year in numbers");
			return;
		}
		
		if (year_str.equals("") && album_str.equals("")) {
			newSong = new Song(songname, artistName);
		} else if (year_str.equals("")) {
			newSong = new Song(songname, artistName, album_str);
		} else if (album_str.equals("")) {
			newSong = new Song(songname, artistName, k);
		} else {
			newSong = new Song(songname, artistName, album_str, k);
		}
		
		for (Song s: songArrayList) {
			if (s.equals(newSong)) {
				alertInvalidSong();
				return;
			}
		}
		
		songArrayList.add(newSong);
		observableList = FXCollections.observableArrayList(songArrayList);
		listView.setItems(observableList);
		up();
		disableRightPane();
	}

	private void alertInvalidSong() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Invalid Song");
		alert.setContentText("Cannot add another song with the same song name and artist.");
		alert.showAndWait();
	}

	private void clear4field() {
		songName.clear();
		artist.clear();
		album.clear();
		year.clear();
	}

	private void editable_4_fields(boolean flag) {
		if (flag == false) {
			labelSongName.setTextFill(Color.GREY);
			labelArtist.setTextFill(Color.GREY);
			labelAlbum.setTextFill(Color.GREY);
			labelYear.setTextFill(Color.GREY);
		} else {
			labelSongName.setTextFill(Color.BLACK);
			labelArtist.setTextFill(Color.BLACK);
			labelAlbum.setTextFill(Color.BLACK);
			labelYear.setTextFill(Color.BLACK);
		}
		songName.setEditable(flag);
		artist.setEditable(flag);
		album.setEditable(flag);
		year.setEditable(flag);
	}

	private boolean checkEmptyLable() {
		String songname = songName.getText();
		String artistName = artist.getText();
		String year_str = year.getText();
		String album_str = album.getText();

		if (songname.equals("") || artistName.equals("")) {
			req1.setVisible(true);
			req2.setVisible(true);
			return false;
		} else {
			req1.setVisible(false);
			req2.setVisible(false);
		}
		return true;
	}

	public void show() {
		try {
			songName.setText(listView.getSelectionModel().getSelectedItem().getSongName());
			artist.setText(listView.getSelectionModel().getSelectedItem().getArtist());
			album.setText(listView.getSelectionModel().getSelectedItem().getAlbum());
			int k = listView.getSelectionModel().getSelectedItem().getYear();
			if (k >= 0) {
				year.setText(Integer.toString(k));
			} else {
				year.clear();
			}

		} catch (NullPointerException s) {
		}
	}
	
	public void hideCancelSave() {
		cancelButton.setVisible(false);
		saveButton.setVisible(false);
	}
	
	public void showCancelSave() {
		cancelButton.setVisible(true);
		saveButton.setVisible(true);
	}
	
	public void disableRightPane() {
		clear4field();//clear the 4 fields on the right
		editable_4_fields(false);//set 4 fields uneditable
		hideCancelSave();
	}
	
	public void invalidYearAlert(String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Invalid year entered");
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public boolean confirmDelete() {
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

	public int getSelectedIndex() {
		return listView.getSelectionModel().getSelectedIndex();
	}

	public void up() {
		Collections.sort(songArrayList, new Song.Comp());
		observableList = FXCollections.observableArrayList(songArrayList);
		listView.setItems(observableList);
	}
}
