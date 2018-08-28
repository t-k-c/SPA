package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import twitter4j.GeoLocation;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.User;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by codename-tkc on 09/12/2017.
 */
public class MenuController implements Initializable {
    @FXML
    private Text text;
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView image;

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addToMenu(String text, Node n, String url, int counter, Status status) {
        AnchorPane.setTopAnchor(n, counter * 98.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(url+" is url");
               image.setImage(new Image(url,true));
            }
        });this.text.setText(text);
    /*    this.image.setStyle("-fx-background-image: url('" + url + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;");*/

//        this.imageView.s
        n.setOnMouseEntered(event -> n.setStyle("-fx-background-color: #e9e9e9"));
        n.setOnMouseExited(event -> n.setStyle("-fx-background-color: #fff"));
        n.setCursor(Cursor.HAND);
        n.setOnMouseClicked(event -> {
            ArrayList<String> strings = new ArrayList<>();
            String url1 ="";
            int indexLink;
            if (status.isRetweet()) {
                strings.add("Status: Retweeted by @" + status.getUser().getScreenName()+" (this user)");
                strings.add((status.getRetweetCount()-1) +  " other(s) retweeted");
                strings.add("Original source: " + status.getRetweetedStatus().getUser().getName() + " (@" + status.getRetweetedStatus().getUser().getScreenName() + ")");
                strings.add("Retweet Date: " + status.getCreatedAt());
                strings.add("Original publication date: " + status.getRetweetedStatus().getCreatedAt());
                String src = status.getRetweetedStatus().getSource().split(">")[1].split("<")[0];
                strings.add("Original tweet device: " + src);
                src = status.getSource().split(">")[1].split("<")[0];
                strings.add("Retweet device: " + src);
                GeoLocation geoLocation;
                if ((geoLocation = status.getRetweetedStatus().getGeoLocation()) != null)
                    strings.add("Original tweet location: LatLng(" + geoLocation.getLatitude() + "," + geoLocation.getLongitude() + ")");
                if (status.getGeoLocation() != null)
                    strings.add("Retweet location: LatLng(" + status.getGeoLocation().getLatitude() + "," + status.getGeoLocation().getLongitude() + ")");
                if (status.getRetweetedStatus().getPlace() != null)
                    strings.add("Original tweet place: " + status.getRetweetedStatus().getPlace().getFullName());
                if (status.getPlace() != null)
                    strings.add("Retweet place: " + status.getPlace().getFullName());
                indexLink = strings.size();
                URLEntity[] urlEntities ;
                if((urlEntities = status.getRetweetedStatus().getURLEntities()).length!=0) {
                    url1 = urlEntities[0].getExpandedURL();
                    strings.add("Source URL: " + status.getRetweetedStatus().getURLEntities()[0].getText());
                }
                else {
                    String[] split = status.getText().split(" ");
                    if (split[split.length - 1].startsWith("https://")) {
                        strings.add("Source URL: " + (url1 = split[split.length-1]));
                    }
                }

            } else {
                strings.add("Status: Author (Not retweet)");
                strings.add("Source: " + status.getUser().getName() + " (@" + status.getUser().getScreenName() + ")");
                strings.add("Publication date: " + status.getCreatedAt());
                String src = status.getSource().split(">")[1].split("<")[0];
                strings.add("Original tweet device: " + src);
                if (status.getGeoLocation() != null)
                    strings.add("location: LatLng(" + status.getGeoLocation().getLatitude() + "," + status.getGeoLocation().getLongitude() + ")");
                if (status.getPlace() != null)
                    strings.add("Place: " + status.getPlace().getFullName());
                indexLink = strings.size();
                URLEntity[] urlEntities ;
                if((urlEntities = status.getURLEntities()).length!=0){
                    url1 = urlEntities[0].getExpandedURL();
                    strings.add("Tweet URL: " + status.getURLEntities()[0].getText());
                } else {
                    String[] split = status.getText().split(" ");
                    if (split[split.length - 1].startsWith("https://")) {
                        strings.add("Source URL: " + (url1 = split[split.length-1]));
                    }
                }

            }

         Controller.showTweetInfo(strings,indexLink, url1);

        });
        Controller.scrollViewAnchor.getChildren().add(n);
    }

}
