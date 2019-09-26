package Controllers;

import App.Alert;
import App.fileTreeItem;
import App.TableElement;
import huffman_algorithm.huffman;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;



public class CompressController implements Initializable, EventHandler<ActionEvent> {
    @FXML private TreeView<File> fileView;
    @FXML private AnchorPane compress_layout;
    @FXML private Button compress_button;
    @FXML private Button write_new_file_button;
    @FXML private Button home_button;
    @FXML private Label compress_title;
    @FXML private GridPane home_grid_pane;
    @FXML private TableView<TableElement> code_table;
    @FXML private TableColumn<TableElement, Character> data_column;
    @FXML private TableColumn<TableElement,String> code_column;

    private File file;
    private huffman huffman;
    private HashMap<Character,String> tableData;


    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        file=null;
        fileView = new TreeView<File>(new fileTreeItem(new File("C:\\")));
        fileView.setPrefHeight(300);
        fileView.setPrefWidth(300);
        fileView.setLayoutX(50);
        fileView.setLayoutY(130);
        fileView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue)->{
            if(newValue !=null){
                file= fileView.getSelectionModel().selectedItemProperty().get().getValue();
            }
        } );
        compress_layout.getChildren().add(fileView);


        compress_button.setOnAction(this);
        home_button.setOnAction(this);
        write_new_file_button.setOnAction(this);




        initializeStyles();

    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    @Override
    public void handle(ActionEvent event){

        if(event.getSource()==compress_button) {
            if(file!=null && file.isFile()) {
                try {
                    validateFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if(event.getSource()==home_button) {
            GridPane pane;
            try {
                pane = FXMLLoader.load(getClass().getResource("/FXML/HomeFXML.fxml"));
                home_grid_pane.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(event.getSource()==write_new_file_button) {
            Runtime rs = Runtime.getRuntime();
            try {
                rs.exec("notepad");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void validateFile(File _file) throws IOException {

        String aux = getExtensionByStringHandling(_file.getName()).get();

        if(aux.equals("txt")){

            tableData = new HashMap<>();
            huffman= new huffman();

            String path = _file.getCanonicalPath().replace(_file.getName(),"");
            aux=_file.getName().replace(".txt","");
            huffman.setPaths(_file.getPath(),path,aux);
            huffman.compress();
            tableData=huffman.getHashMap();


            data_column.setCellValueFactory(new PropertyValueFactory<>("data"));
            code_column.setCellValueFactory(new PropertyValueFactory<>("code"));
            code_table.setItems(getData());
         // code_table.getColumns().addAll(data_column,code_column);
            Alert.Display("Compressed","The file was compressed");

        } else{
            Alert.Display("Error","Please select a text file");
        }
    }

    public void initializeStyles(){
        compress_layout.getStyleClass().add("home_layout");
        compress_button.getStyleClass().add("menu_button");
        write_new_file_button.getStyleClass().add("menu_button");
        compress_title.getStyleClass().add("title");
        home_button.getStyleClass().add("menu_button");
    }


    public ObservableList<TableElement> getData(){
        ObservableList<TableElement> elements = FXCollections.observableArrayList();

       for(Map.Entry<Character, String> entry : tableData.entrySet()) {
            char key = entry.getKey();
            String value = entry.getValue();
            elements.add(new TableElement(key,value));
        }

        return elements;

    }



}
