/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package component;

import static component.ScoreComponent.WIDTH;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javax.sound.midi.SysexMessage;
import javax.swing.SpinnerNumberModel;
import obsplugin.scoreboard.App;
import obsplugin.scoreboard.ConfigManager;

/**
 *
 * @author Rémi
 */
public class StopWatch extends VBox {
    private int time = 0;
    private Label timeLabel;
    private final Timeline timeline;
    private boolean isRunning;
    private Button play;
    private Image pauseImage, playImage;
    Spinner<Integer> s;
    private String bgColor = "#E6DADA";
    
    public StopWatch() {
        isRunning=false;
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(new Insets(10));
        this.setStyle("-fx-background-color: "+bgColor);
        this.setPrefWidth(140);
        timeLabel = new Label("00:00");
        //writeTime();
        timeLabel.setTextAlignment(TextAlignment.JUSTIFY);
        timeLabel.setFont(App.TEXT_FONT);

        VBox v = new VBox(timeLabel);
        v.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(7.), Insets.EMPTY)));
        v.setPrefHeight(WIDTH/2);
        v.setAlignment(Pos.CENTER);
        
        HBox h = new HBox();
        h.setAlignment(Pos.CENTER);
        h.setPadding(new Insets(10));
        Button reset = new Button();
        reset.setMaxSize(20, 20);
        reset.setStyle("-fx-background-color: #FF9290");
        play = new Button();
        play.setMaxSize(20, 20);
        play.setStyle("-fx-background-color: #54FF89");
        
        pauseImage = new Image("asset/pause.png");
        playImage = new Image("asset/play.png");
        play.setGraphic(new ImageView(playImage));
        reset.setGraphic(new ImageView(new Image("asset/stop.png")));
        
        reset.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                reset.setStyle("-fx-background-color: #FF9290");
            }
        });
        
        play.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                play.setStyle("-fx-background-color: #F5E9E9");
            }
        });
        play.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                play.setStyle("-fx-background-color: #A0FFBE");
            }
        });
        reset.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                reset.setStyle("-fx-background-color: #FF9290");
            }
        });
        
        reset.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                reset.setStyle("-fx-background-color: #FFBAB9");
            }
        });
        
        
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isRunning) {
                    pause();
                } else {
                    play(); 
                }
            }
        });
                reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeLabel.setText("00:00");
                time = 0;
                pause();
                
                
                
            }
        });
        h.getChildren().addAll(play, reset);
        
        Label l = new Label("Durée d'une période (min)");
        Integer period = 45;
        try {
            period = Integer.parseInt(ConfigManager.loadConfig("periodDuration"));
        } catch (Exception ex) {
            
        }
        SpinnerValueFactory<Integer> sf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1000,period);
        s = new Spinner<>(sf);
        s.setEditable(true);
        s.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                try {
                    ConfigManager.saveConfig("periodDuration",s.getValue().toString());
                } catch (IOException ex) {
                }
            }
        });
        SpinnerValueFactory<Integer> sMinutes = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,400,0);
        SpinnerValueFactory<Integer> sSeconds = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0);
        Spinner minuteSpinner = new Spinner<Integer>(sMinutes);
        minuteSpinner.setPrefWidth(60);
        Spinner secondSpinner = new Spinner<Integer>(sSeconds);
        secondSpinner.setPrefWidth(60);
        Button setButton = new Button("SET");
        setButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                time = (Integer)minuteSpinner.getValue() * 60 + (Integer)secondSpinner.getValue();
                timeLabel.setText(getTime());
            }
        });
        setButton.setStyle("-fx-background-color: #Ffe24f");
        setButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                setButton.setStyle("-fx-background-color: #Ffd37e");
            }
        });
        setButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                setButton.setStyle("-fx-background-color: #Ffe24f");
            }
        });
        HBox jumpTo = new HBox();
        jumpTo.setPadding(new Insets(20,0,20,0));
        jumpTo.getChildren().addAll(minuteSpinner,secondSpinner,setButton);
        jumpTo.setSpacing(8);
        
        this.getChildren().addAll(v,h,jumpTo,l,s);
        
        timeline = new Timeline(
        new KeyFrame(Duration.seconds(1.0), e -> {
            time+=1;
            //writeTime();
            timeLabel.setText(getTime());
            
            if (time%(s.getValue()*60)==0) {
                pause();
            }
            
        })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        
    }
    
    private String to2digits(Integer a) {
        if (a<10) {
            return "0"+a.toString();
        } else {
            return a.toString();
        }
    }
    
    private void pause() {
        timeline.pause();
        isRunning = false;
        play.setGraphic(new ImageView(playImage));
        //writeTime();
    }
    
    private void play() {
        timeline.play();
        isRunning = true;
        play.setGraphic(new ImageView(pauseImage));
    }
    
    
    
    
    private String getTime() {
        return to2digits(time/60) + ":" + to2digits(time%60);
        
    }
    
    private void writeTime() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("time.txt"))) {
            bw.write(timeLabel.getText());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
