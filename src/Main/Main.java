package Main;

import Controllers.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static HomeController HomeController;
    private Stage windows;

    @Override
    public void start(Stage primaryStage) throws Exception{


        windows=primaryStage;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXML/HomeFXML.fxml"));
        Parent root = loader.load();
        HomeController = (HomeController)loader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setTitle("Huffman Compressor");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);



        windows.setOnCloseRequest(e->closeProgram());

        primaryStage.show();

    }

    public void closeProgram(){
        windows.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
