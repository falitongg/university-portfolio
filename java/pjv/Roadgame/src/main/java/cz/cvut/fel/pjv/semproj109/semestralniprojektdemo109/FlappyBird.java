package cz.cvut.fel.pjv.semproj109.semestralniprojektdemo109;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FlappyBird extends Application {
    private final Image bgrImage = new Image("background.png");
    private final Image birdImage = new Image("bird1.png");
    private final double appWidth = bgrImage.getWidth();
    private final double appHeight = bgrImage.getHeight();

    @Override
    public void start(Stage stage) throws IOException {
        Canvas canvas = new Canvas(appWidth, appHeight);
        StackPane root = new StackPane(canvas);

        drawBackground(canvas);
        drawItems(canvas);

        Scene scene = new Scene(root, appWidth, appHeight);
        stage.setTitle("Flappy Bird");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void drawBackground(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(bgrImage, 0, 0);
    }

    private void drawItems(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(birdImage, 100, 280);
    }

    public static void main(String[] args) {
        launch();
    }
}