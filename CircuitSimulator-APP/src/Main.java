
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();

		Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
			Alert alert = new Alert(Alert.AlertType.ERROR,"Unfortunately error occurred during requested operation.");
			alert.show();
		});

		Scene scene = new Scene(root,850,600);
		scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		
		root.setCenter(new RootLayout());
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
