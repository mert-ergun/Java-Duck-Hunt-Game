import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.animation.KeyFrame;

public class TitleScreen extends Scene {

    public TitleScreen(Parent root) {
        super(root);
        
        ImageView welcomeImage = new ImageView(new Image("file:assets\\welcome\\1.png"));
        welcomeImage.setFitWidth(256 * DuckHunt.scale);
        welcomeImage.setFitHeight(240 * DuckHunt.scale);
        
        Text welcomeText = new Text("PRESS ENTER TO PLAY\nPRESS ESC TO QUIT");
        welcomeText.setFont(Font.font("Verdana",FontWeight.BOLD, 18 * DuckHunt.scale));
        welcomeText.setFill(javafx.scene.paint.Color.ORANGE);
        welcomeText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(welcomeText);
        stackPane.setAlignment(javafx.geometry.Pos.CENTER);

        stackPane.setTranslateY(50 * DuckHunt.scale);

        Timeline timeline = new Timeline(
            new KeyFrame(javafx.util.Duration.seconds(0.5), e -> {
                welcomeText.setVisible(!welcomeText.isVisible());
            })
        );
        
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        AudioClip welcomeSound = new AudioClip("file:assets/effects/Title.mp3");
        welcomeSound.setVolume(DuckHunt.volume);
        welcomeSound.setCycleCount(AudioClip.INDEFINITE);
        welcomeSound.play();
        
        ((StackPane)root).getChildren().addAll(welcomeImage, stackPane);

        this.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER:
                    BackgroundSelection backgroundSelection = new BackgroundSelection(new StackPane());
                    DuckHunt.stage.setScene(backgroundSelection);
                    break;
                case ESCAPE:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        });
    }

    public static void stopMusic() {
        AudioClip welcomeSound = new AudioClip("file:assets/effects/Title.mp3");
        welcomeSound.stop();
    }
}
