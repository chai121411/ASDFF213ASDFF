package sl.app;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import sl.view.SongController;

public class SongLib extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/sl/view/Song.fxml"));
		
		SplitPane root = (SplitPane)loader.load();

		SongController songController = loader.getController();
		songController.put_list_to_view();
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);  
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
