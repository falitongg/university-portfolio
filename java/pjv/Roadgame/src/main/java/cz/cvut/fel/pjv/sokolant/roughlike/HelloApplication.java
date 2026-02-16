package cz.cvut.fel.pjv.sokolant.roughlike;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    private final Image background = new Image("background.png");
    private final Image birdImage = new Image("bird1.png");
    private final double appWidth = background.getWidth();
    private final double appHeight = background.getHeight();
    @Override
    public void start(Stage stage) throws IOException {
        Canvas canvas = new Canvas(appWidth, appHeight);
        StackPane root = new StackPane(canvas);

        drawBackground(canvas);
        drawItems(canvas);
    //        ImageView imageView = new ImageView(birdImage);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(background, 0, 0);

        Scene scene = new Scene(root, appWidth, appHeight);
        stage.setTitle("Hello!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void drawBackground(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(birdImage, 100, 280);


    }
    private void drawItems(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(birdImage, 100, 260); // magic coordinates
    }


    public static void main(String[] args) {
        launch();
    }
}