import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class MainGame extends Scene {

    private ImageView background;
    private ImageView background1;
    private ImageView background2;
    private ImageView background3;
    private ImageView foreground;
    private ImageView foreground1;
    private ImageView foreground2;
    private ImageView foreground3;
    private ImageView crosshair;
    private int level;
    private Duck[] ducks;
    private int bullets;
    private boolean completed;

    private HorizontalScrolling scrolling;

    private StackPane root;

    public MainGame(Parent root) {
        super(root);
    }
    
    public MainGame(Parent root, int bgindex, int crossindex) {
        super(root);
        this.root = (StackPane)root;

        String backgroundPath = "file:assets/background/" + (bgindex+1) + ".png";
        String foregroundPath = "file:assets/foreground/" + (bgindex+1) + ".png";
        String crosshairPath = "file:assets/crosshair/" + (crossindex+1) + ".png";

        this.background = new ImageView(backgroundPath);
        this.foreground = new ImageView(foregroundPath);
        this.crosshair = new ImageView(crosshairPath);

        this.background.setFitWidth(256 * DuckHunt.scale);
        this.background.setFitHeight(240 * DuckHunt.scale);

        this.foreground.setFitWidth(256 * DuckHunt.scale);
        this.foreground.setFitHeight(240 * DuckHunt.scale);

        this.crosshair.setFitWidth(11 * DuckHunt.scale);
        this.crosshair.setFitHeight(11 * DuckHunt.scale);

        this.background1 = new ImageView(background.getImage());
        this.background2 = new ImageView(background.getImage());
        this.background3 = new ImageView(background.getImage());

        this.foreground1 = new ImageView(foreground.getImage());
        this.foreground2 = new ImageView(foreground.getImage());
        this.foreground3 = new ImageView(foreground.getImage());

        this.setCursor(javafx.scene.Cursor.NONE);

        ((StackPane)root).getChildren().addAll(background, foreground, crosshair);

        scrolling = new HorizontalScrolling(this);
        scrolling.startScrolling();

        createDucks(1);
        setLevel();
    }


    public void createDucks(int level) {
        this.level = level;
        Duck.ducks.clear();
        switch (level) {
            case 1:
                Duck duck = new Duck(-60, -50, 10, 6, "red");
                ducks = new Duck[] {duck};
                break;
            case 2:
                Duck duck1 = new Duck(60, -40, 10, 4, "black");
                Duck duck2 = new Duck(-60, -40, 10, 6, "red");
                ducks = new Duck[] {duck1, duck2};
                break;
            case 3:
                Duck duck3 = new Duck(60, 40, 10, 9, "black");
                Duck duck4 = new Duck(-60, -40, 10, 6, "red");
                Duck duck5 = new Duck(60, -40, 10, 4, "blue");
                ducks = new Duck[] {duck3, duck4, duck5};
                break;
            case 4:
                Duck duck6 = new Duck(60, 40, 10, 9, "black");
                Duck duck7 = new Duck(-60, -40, 10, 6, "red");
                Duck duck8 = new Duck(60, -40, 10, 4, "blue");
                Duck duck9 = new Duck(-60, -20, 10, 3, "red");
                ducks = new Duck[] {duck6, duck7, duck8, duck9};
                break;
            case 5:
                Duck duck10 = new Duck(60, 40, 15, 9, "black");
                Duck duck11 = new Duck(-60, -40, 15, 6, "red");
                Duck duck12 = new Duck(60, -40, 15, 4, "blue");
                Duck duck13 = new Duck(-60, -20, 15, 3, "red");
                ducks = new Duck[] {duck10, duck11, duck12, duck13};
                break;
            case 6:
                Duck duck14 = new Duck(60, 40, 15, 9, "black");
                Duck duck15 = new Duck(-60, -60, 20, 6, "red");
                Duck duck16 = new Duck(60, -80, 20, 4, "blue");
                Duck duck17 = new Duck(-60, -20, 15, 7, "red");
                ducks = new Duck[] {duck14, duck15, duck16, duck17};
                break;
        }
        bullets = ducks.length * 3;
    }

    public void setLevel() {
        root.getChildren().clear();
        scrolling.resetBackground();

        ImageView[] backgrounds = {background1, background2, background3};

        for (ImageView background : backgrounds) {
            background.setFitWidth(256 * DuckHunt.scale);
            background.setFitHeight(240 * DuckHunt.scale);
        }

        root.getChildren().addAll(backgrounds);
        background2.setTranslateX(background1.getFitWidth());
        background3.setTranslateX(background1.getFitWidth() * 2);

        for (Duck duck : ducks) {
            root.getChildren().add(duck.getCurrentImage());
        }

        ImageView[] foregrounds = {foreground1, foreground2, foreground3};

        for (ImageView foreground : foregrounds) {
            foreground.setFitWidth(256 * DuckHunt.scale);
            foreground.setFitHeight(240 * DuckHunt.scale);
        }

        root.getChildren().addAll(foregrounds);
        foreground2.setTranslateX(foreground1.getFitWidth());
        foreground3.setTranslateX(foreground1.getFitWidth() * 2);

        Text bullets = new Text("Ammo Left: " + this.bullets);
        bullets.setFont(Font.font("Verdana", FontWeight.BOLD, 8 * DuckHunt.scale));
        bullets.setFill(Color.ORANGE);
        bullets.setTranslateX(90 * DuckHunt.scale);
        bullets.setTranslateY(-110 * DuckHunt.scale);

        Text levelText = new Text("Level " + this.level + "/6");
        levelText.setFont(Font.font("Verdana", FontWeight.BOLD, 8 * DuckHunt.scale));
        levelText.setFill(Color.ORANGE);
        levelText.setTranslateY(-110 * DuckHunt.scale);

        root.getChildren().addAll(bullets, levelText);

        root.getChildren().add(crosshair);

        completed = false;

        this.setOnMouseClicked(e -> {
            if (!completed) {
                this.bullets--;
                bullets.setText("Ammo Left: " + this.bullets);
                int deadDucks = 0;
                AudioClip gunShot = new AudioClip("file:assets/effects/Gunshot.mp3");
                gunShot.setVolume(DuckHunt.volume);
                gunShot.play();
                for (Duck duck : ducks) {
                    if (duck.getCurrentImage().getBoundsInParent().contains(e.getX(), e.getY())) {
                        duck.killDuck();
                    }
                    if (duck.isShot()) {
                        deadDucks++;
                    }
                }
                if (deadDucks == ducks.length) {
                    if (level == 6) {
                        AudioClip gameCompleted = new AudioClip("file:assets/effects/GameCompleted.mp3");
                        gameCompleted.setVolume(DuckHunt.volume);
                        gameCompleted.play();
                        Text gameCompletedText = new Text("You have completed the game!");
                        gameCompletedText.setFont(Font.font("Verdana", FontWeight.BOLD, 13 * DuckHunt.scale));
                        gameCompletedText.setFill(Color.ORANGE);
                        Text keyText = new Text("Press ENTER to play again\nPress ESC to exit");
                        keyText.setFont(Font.font("Verdana", FontWeight.BOLD, 10 * DuckHunt.scale));
                        keyText.setFill(Color.ORANGE);
                        keyText.setTextAlignment(TextAlignment.CENTER);
                        keyText.setTranslateY(15 * DuckHunt.scale);
                        Timeline fading = new Timeline(new KeyFrame(Duration.seconds(0.5), event2 -> {
                            keyText.setVisible(!keyText.isVisible());
                        }));
                        fading.setCycleCount(Timeline.INDEFINITE);
                        fading.play();
                        root.getChildren().addAll(gameCompletedText, keyText);
                        completed = true;
                        this.setOnKeyPressed(event -> {
                            if (event.getCode() == KeyCode.ENTER && completed) {
                                gameCompleted.stop();
                                createDucks(1);
                                setLevel();
                                return;
                            } else if (event.getCode() == KeyCode.ESCAPE && completed) {
                                gameCompleted.stop();
                                root.getChildren().clear();
                                DuckHunt.hardReset();
                            }
                        });
                    } else {
                        AudioClip levelCompleted = new AudioClip("file:assets/effects/LevelCompleted.mp3");
                        levelCompleted.setVolume(DuckHunt.volume);
                        levelCompleted.play();
                        Text levelCompletedText = new Text("YOU WIN!");
                        levelCompletedText.setFont(Font.font("Verdana", FontWeight.BOLD, 13 * DuckHunt.scale));
                        levelCompletedText.setFill(Color.ORANGE);
                        Text keyText = new Text("Press ENTER to play next level");
                        keyText.setFont(Font.font("Verdana", FontWeight.BOLD, 10 * DuckHunt.scale));
                        keyText.setFill(Color.ORANGE);
                        keyText.setTranslateY(20 * DuckHunt.scale);
                        Timeline fading = new Timeline(new KeyFrame(Duration.seconds(0.5), event2 -> {
                            keyText.setVisible(!keyText.isVisible());
                        }));
                        fading.setCycleCount(Timeline.INDEFINITE);
                        fading.play();
                        root.getChildren().addAll(levelCompletedText, keyText);
                        completed = true;
                        
                        this.setOnKeyPressed(event -> {
                            if (event.getCode() == KeyCode.ENTER && completed) {
                                levelCompleted.stop();
                                root.getChildren().clear();
                                level += 1;
                                createDucks(level);
                                setLevel();
                            }
                        });
                    }
                }

                if (this.bullets == 0) {
                    int aliveDucks = 0;
                    for (Duck duck : ducks) {
                        if (!duck.isShot()) {
                            aliveDucks++;
                        }
                    }
                    if (aliveDucks > 0) {
                        AudioClip gameOver = new AudioClip("file:assets/effects/GameOver.mp3");
                        gameOver.setVolume(DuckHunt.volume);
                        gameOver.play();
                        Text gameOverText = new Text("GAME OVER!");
                        gameOverText.setFont(Font.font("Verdana", FontWeight.BOLD, 13 * DuckHunt.scale));
                        gameOverText.setFill(Color.ORANGE);
                        Text keyText = new Text("Press ENTER to play again\nPress ESC to exit");
                        keyText.setFont(Font.font("Verdana", FontWeight.BOLD, 10 * DuckHunt.scale));
                        keyText.setFill(Color.ORANGE);
                        keyText.setTextAlignment(TextAlignment.CENTER);
                        keyText.setTranslateY(15 * DuckHunt.scale);
                        Timeline fading = new Timeline(new KeyFrame(Duration.seconds(0.5), event2 -> {
                            keyText.setVisible(!keyText.isVisible());
                        }));
                        fading.setCycleCount(Timeline.INDEFINITE);
                        fading.play();
                        root.getChildren().addAll(gameOverText, keyText);
                        completed = true;
                        this.setOnKeyPressed(event -> {
                            if (event.getCode() == KeyCode.ENTER && completed) {
                                gameOver.stop();
                                root.getChildren().clear();
                                createDucks(1);
                                setLevel();
                                return;
                            } else if (event.getCode() == KeyCode.ESCAPE && completed) {
                                gameOver.stop();
                                root.getChildren().clear();
                                DuckHunt.hardReset();
                            }
                        });
                    }
                }
            }
        });
    }

    public ImageView getBackground() {
        return background;
    }

    public Duck[] getDucks() {
        return ducks;
    }

    public ImageView getForeground() {
        return foreground;
    }

    public ImageView getCrosshair() {
        return crosshair;
    }

    public ImageView[] getBackgrounds() {
        return new ImageView[]{background1, background2, background3};
    }

    public ImageView[] getForegrounds() {
        return new ImageView[]{foreground1, foreground2, foreground3};
    }
}
