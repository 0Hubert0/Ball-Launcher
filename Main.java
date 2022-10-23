package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    static double x, y, gravity=0.4;
    static Circle[] cir = new Circle[100];
    static double[] vx = new double[100];
    static double[] vy = new double[100];
    static int counter=0;
    boolean isPressed=false, gravityOff=false;

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane root = new AnchorPane();

        Scene scene = new Scene(root, 800, 600);
        scene.setFill(Color.BEIGE);

        Line line = new Line();
        line.setFill(Color.BEIGE);
        root.getChildren().add(line);


        scene.setOnMousePressed(event -> {
            isPressed=true;
                x = event.getX();
                y = event.getY();
                line.setStartX(x);
                line.setStartY(y);
                line.setEndX(x);
                line.setEndY(y);
                line.setStroke(Color.BLACK);
        });

        scene.setOnMouseDragged(event -> {
            if(isPressed) {
                line.setEndX(event.getX());
                line.setEndY(event.getY());
            }
        });

        scene.setOnMouseReleased(event -> {
            line.setStroke(Color.BEIGE);
            isPressed=false;

            vx[counter] = (x-event.getX())/10;
            vy[counter] = (y-event.getY())/10;

            cir[counter] = new Circle(x, y, 4);
            root.getChildren().add(cir[counter]);
            counter++;
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            for (int i = 0; i <counter ; i++) {
                cir[i].setCenterX(cir[i].getCenterX()+vx[i]);
                cir[i].setCenterY(cir[i].getCenterY()+vy[i]);
                if(!gravityOff) {
                    vy[i] += gravity;
                }
                if(cir[i].getCenterY()+cir[i].getRadius()>=scene.getHeight())
                {
                    cir[i].setCenterY(scene.getHeight()-cir[i].getRadius());
                    vy[i]*=-0.6;
                    vx[i]*=0.99;
                }
                if(cir[i].getCenterY()-cir[i].getRadius()<=0)
                {
                    cir[i].setCenterY(0+cir[i].getRadius());
                    vy[i]*=-0.9;
                }
                if(cir[i].getCenterX()+cir[i].getRadius()>=scene.getWidth())
                {
                    cir[i].setCenterX(scene.getWidth()-cir[i].getRadius());
                    vx[i]*=-0.9;
                }
                if(cir[i].getCenterX()-cir[i].getRadius()<=0)
                {
                    cir[i].setCenterX(0+cir[i].getRadius());
                    vx[i]*=-0.9;
                }
                if(vy[i]>0 && vy[i]<0.6 && cir[i].getCenterY()>scene.getHeight()-cir[i].getRadius()-4)
                {
                    gravityOff=true;
                    vy[i]=0;
                    cir[i].setCenterY(scene.getHeight()-cir[i].getRadius());
                }
                if(cir[i].getCenterY()<scene.getHeight()-cir[i].getRadius()-4)
                {
                    gravityOff=false;
                }
            }
        }));

        Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            if(counter>0) {
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
            }
        }));

        timeline1.setCycleCount(Animation.INDEFINITE);
        timeline1.play();

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Appa");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
