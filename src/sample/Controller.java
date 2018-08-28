package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.*;
import twittermanager.TwitterManager;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private  ScrollPane scroller;
    @FXML
    private   AnchorPane scrollerElt,tweetInfo;
    @FXML
    private ProgressIndicator loader;
    public static AnchorPane scrollViewAnchor,tweetInfoRepresentative;
    public static ProgressIndicator loaderRepresentative;
    @FXML private  ImageView searchButton;
    @FXML private TextField searchField;
    @FXML private Label log;
    public static Label logRepresentative;
    @FXML private ImageView stopButton;
    public static ImageView stopButtonRepresentative;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollViewAnchor  = scrollerElt ;
        loaderRepresentative=loader;
        logRepresentative = log;
        tweetInfoRepresentative=tweetInfo;
        stopButtonRepresentative= stopButton;
        stopButton.setVisible(false);
        loader.setVisible(false);
        searchButton.setCursor(Cursor.HAND);
        stopButton.setCursor(Cursor.HAND);
        searchButton.setOnMouseClicked(event -> {
            tweetInfoRepresentative.getChildren().removeAll(tweetInfoRepresentative.getChildren());
            TwitterManager.searchIsOn=true;
           String text =  searchField.getText();
            log.setText("User initiating search with query : " +text);
            TwitterManager.start(text);

        }); stopButton.setOnMouseClicked(event -> {
            log.setText("Search aborted");
            TwitterManager.searchIsOn=false;
            loader.setVisible(false);
            stopButton.setVisible(false);

        });
        try {

            Node node = FXMLLoader.load(getClass().getResource("nothing.fxml"));
//            AnchorPane.setTopAnchor(node,i*78.0);
            AnchorPane.setRightAnchor(node,0.0);
            AnchorPane.setLeftAnchor(node,0.0);
            scrollerElt.getChildren().add(node);
            log.setText("All components are loaded :)");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void showTweetInfo(ArrayList<String> stringArrayList,int index,String url){

            tweetInfoRepresentative.getChildren().removeAll(tweetInfoRepresentative.getChildren());

        double anchorVal = 0.0 ;
        for(String concern : stringArrayList){
            Label label = new Label(concern);
            if(stringArrayList.indexOf(concern) == index && url!=""){
                label.setTextFill(javafx.scene.paint.Paint.valueOf("#0078D7"));
                label.setCursor(Cursor.HAND);
                label.setOnMouseClicked(event -> {
                    try {
                        Desktop.getDesktop().browse(new URI(url));
                    }catch (IOException | URISyntaxException e){
                        e.printStackTrace();
                    }
                });
            }
            AnchorPane.setTopAnchor(label,anchorVal);
            AnchorPane.setLeftAnchor(label,22.0);
            AnchorPane.setRightAnchor(label,14.0);
            tweetInfoRepresentative.getChildren().add(label);
            anchorVal += 36.0;
        }
    }


}
