/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import obsplugin.scoreboard.App;

/**
 *
 * @author Rémi
 */
public class ScoreComponent extends VBox {
    private Team teamPane;
    public static final int WIDTH = 130;
    private final String team;
    private final Label scoreLabel;
    
    public ScoreComponent(Team teamPane, String color, String team) {
        this.teamPane = teamPane;
        this.team = team;
        //this.setStyle("-fx-background-color: "+ color +";");
        this.setPrefWidth(WIDTH);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.TOP_CENTER);
        
        
        scoreLabel = new Label("0");
        scoreLabel.setFont(App.TEXT_FONT);
        scoreLabel.setWrapText(true);
        scoreLabel.setCenterShape(true);
        VBox score = new VBox(scoreLabel);
        score.setAlignment(Pos.CENTER);
        score.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(7.), Insets.EMPTY)));
        score.setPrefHeight(WIDTH/2);
        
                
        TilePane t = new TilePane();
        t.setPrefColumns(2);
        t.setPrefRows(2);
        t.getChildren().addAll(new PlusButton(1,scoreLabel,this),new PlusButton(2,scoreLabel,this), new PlusButton(3,scoreLabel,this), new PlusButton(5,scoreLabel,this));
        t.setAlignment(Pos.CENTER);
        
        Button resetButton = new Button("RESET");
        resetButton.setStyle("-fx-background-color: #Ffe24f");
        resetButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                resetButton.setStyle("-fx-background-color: #Ffd37e");
            }
        });
        resetButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                resetButton.setStyle("-fx-background-color: #Ffe24f");
            }
        });
        
        
        resetButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                scoreLabel.setText("0");
                //writeScore();
            }
        });
        
        this.setSpacing(10);
        
        this.getChildren().addAll(score,t,resetButton);
    }
    
    
    public void writeScore() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(team+".txt"))) {
            bw.write(scoreLabel.getText());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void animate() {
        KeyValue initKeyValue = new KeyValue(scoreLabel.scaleXProperty(), 1.3);
        KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);

        KeyValue endKeyValue = new KeyValue(scoreLabel.scaleXProperty(), 1);
        KeyFrame endFrame = new KeyFrame(Duration.seconds(0.3), endKeyValue);
        
        Timeline animation1 = new Timeline(endFrame,initFrame);
        Timeline animation2 = new Timeline(initFrame,endFrame);
        animation1.setCycleCount(1);
        animation2.setCycleCount(1);
        animation1.play();
        animation2.play();
    }
    
}
