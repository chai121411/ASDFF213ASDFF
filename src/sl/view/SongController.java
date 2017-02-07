package sl.view;

import java.util.ArrayList;
import java.util.Collections;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import sl.model.Song;

/*
 * Min Chai
 * Josh Su
 */

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
	@FXML Label labelSongName;//if user does not put first two column , then it will show up
	@FXML Label labelArtist;
	@FXML Label labelAlbum;
	@FXML Label labelYear;
	
	@FXML ListView<Song> listView = new ListView<Song>();
	private ArrayList<Song> songArrayList = new ArrayList<>();

	private int index = 0;
	private int in = 0;
	public ObservableList <Song> observableList;
	boolean editButtonClickedBeforeSave = false;
	private int oldSongIndex = -1; //Used in detecting when "Add" is clicked before "Cancel"

	public ArrayList<Song> getSongArrayList() {
		return songArrayList;
	}
	
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
		Button b = (Button)e.getSource(); //each button has a unique fxml_id
		
		if (b == addButton) {
			songName.requestFocus();
			oldSongIndex = listView.getSelectionModel().getSelectedIndex();
			listView.getSelectionModel().clearSelection();//no selection for any item
			clearTextFields();//clear the 4 fields on the right
			setTextFieldsEditable(true);//set 4 fields editable
			showCancelSave();
			editButton.setDisable(true);
		} else if (b == deleteButton) {
			if( (!songArrayList.isEmpty()) && listView.getSelectionModel().getSelectedIndex() >= 0) {
				if (AlertUtil.confirmDelete()) {
					boolean flagendlist = false;
					int n = getSelectedIndex();
					
					if(n == songArrayList.size() - 1) flagendlist = true;
					songArrayList.remove(n);
					clearTextFields();
					up();
	
					listView.getSelectionModel().select(n);
					if(flagendlist) listView.getSelectionModel().select(n - 1);
					disableRightPane();
				}
			} else {
				AlertUtil.noSongSelected();
			}
		} else if (b == editButton) {
			setTextFieldsEditable(true);
			showCancelSave();
			editButtonClickedBeforeSave = true; 
		} else if (b == cancelButton) {
			if ( (!songArrayList.isEmpty()) && oldSongIndex >= 0) {
				listView.getSelectionModel().select(oldSongIndex);
				oldSongIndex = -1;
			}
			show();
		} else if (b == saveButton) {
			if (!checkEmptyLabel()) {
				return;
			} else if (editButtonClickedBeforeSave) {//The edit button was clicked exactly before save was clicked.
				if (AlertUtil.confirmEdit()) { //Only ask to confirm edit after we know the edit btn was clicked
					modifySongInList();
				}
			} else if (AlertUtil.confirmSave()) {
				addSong2ListView();
			}
		}
	}

	private void addSong2ListView() {
		String songname = songName.getText();
		String artistName = artist.getText();
		String year_str = year.getText();
		String album_str = album.getText();
		int k = 0;
		Song newSong = null;
		
		try {
			if (!year_str.equals("")) {
				k = Integer.parseInt(year_str);
				if (k <= 0) {
				    AlertUtil.invalidYear("You entered an invalid year.");
				    return;
				}
			}
		} catch (NumberFormatException e) {
			AlertUtil.invalidYear("You did not enter a year in numbers.");
			return;
		}
		
		if ( ((year_str == null || year_str.equals("")) && (album_str == null ||album_str.equals(""))) ) {
			newSong = new Song(songname, artistName);
		} else if (year_str == null || year_str.equals("")) {
			newSong = new Song(songname, artistName, album_str);
		} else if (album_str == null || album_str.equals("")) {
			newSong = new Song(songname, artistName, k);
		} else {
			newSong = new Song(songname, artistName, album_str, k);
		}
		
		for (Song s: songArrayList) {
			if (s.equals(newSong)) {
				AlertUtil.invalidSong();
				return;
			}
		}
		
		songArrayList.add(newSong);
		observableList = FXCollections.observableArrayList(songArrayList);
		listView.setItems(observableList);
		up();
		highlightAndShow(newSong);
	}
	
	private void modifySongInList() {
		int element = getSelectedIndex();
		songArrayList.remove(element);
		addSong2ListView();
	}

	private void clearTextFields() {
		songName.clear();
		artist.clear();
		album.clear();
		year.clear();
	}

	private void setTextFieldsEditable(boolean flag) {
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
		editButtonClickedBeforeSave = false;
	}

	private boolean checkEmptyLabel() {
		String songname = songName.getText();
		String artistName = artist.getText();

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

	private void show() {
		try {
			songName.setText(listView.getSelectionModel().getSelectedItem().getSongName());
			artist.setText(listView.getSelectionModel().getSelectedItem().getArtist());
			album.setText(listView.getSelectionModel().getSelectedItem().getAlbum());
			int k = listView.getSelectionModel().getSelectedItem().getYear();
			if (k > 0) {
				year.setText(Integer.toString(k));
			} else {
				year.clear();
			}
			disableRightPane();

		} catch (NullPointerException s) {
		}
	}
	
	private void hideCancelSave() {
		cancelButton.setVisible(false);
		saveButton.setVisible(false);
	}
	
	private void showCancelSave() {
		cancelButton.setVisible(true);
		saveButton.setVisible(true);
	}
	
	private void disableRightPane() {
		setTextFieldsEditable(false);//set 4 fields uneditable
		hideCancelSave();
		req1.setVisible(false);
		req2.setVisible(false);
		if (songArrayList.isEmpty()) {
			editButton.setDisable(true);
		} else {
			editButton.setDisable(false);
		}
	}

	private int getSelectedIndex() {
		return listView.getSelectionModel().getSelectedIndex();
	}

	private void up() {
		Collections.sort(songArrayList, new Song.Comp());
		observableList = FXCollections.observableArrayList(songArrayList);
		listView.setItems(observableList);
	}
	
	private void highlightAndShow(Song s) {
		listView.getSelectionModel().select(s);
		show();
	}
}
