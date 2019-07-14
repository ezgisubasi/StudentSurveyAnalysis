import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        root.getStylesheets().add(getClass().getResource("FXMLDocument.fxml").toExternalForm());
        primaryStage.setTitle("Student Survey Analysis");
        primaryStage.setScene(new Scene(root, 1067, 588));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

