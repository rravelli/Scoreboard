/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package component;

import static component.ScoreComponent.WIDTH;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import obsplugin.scoreboard.App;
import obsplugin.scoreboard.ConfigManager;

/**
 *
 * @author Rémi
 */
public class TeamName extends VBox{
    
    private Label teamLabel;
    private Team teamPane;
    
    public TeamName(Team teamPane, String color,String name) {
        this.teamPane = teamPane;
        this.setPrefSize(150, USE_PREF_SIZE);
        this.setPadding(new Insets(10));
        teamLabel = new Label(name);
        teamLabel.setFont(App.TEXT_FONT);
        teamLabel.setWrapText(true);
        teamLabel.setCenterShape(true);
        teamLabel.setTextAlignment(TextAlignment.RIGHT);
        VBox score = new VBox(teamLabel);
        if (teamPane.teamType.equals("A")) {
            score.setAlignment(Pos.CENTER_RIGHT);
        } else {
            score.setAlignment(Pos.CENTER_LEFT);
        }
        //score.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(7.), Insets.EMPTY)));
        score.setPrefHeight(WIDTH/2); 
        
        TextField field = new TextField(name);
        addTextLimiter(field, 4);
        field.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                teamLabel.setText(field.getText());
            }
        });
        
        ColorPicker cp = new ColorPicker(Color.web(teamPane.getBgColor()));
        cp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                String color = "#" + cp.getValue().toString().substring(2);
                teamPane.setBgColor(color);
                try {
                    ConfigManager.saveConfig("team"+teamPane.teamType+"Color", color);
                } catch (IOException ex) {

                }
            }
        });
       
        
        this.setSpacing(20);
        this.getChildren().addAll(score,field,cp);
        
    }
    
    
    
    public void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
            try {
                ConfigManager.saveConfig("team"+teamPane.teamType+"Name", tf.getText());
            } catch (IOException ex) {
                Logger.getLogger(TeamName.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
    }

    void saveConfig() {
        try {
            ConfigManager.saveConfig("team"+teamPane.teamType+"Name", teamLabel.getText());
        } catch (IOException ex) {}
    }
    
    
}
