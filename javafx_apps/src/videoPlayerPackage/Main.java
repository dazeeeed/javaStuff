package videoPlayerPackage;
	
import java.io.File;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	private static final String MEDIA_URL =
    		"http://techslides.com/demos/sample-videos/small.mp4";

    MediaPlayer mediaPlayer;
    MediaControl mediaControl;
    Group root;
    FileChooser fileChooser;
    Media media;
	File file;
	Scene scene;
	VBox vb;
	
	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Embedded Media Player");
        
        
        //---Menu---
        MenuBar menuBar = new MenuBar();
        Menu mediaMenu = new Menu("Media");
        MenuItem menuItem = new MenuItem("Choose file");
        mediaMenu.getItems().add(menuItem);
        menuBar.getMenus().add(mediaMenu);
        
        vb = new VBox(menuBar);
        root = new Group();
        scene = new Scene(vb, 600, 400);
        
        //---FileChooser---
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
        fileChooser.setInitialFileName("http://techslides.com/demos/sample-videos/small.mp4");
        //---Button---
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		((VBox) scene.getRoot()).getChildren().remove(mediaControl);
        		file = fileChooser.showOpenDialog(primaryStage);
        		media = new Media(file.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
               	mediaControl = new MediaControl(mediaPlayer);
               	
               	((VBox) scene.getRoot()).getChildren().add(mediaControl);
               
               	
        	}
        });
        
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
