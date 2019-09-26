package Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable, EventHandler<ActionEvent> {
    @FXML private Button compress_button;
    @FXML private Button decompress_button;
    @FXML private AnchorPane home_layout;
    @FXML private Label home_title;
    @FXML private GridPane home_grid_panel;



    @FXML
    public void initialize(URL location, ResourceBundle resources) {
       initializeStyles();
       compress_button.setOnAction(this);
       decompress_button.setOnAction(this);

    }

    public void initializeStyles(){
        compress_button.getStyleClass().add("menu_button");
        decompress_button.getStyleClass().add("menu_button");
        home_layout.getStyleClass().add("home_layout");
        home_title.getStyleClass().add("title");
    }

    @Override
    public void handle(ActionEvent event){
        GridPane pane;

        if(event.getSource()==compress_button) {
            try {
                pane = FXMLLoader.load(getClass().getResource("/FXML/CompressFXML.fxml"));
                home_grid_panel.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(event.getSource()==decompress_button){
            try {
                pane = FXMLLoader.load(getClass().getResource("/FXML/DecompressFXML.fxml"));
                home_grid_panel.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
