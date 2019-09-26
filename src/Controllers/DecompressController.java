package Controllers;

import App.Alert;
import App.fileTreeItem;
import huffman_algorithm.huffman;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.Optional;
import java.util.ResourceBundle;


public class DecompressController implements Initializable, EventHandler<ActionEvent>  {

    @FXML private TreeView<File> fileView;
    @FXML private Button home_button;
    @FXML private Button decompress_button;
    @FXML private GridPane home_grid_pane;
    @FXML private AnchorPane decompress_layout;
    @FXML private Label decompress_title;
    @FXML private Text result;
    private File file;
    private huffman huffman;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        file=null;
        fileView = new TreeView<File>(new fileTreeItem(new File("C:\\")));
        fileView.setPrefHeight(300);
        fileView.setPrefWidth(300);
        fileView.setLayoutX(40);
        fileView.setLayoutY(130);
        fileView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue)->{
            if(newValue !=null){
                file= fileView.getSelectionModel().selectedItemProperty().get().getValue();
            }
        } );
        decompress_layout.getChildren().add(fileView);

        home_button.setOnAction(this);
        decompress_button.setOnAction(this);
        initializeStyles();

    }

    @Override
    public void handle(ActionEvent event){

        if(event.getSource()==home_button) {
            GridPane pane;
            try {
                pane = FXMLLoader.load(getClass().getResource("/FXML/HomeFXML.fxml"));
                home_grid_pane.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(event.getSource()==decompress_button) {
            if(file!=null && file.isFile()) {
                try {
                    validateFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }



    public void validateFile(File _file) throws IOException {

        String aux = getExtensionByStringHandling(_file.getName()).get();
        String textDecoded;

        if(aux.equals("txt")){
            huffman= new huffman();

            String path = _file.getCanonicalPath().replace(_file.getName(),"");
            aux=_file.getName().replace("-compressed.txt","");
            huffman.setPaths(_file.getPath(),path,aux);
            huffman.uncompress() ;
            result.setText(huffman._readFile());

            Alert.Display("Decompressed","The file was decompressed");


        } else{
            Alert.Display("Error","Please select a text file");
        }
    }

    public void initializeStyles(){
        decompress_layout.getStyleClass().add("home_layout");
        home_button.getStyleClass().add("menu_button");
        decompress_button.getStyleClass().add("menu_button");
        decompress_title.getStyleClass().add("title");

    }


}
