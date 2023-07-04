import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

public class BackgroundSelection extends Scene {

    private int currentCrosshairIndex = 0;
    private int currentBackgroundIndex = 0;
    private boolean isEnterPressed = false;

    MediaPlayer mediaPlayer;

    public BackgroundSelection(Parent root) {
        super(root);

        ImageView background1 = new ImageView("file:assets/background/1.png");
        ImageView background2 = new ImageView("file:assets/background/2.png");
        ImageView background3 = new ImageView("file:assets/background/3.png");
        ImageView background4 = new ImageView("file:assets/background/4.png");
        ImageView background5 = new ImageView("file:assets/background/5.png");
        ImageView background6 = new ImageView("file:assets/background/6.png");

        ImageView[] backgrounds = {background1, background2, background3, background4, background5, background6};

        for (ImageView background : backgrounds) {
            background.setFitWidth(256 * DuckHunt.scale);
            background.setFitHeight(240 * DuckHunt.scale);
        }

        ImageView cross1 = new ImageView("file:assets/crosshair/1.png");
        ImageView cross2 = new ImageView("file:assets/crosshair/2.png");
        ImageView cross3 = new ImageView("file:assets/crosshair/3.png");
        ImageView cross4 = new ImageView("file:assets/crosshair/4.png");
        ImageView cross5 = new ImageView("file:assets/crosshair/5.png");
        ImageView cross6 = new ImageView("file:assets/crosshair/6.png");
        ImageView cross7 = new ImageView("file:assets/crosshair/7.png");

        ImageView[] crosses = {cross1, cross2, cross3, cross4, cross5, cross6, cross7};

        for (ImageView cross : crosses) {
            cross.setFitWidth(11 * DuckHunt.scale);
            cross.setFitHeight(11 * DuckHunt.scale);
        }

        StackPane crosshairs = new StackPane();
        crosshairs.getChildren().addAll(crosses);

        for (int i = 1; i < crosses.length; i++) {
            crosses[i].setOpacity(0);
        }

        for (int i = 1; i < backgrounds.length; i++) {
            backgrounds[i].setOpacity(0);
        }

        Text backgroundText = new Text("USE ARROW KEYS TO NAVIGATE\nPRESS ENTER TO SELECT\nPRESS ESC TO EXIT");
        backgroundText.setFont(javafx.scene.text.Font.font("Verdana", javafx.scene.text.FontWeight.BOLD, 10 * DuckHunt.scale));
        backgroundText.setFill(javafx.scene.paint.Color.ORANGE);
        backgroundText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        ((StackPane) root).getChildren().addAll(background1, background2, background3, background4, background5, background6, crosshairs, backgroundText);

        backgroundText.setTranslateY(-85 * DuckHunt.scale);

        setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP && !isEnterPressed) {
                    currentCrosshairIndex--;
                    if (currentCrosshairIndex < 0) {
                        currentCrosshairIndex = crosses.length - 1;
                    }
                    crosshairs.getChildren().clear();
                    crosshairs.getChildren().add(crosses[currentCrosshairIndex]);
                    for (int i = 0; i < crosses.length; i++) {
                        if (i == currentCrosshairIndex) {
                            crosses[i].setOpacity(1);
                        } else {
                            crosses[i].setOpacity(0);
                        }
                    }
                } else if (event.getCode() == KeyCode.DOWN && !isEnterPressed) {
                    currentCrosshairIndex++;
                    if (currentCrosshairIndex >= crosses.length) {
                        currentCrosshairIndex = 0;
                    }
                    crosshairs.getChildren().clear();
                    crosshairs.getChildren().add(crosses[currentCrosshairIndex]);
                    for (int i = 0; i < crosses.length; i++) {
                        if (i == currentCrosshairIndex) {
                            crosses[i].setOpacity(1);
                        } else {
                            crosses[i].setOpacity(0);
                        }
                    }
                } else if (event.getCode() == KeyCode.LEFT && !isEnterPressed) {
                    currentBackgroundIndex--;
                    if (currentBackgroundIndex < 0) {
                        currentBackgroundIndex = backgrounds.length - 1;
                    }
                    for (int i = 0; i < backgrounds.length; i++) {
                        if (i == currentBackgroundIndex) {
                            backgrounds[i].setOpacity(1);
                        } else {
                            backgrounds[i].setOpacity(0);
                        }
                    }
                } else if (event.getCode() == KeyCode.RIGHT && !isEnterPressed) {
                    currentBackgroundIndex++;
                    if (currentBackgroundIndex >= backgrounds.length) {
                        currentBackgroundIndex = 0;
                    }
                    for (int i = 0; i < backgrounds.length; i++) {
                        if (i == currentBackgroundIndex) {
                            backgrounds[i].setOpacity(1);
                        } else {
                            backgrounds[i].setOpacity(0);
                        }
                    }
                } else if (event.getCode() == KeyCode.ESCAPE && !isEnterPressed) {
                    DuckHunt.reset();
                } else if (event.getCode() == KeyCode.ENTER && !isEnterPressed) {
                    isEnterPressed = true;
                    TitleScreen.stopMusic();
                    Media media = new Media(new File("assets/effects/Intro.mp3").toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.setOnEndOfMedia(() -> {
                        mediaPlayer.dispose();
                        DuckHunt.stage.setScene(new MainGame(new StackPane(), currentBackgroundIndex, currentCrosshairIndex));
                    });
                    mediaPlayer.setVolume(DuckHunt.volume);
                    mediaPlayer.play();
                }
            }
        });
    }
}
