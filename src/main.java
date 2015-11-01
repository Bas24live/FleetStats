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
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;


public class main extends Application {
    private TextField txtFileURL;
    private TextArea txtAreaInfo;
    private Button btnOpen, btnProcess;

    private final FileChooser fc  = new FileChooser();
    private File file;

    private Hashtable<String, InstanceType> fleet;

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
        fleet = new Hashtable<>();
        Scanner sc;
        try {
            sc = new Scanner(file);
            if (file == null) {
                txtAreaInfo.appendText("No File was selected\n");
            } else {
                txtAreaInfo.appendText("Processing: " + file.getName() + "\n");
                while (sc.hasNext()) {
                    String line = sc.nextLine();
                    line.trim();
                    String component = "";

                    if (line != "") {
                        Host host = new Host();
                        int posCounter = 0;

                        for (char letter : line.toCharArray()) {
                            if (letter == ',') {
                                ++posCounter;
                                if (posCounter == 1)
                                    host.setId(Integer.valueOf(component));
                                else if(posCounter == 2)
                                    host.setInstanceType(component);
                                else
                                    host.addSlot(Integer.valueOf(component));

                                component = "";

                            } else{
                                component += letter;
                            }
                        }

                        String hostInstanceType = host.getInstanceType();
                        txtAreaInfo.appendText("Processed new host: " + host.getId() + ".\n");

                        if(fleet.containsKey(hostInstanceType))
                            fleet.get(hostInstanceType).addHost(host);
                        else {
                            InstanceType instanceType = new InstanceType(hostInstanceType);
                            instanceType.addHost(host);
                            fleet.put(hostInstanceType, instanceType);
                            txtAreaInfo.appendText("Created new instance type: " + hostInstanceType + ", added " + hostInstanceType + " to the instance type data structure.\n");
                        }

                        txtAreaInfo.appendText("Added " + host.getId() + " to " + hostInstanceType + " instance type data structure.\n");
                    }
                }
            }
        }catch (IOException e) {
            txtAreaInfo.appendText("IO Exception, file was not found!\n");
            e.printStackTrace();
        }
    }
}
