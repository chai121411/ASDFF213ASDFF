package sl.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import sl.model.Song;

import java.util.ArrayList;

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


	@FXML ListView<Song> listView = new ListView<Song>();
	ArrayList<Song> songArrayList = new ArrayList<>();
	private int index = 0;
	private int in = 0;
	public ObservableList <Song> observableList;

	public void put_list_to_view(){

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

		listView.setCellFactory(param -> new ListCell<Song>(){
			protected void updateItem(Song newSong , boolean flag){
				super.updateItem(newSong, flag);

				if(flag || newSong == null || newSong.getArtist() == null){
					setText(null);
				}else {
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






		} else if (b == deleteButton) {
			str = "delete, hi josh";
		} else if (b == editButton) {
			str = "edit, hi josh";
		} else if (b == cancelButton) {
			str = "cancel, hi josh";
		} else if (b == saveButton) {
			if(!checkEmptyLable()){
				return;
			}else{
				addSong2ListView();
			}

		}



	}

	public void addSong2ListView(){
		String songname = songName.getText();
		String artistName = artist.getText();
		String year_str = year.getText();
		String album_str = album.getText();

		Song newSong = new Song(songname , artistName);
		songArrayList.add(newSong);

		observableList = FXCollections.observableArrayList(songArrayList);
		listView.setItems(observableList);


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
			return false;
		}else{
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
			if (k != 0) {
				year.setText(Integer.toString(k));
			} else {
				year.clear();
			}

		} catch (NullPointerException s) {
		}
	}
	
}
