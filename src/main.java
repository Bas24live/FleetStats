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

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class main extends Application {
    TextField txtFileURL;
    TextArea txtAreaInfo;
    Button btnOpen;
    final JFileChooser fc  = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("txt");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));
        Scene scene = new Scene(root);
        fc.setFileFilter(filter);
        connectToUI(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void connectToUI(Scene scene){
        btnOpen = (Button)scene.lookup("#btnOpen");
        txtFileURL = (TextField)scene.lookup("#txtFileURL");
        txtAreaInfo = (TextArea)scene.lookup("#txtAreaInfo");

        btnOpen.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fc.showOpenDialog(scene);
            }
        });
    }
}
