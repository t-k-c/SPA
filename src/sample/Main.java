package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 * Created by codename-tkc on 09/12/2017.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Activity Monitor (Twitter-Prototype) - SUP'PTIC B.A");
        primaryStage.getIcons().add(new Image(getClass().getResource("icons/icons8_Search_50px.png").toString()));
        primaryStage.setScene(new Scene(root, 500, 475));
        primaryStage.show();



    }

    @Override
    public void init() throws Exception {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
