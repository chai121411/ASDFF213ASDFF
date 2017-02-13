package sl.app;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import sl.model.Song;
import sl.view.SongController;

/*
 * Min Chai
 * Jiaxu Su
 */
public class SongLib extends Application {
	
	SongController songController = null;
	private final String DELIMITER = "###@@@";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/sl/view/Song.fxml"));
		
		SplitPane root = (SplitPane)loader.load();

		songController = loader.getController();
		initializeLibrary(); //Check for file, and add songs into arraylist
		songController.put_list_to_view();
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);  
		primaryStage.show();
	}
	
	@Override
	public void stop() throws IOException {
		ArrayList<Song> songArrayList = songController.getSongArrayList();		
		File file = new File("songLibrary.txt");
		FileWriter out = new FileWriter(file);
		
		for (Song s : songArrayList) {
			if (s.getSongName() != null)
				out.write(s.getSongName());
			out.write(DELIMITER);
			if (s.getArtist() != null)
				out.write(s.getArtist());
			out.write(DELIMITER);
			if (s.getAlbum() != null)
				out.write(s.getAlbum());
			out.write(DELIMITER);
				out.write(String.valueOf(s.getYear()));
			out.write(String.format("%n"));
		}
		
		out.flush();
		out.close();
	}
	
	private void initializeLibrary() throws FileNotFoundException, IOException {
		if (new File("songLibrary.txt").exists()) {
			ArrayList<Song> songArrayList = songController.getSongArrayList();
			
			try (BufferedReader br = new BufferedReader(new FileReader("songLibrary.txt"))) {
			    String line = null;
			    while ((line = br.readLine()) != null) {
			    	String optionalAlbum = null;
			        String[] splits = line.split(DELIMITER);
			        if (!splits[2].equals("")) {
			        	optionalAlbum = splits[2];
			        }
			        songArrayList.add(new Song(splits[0], splits[1], optionalAlbum, Integer.parseInt(splits[3])));
			    }
			}
		}	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
