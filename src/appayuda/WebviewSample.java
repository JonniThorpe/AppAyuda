/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appayuda;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebHistory.Entry;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebviewSample extends Application {
    private Scene scene;
    @Override 
    public void start(Stage stage) {
        // create scene 
        stage.setTitle("Web View");
        scene = new Scene(new Browser(),750,500, Color.web("#666970"));
        stage.setScene(scene); 
        stage.show(); 
    } 
    public static void main(String[] args){
        launch(args); 
    } 
}
class Browser extends Region {
    private HBox toolBar;
    private static String[] imageFiles = new String[]{ 
        "facebook.png", "moodle.png",
        "chrome.png", "instagram.png " 
    };
    private static String[] captions = new String[]{
        "Facebook", "Moodle",
        "Google", "Instagram " 
    }; 
    private static String[] urls = new String[]{ " "
            + "https://es-es.facebook.com/", 
            "http://www.ieslosmontecillos.es/moodle/",
            "https://www.google.es/", 
            "https://www.instagram.com/?hl=es" 
    }; 
    final ImageView selectedImage = new ImageView();
    final Hyperlink[] hpls = new Hyperlink[captions.length];
    final Image[] images = new Image[imageFiles.length];
    private boolean needDocumentationButton = false;
    
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
    
    public Browser() { 
    //apply the styles 
    getStyleClass().add("browser");
    //Para tratar lo tres enlaces 
    for (int i = 0; i < captions.length; i++) {
        Hyperlink hpl = hpls[i] = new Hyperlink(captions[i]); 
        Image image = images[i] = 
                new Image(getClass().getResourceAsStream(imageFiles[i]));
        hpl.setGraphic(new ImageView (image));
        final String url = urls[i];
        //proccess event 
        hpl.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                webEngine.load(url); 
            } 
        }); 
    } 
    // load the web page 
    webEngine.load("http://www.ieslosmontecillos.es/moodle/");
    // create the toolbar 
    toolBar = new HBox();
    toolBar.setAlignment(Pos.CENTER);
    toolBar.getStyleClass().add("browser-toolbar");
    toolBar.getChildren().addAll(hpls);
    toolBar.getChildren().add(createSpacer());
    //add components 
    getChildren().add(toolBar);
    getChildren().add(browser);
    
    
final ComboBox comboBox = new ComboBox(); 
//En el constructor de la clase Browser damos formato al combobox y lo 
//incluimos en la toolbar 
comboBox.setPrefWidth(90); 
toolBar.getChildren().add(comboBox);
//también el constructor de la clase Browser declaramos el manejador 
//del histórico 
final WebHistory history = webEngine.getHistory();
history.getEntries().addListener(new ListChangeListener<WebHistory.Entry>(){
    @Override 
    public void onChanged(Change<? extends Entry> c) {
        c.next();
        for (Entry e : c.getRemoved()) {
            comboBox.getItems().remove(e.getUrl()); 
        } for (Entry e : c.getAddedSubList()) {
            comboBox.getItems().add(e.getUrl()); 
        } 
    } 
}); 
//Se define el comportamiento del combobox comboBox.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent ev) { int offset = comboBox.getSelectionModel().getSelectedIndex() - history.getCurrentIndex(); history.go(offset); } });
    
   } 
    private Node createSpacer() {
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    return spacer; 
    } 
    @Override 
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        double tbHeight = toolBar.prefHeight(w);
        layoutInArea(browser,0,0,w,h-tbHeight,0, HPos.CENTER, VPos.CENTER);
        layoutInArea(toolBar,0,h- tbHeight,w,tbHeight,0,HPos.CENTER,VPos.CENTER);
    } 
    @Override 
    protected double computePrefWidth(double height) {
        return 300;

    } 
    @Override 
    protected double computePrefHeight(double width) {
        return 150; 
    } 
}