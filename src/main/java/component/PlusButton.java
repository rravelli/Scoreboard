/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package component;

import static component.ScoreComponent.WIDTH;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author Rémi
 */
public class PlusButton extends Button {
    private int value;
    private Label score;
    public PlusButton(int value,Label score,ScoreComponent sc) {
        super("+"+value);
        this.value = value;
        this.score = score;
        this.setPrefSize((WIDTH-40)/2,(WIDTH-40)/2);
        this.setAlignment(Pos.CENTER);
        this.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int newValue = Integer.parseInt(score.getText())+value;
                score.setText(Integer.toString(newValue));
                sc.animate();
            }
        });
    }
}
