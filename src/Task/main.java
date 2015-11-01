package Task;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class main extends Application {
    private TextArea txtAreaInfo;
    private Button btnOpen, btnProcess;
    private Label lblStatus;
    private Scene scene;

    private final FileChooser fc  = new FileChooser();
    private File inputFile, outputFile;

    private ArrayList<InstanceType> fleet;

    private final int slotLength = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Layout/layout.fxml"));
        scene = new Scene(root);
        connectToUI(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void connectToUI(Scene scene){
        btnOpen = (Button)scene.lookup("#btnOpen");
        lblStatus = (Label)scene.lookup("#lblStatus");
        btnProcess = (Button)scene.lookup("#btnProcess");
        txtAreaInfo = (TextArea)scene.lookup("#txtAreaInfo");

        lblStatus.setText("Choose a file to be processed.");

        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        btnOpen.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fc.setTitle("Select File");
                inputFile = fc.showOpenDialog(scene.getWindow());
                if (inputFile != null)
                    lblStatus.setText("Process file.");
                btnProcess.setText("Process File");
                btnProcess.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        processFile();
                    }
                });
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
        fleet = new ArrayList<>();
        Scanner sc;
        try {
            if (inputFile == null) {
                txtAreaInfo.appendText("No File was selected\n");
            } else {
                sc = new Scanner(inputFile);
                txtAreaInfo.appendText("Processing: " + inputFile.getName() + "\n");

                while (sc.hasNext()) {
                    String line = sc.nextLine();
                    line.trim();
                    boolean added = false;
                    int index = -1;

                    if (line != "") {
                        String[] hostParts = line.split(",");
                        Host host = createHost(hostParts);

                        if (host != null) {
                            String hostInstanceType = host.getInstanceType();
                            txtAreaInfo.appendText("Processed a new host: " + host.getId() + ".\n");

                            //check if InstanceType already exists, if so add host to existing object else create a new one and add the host to the new object
                            if (fleet.size() == 0)
                                index = 0;
                            else
                                for (int i = 0; i < fleet.size(); i++) {
                                    if (hostInstanceType.equals(fleet.get(i).getId())) {
                                        added = true;
                                        fleet.get(i).addHost(host);
                                        break;
                                    } else if (hostInstanceType.compareTo(fleet.get(i).getId()) < 0) {
                                        index = i;
                                        break;
                                    }
                                }
                            //If the host was not added, create a new InstanceType object and add the host to the new object
                            if (!added) {
                                InstanceType instanceType = new InstanceType(hostInstanceType);
                                instanceType.addHost(host);
                                if (index != -1)
                                    fleet.add(index, instanceType);
                                else
                                    fleet.add(fleet.size(), instanceType);
                                txtAreaInfo.appendText("Created new instance type: " + hostInstanceType + ", added " + hostInstanceType + " to the instance type collection.\n");
                            }
                            txtAreaInfo.appendText("Added host " + host.getId() + " to instance type " + hostInstanceType + " data structure.\n");
                        } else
                            txtAreaInfo.appendText("Host " + host.getId() + " data corrupt, host has been rejected.\n");
                    }
                }
                lblStatus.setText("File can now saved.");

                btnProcess.setText("Save File");
                btnProcess.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        saveFile();
                    }
                });

                sc.close();
            }
            txtAreaInfo.appendText("-----------------------------------------------------------------------------------------------------------------------\n");

        } catch (IOException e) {
            txtAreaInfo.appendText("IO Exception, file was not found!\n");
            e.printStackTrace();
        }
    }

    private Host createHost(String[] hostParts) {
        Host host = new Host();

        try{
            host.setId(Integer.valueOf(hostParts[0]));
            host.setInstanceType(hostParts[1]);
            host.setSlotCount(Integer.valueOf(hostParts[2]));

            if (hostParts.length > host.getSlotCount()+3) {
                txtAreaInfo.appendText("Host could not be created, malformed input, more instance slots than specified!\nNext Host will now be processed.\n");
                host = null;
            }
            else
                for (int i = 3; i < hostParts.length; i++) {
                    if(hostParts[i].length() == slotLength && !host.addSlot(Integer.valueOf(hostParts[i]))){
                        txtAreaInfo.appendText("Host could not be created, malformed input, slot value not valid, must be of value 0 or 1!\nNext Host will now be processed.\n");
                        return null;
                    }
                }
        }catch (Exception e) {
            txtAreaInfo.appendText("Host could not be created, malformed input!\nNext Host will now be processed.\n");
            e.printStackTrace();
            host = null;
        }finally {
            return host;
        }
    }

    private void saveFile() {
        fc.setTitle("Save File");
        fc.setInitialFileName("Statistics");

        outputFile = fc.showSaveDialog(scene.getWindow());

        if(outputFile != null)
            if (fleet.size() >= 1 ) {
                processStats();
            }
            else
                txtAreaInfo.appendText("No data to save, the file was empty or corrupt!\n");
        else
            lblStatus.setText("Choose a valid destination to save file!");
    }

    private void processStats(){
        try {
            String empty = "Empty: ", full = "FULL: ", mstFilled = "MOST FILLED: ";
            PrintWriter pw = new PrintWriter(new FileWriter(outputFile));

            for (InstanceType instanceType : fleet) {
                empty += String.format("%s=%s; ", instanceType.getId(), instanceType.getEmpHostCount());
                full += String.format("%s=%s; ", instanceType.getId(), instanceType.getFullHostCount());
                mstFilled += String.format("%s=%s,%s; ", instanceType.getId(), instanceType.getMstFilledHosts(), instanceType.getFilledHostEmpCount());
            }

            pw.println(empty);
            pw.println(full);
            pw.println(mstFilled);
            pw.close();

            lblStatus.setText("Your file has been successfully saved to the desired location.");
        }catch (IOException e) {
            txtAreaInfo.appendText("IO Exception with the output file.");
            e.printStackTrace();
        }
    }
}
