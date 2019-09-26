package App;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class Alert {


    public static void Display(String title, String message){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setTitle(title);
        stage.setWidth(300);
        stage.setHeight(200);

        Label label = new Label();
        label.setAlignment(Pos.CENTER);
        label.setText(message);
        label.getStyleClass().add("error");

        Button button = new Button("Accept");
        button.setAlignment(Pos.CENTER);
        button.setOnAction(e-> stage.close());
        button.getStyleClass().add("error_button");


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,button);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("home_layout");
        button.setLayoutY(400);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(Alert.class.getResource("/Main/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();

        stage.setOnCloseRequest(e-> stage.close());
    }


}
