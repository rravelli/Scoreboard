/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package component;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.HBox;
import obsplugin.scoreboard.ConfigManager;

/**
 *
 * @author Rémi
 */
public class Team extends HBox {
    private ScoreComponent sc;
    private TeamName tn;
    private String bgColor;
    public String teamType;
    
    public Team(String color, String team, String teamName) {
        this.setStyle("-fx-background-color: "+color+";");
        bgColor = color;
        teamType = team;
        sc = new ScoreComponent(this,color, team);
        tn = new TeamName(this,color, teamName);
        if (team.equals("A")) {
            this.getChildren().addAll(tn,sc); 
        } else {
            this.getChildren().addAll(sc,tn);
        }
        
    }
    
    public void setBgColor(String color) {
       this.setStyle("-fx-background-color: "+color+";"); 
       bgColor = color;
    }
    
    public String getBgColor() {
        return bgColor;
    }
    
    public void saveConfig() {
        try {
            ConfigManager.saveConfig("team"+teamType+"Color", bgColor);
        } catch (IOException ex) {}
        tn.saveConfig();
    }
    
}
