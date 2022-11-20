package obsplugin.scoreboard;

import component.StopWatch;
import component.Team;
import java.io.File;
import java.io.FileReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static final Font TEXT_FONT = new Font("Arial Black",40);
    // Team A
    public static final String DEFAULT_TEAM_A_COLOR = "#C41E3A";
    public static final String DEFAULT_TEAM_A_NAME = "ECN";
    // Team B
    public static final String DEFAULT_TEAM_B_COLOR = "#89CFF0";
    public static final String DEFAULT_TEAM_B_NAME = "ABS";
    
    private Team teamA;
    private Team teamB;
    
    @Override
    public void start(Stage stage) throws IOException {
        
        HBox box = new HBox();
        scene = new Scene(box);
        stage.getIcons().add(new Image("asset/icon.png"));
        stage.setResizable(false);
        stage.setTitle("Scoreboard");
        stage.setScene(scene);
        stage.alwaysOnTopProperty();
        
        // Load Config
        Properties config = new Properties();
        String teamAColor = DEFAULT_TEAM_A_COLOR;
        String teamAName = "";
        
        String teamBColor = DEFAULT_TEAM_B_COLOR;
        String teamBName = "";
        
        try {
            // TeamA
            teamAColor = ConfigManager.loadConfig("teamAColor");
            teamAName = ConfigManager.loadConfig("teamAName");
            
            // TeamB
            teamBColor = ConfigManager.loadConfig("teamBColor");
            teamBName = ConfigManager.loadConfig("teamBName");
        } catch (Exception e) {
            ConfigManager.initConfig();
            System.out.println("oui");
        }
        teamA = new Team(teamAColor,"A",teamAName);
        teamB = new Team(teamBColor,"B",teamBName);
        StopWatch s = new StopWatch();
        
        box.getChildren().addAll(teamA,s,teamB);
        saveConfig();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    
    public void saveConfig() {
        teamA.saveConfig();
        teamB.saveConfig();
    }

}