package twittermanager;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import sample.Controller;
import sample.MenuController;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;

/**
 * Created by codename-tkc on 09/12/2017.
 */

public class TwitterManager {
    public static boolean searchIsOn = true;
    public void startSearching(String[] queries) throws TwitterException, IOException {
        int counter=0;

        String query = "";
        for(String stuff: queries){
            if(!Objects.equals(queries[queries.length - 1], stuff)){
                query+=stuff+"%20";
            }else{
                query+=stuff;
            }
        }
        System.out.println(" =====> "+query+" is the query");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Controller.logRepresentative.setText("Please wait...");
            }
        });

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("wC81RH2KuxBYXvouoyCGQ5Ujl")
                .setOAuthConsumerSecret("csqNyyD928AjrUtamEvP1cMV6hWEOwmotWAcI1slubmimd778z")
                .setOAuthAccessToken("2821751687-KAJ0rASObboCrFkP4aFXumzimbn9LiwpaGZp2Eu")
                .setOAuthAccessTokenSecret("z9956MXkP9MyLQzhn8Hh5OHGL5PFkcNZzIESZiZuPTmt3");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        /*Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Controller.logRepresentative.setText("Creating twitter instance...");
            }
        });*/

        QueryResult qr = twitter.search(new Query(query));
        while(qr.hasNext()){
            final int dummy = counter;
            if(!searchIsOn){
                break;
            }
            List<Status> statuses  = qr.getTweets();
            for(Status status : statuses){
                if(!searchIsOn){
                    break;
                }
            boolean contains=true;
             /*   for(String keyword : queries){
                    if(status.isRetweet()){
                        if(!status.getRetweetedStatus().getText().contains(keyword))
                            contains=false;
                    }
                    else{
                        if(!status.getText().contains(keyword))
                            contains=false;
                    }
                }*/

                final int value =counter;
                Platform.runLater(() -> {
                    try {
                        FXMLLoader fxmlLoader  = new FXMLLoader(new File("src/sample/menuitem.fxml").toURI().toURL());
                        MenuController  menuController = new MenuController();
                        fxmlLoader.setController(menuController);
                        System.out.println("*\n"+value+" is the current counter*\n");
                        String content = status.isRetweet()?status.getRetweetedStatus().getText() : status.getText();
                        menuController.addToMenu(status.getUser().getName()+" , "+ status.getCreatedAt()+":"+content,fxmlLoader.load(),status.getUser().getOriginalProfileImageURL(),value,status);
                        Controller.logRepresentative.setText("Total Search Results: "+value);
                    } catch (IOException e) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Controller.logRepresentative.setText("Error occurred (code 1998): "+e.getMessage());
                            }
                        });

                        e.printStackTrace();
                    }
                });

                System.out.println(
                                status.getCreatedAt()+"==="+
                                status.getUser().getScreenName()+"/////"+
                                status.getSource()+"==="+
                                status.getText()+"//////// "+
                                status.getUser()+ "====///====" +
                                status.isPossiblySensitive() +" |||||||||||||| "+status.getUser().getOriginalProfileImageURL()

                );
                counter++;

            }  qr = twitter.search(qr.nextQuery());
        }
    }
//    public static void main(String args[]){
    public  static void start(String text){

        ObservableList<Node> nodes = Controller.scrollViewAnchor.getChildren();
        Controller.scrollViewAnchor.getChildren().removeAll(nodes);
        new Thread(() -> {
            try {
                Controller.stopButtonRepresentative.setVisible(true);
                Controller.loaderRepresentative.setVisible(true);
                new TwitterManager().startSearching(text.split(" "));
            }  catch(Exception e){
                e.printStackTrace();
            }
        }).start();

    }
}
