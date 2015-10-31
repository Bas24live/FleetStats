import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class main extends Application {
    private TextField txtFileURL;
    private TextArea txtAreaInfo;
    private Button btnOpen, btnProcess;

    private final FileChooser fc  = new FileChooser();
    private File file;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));
        Scene scene = new Scene(root);
        connectToUI(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void connectToUI(Scene scene){
        btnOpen = (Button)scene.lookup("#btnOpen");
        btnProcess = (Button)scene.lookup("#btnProcess");
        txtFileURL = (TextField)scene.lookup("#txtFileURL");
        txtAreaInfo = (TextArea)scene.lookup("#txtAreaInfo");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        btnOpen.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                file = fc.showOpenDialog(scene.getWindow());
            }
        });

        btnProcess.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                processFile();
            }
        });
    }

    private void processFile() {
        if (file == null) {
            txtAreaInfo.appendText("No File was selected\n");
        }
        else
            txtAreaInfo.appendText("Selected: " + file.getName() + "\n");

    }
}
