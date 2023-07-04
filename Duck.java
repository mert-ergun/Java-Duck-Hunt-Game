import java.io.File;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Duck {
    private double x;
    private double y;
    private double speed;
    
    private ImageView[] crossImages;
    private ImageView[] cruiseImages;
    private ImageView shotImage;
    private ImageView fallImage;
    private ImageView currentImage = null;

    private boolean isShot;
    private int direction;
    private Timeline flyTimeline;
    private boolean canCollide = true;

    public static ArrayList<Duck> ducks = new ArrayList<Duck>();

    public Duck(double x, double y, double speed, int direction, String color) {
        this.x = x * DuckHunt.scale;
        this.y = y * DuckHunt.scale;
        this.speed = speed * DuckHunt.scale;
        this.isShot = false;
        this.direction = direction;

        switch (color) {
            case "black":
                this.crossImages = new ImageView[] {
                    new ImageView("file:assets/duck_black/1.png"),
                    new ImageView("file:assets/duck_black/2.png"),
                    new ImageView("file:assets/duck_black/3.png"),
                };
                this.cruiseImages = new ImageView[] {
                    new ImageView("file:assets/duck_black/4.png"),
                    new ImageView("file:assets/duck_black/5.png"),
                    new ImageView("file:assets/duck_black/6.png"),
                };
                this.shotImage = new ImageView("file:assets/duck_black/7.png");
                this.fallImage = new ImageView("file:assets/duck_black/8.png");
                break;
            case "blue":
                this.crossImages = new ImageView[] {
                    new ImageView("file:assets/duck_blue/1.png"),
                    new ImageView("file:assets/duck_blue/2.png"),
                    new ImageView("file:assets/duck_blue/3.png"),
                };
                this.cruiseImages = new ImageView[] {
                    new ImageView("file:assets/duck_blue/4.png"),
                    new ImageView("file:assets/duck_blue/5.png"),
                    new ImageView("file:assets/duck_blue/6.png"),
                };
                this.shotImage = new ImageView("file:assets/duck_blue/7.png");
                this.fallImage = new ImageView("file:assets/duck_blue/8.png");
                break;
            case "red":
                this.crossImages = new ImageView[] {
                    new ImageView("file:assets/duck_red/1.png"),
                    new ImageView("file:assets/duck_red/2.png"),
                    new ImageView("file:assets/duck_red/3.png"),
                };
                this.cruiseImages = new ImageView[] {
                    new ImageView("file:assets/duck_red/4.png"),
                    new ImageView("file:assets/duck_red/5.png"),
                    new ImageView("file:assets/duck_red/6.png"),
                };
                this.shotImage = new ImageView("file:assets/duck_red/7.png");
                this.fallImage = new ImageView("file:assets/duck_red/8.png");
                break;
        }

        for (ImageView image : this.crossImages) {
            image.setFitWidth(image.getImage().getWidth() * DuckHunt.scale);
            image.setFitHeight(image.getImage().getHeight() * DuckHunt.scale);
        }

        for (ImageView image : this.cruiseImages) {
            image.setFitWidth(image.getImage().getWidth() * DuckHunt.scale);
            image.setFitHeight(image.getImage().getHeight() * DuckHunt.scale);
        }

        this.shotImage.setFitWidth(this.shotImage.getImage().getWidth() * DuckHunt.scale);
        this.shotImage.setFitHeight(this.shotImage.getImage().getHeight() * DuckHunt.scale);

        this.fallImage.setFitWidth(this.fallImage.getImage().getWidth() * DuckHunt.scale);
        this.fallImage.setFitHeight(this.fallImage.getImage().getHeight() * DuckHunt.scale);

        this.currentImage = new ImageView();
        
        ducks.add(this);

        this.fly();
    }

    public void fly() {
        if (this.isShot) {
            return;
        }

        flyTimeline = new Timeline();
        flyTimeline.setCycleCount(Timeline.INDEFINITE);

        switch (this.direction) {
            case 6: // Straigth right direction (6 on numpad)
                for (int i = 0; i < this.cruiseImages.length; i++) {
                    ImageView image = this.cruiseImages[i];
                    KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 0.2 + 0.2), e -> {
                        this.currentImage.setImage(image.getImage());
                        this.currentImage.setFitWidth(image.getImage().getWidth() * DuckHunt.scale);
                        this.currentImage.setFitHeight(image.getImage().getHeight() * DuckHunt.scale);
                        currentImage.setScaleX(1);
                        x += speed;
                        currentImage.setTranslateX(x);
                        currentImage.setTranslateY(y);
                        if (isCollideHorizontal()) {
                            this.direction = 4;
                            flyTimeline.stop();
                            fly();
                        }
                        if (isCollideAnotherDuck()[0] && canCollide) {
                            canCollide = false;
                            Timeline collisionTimeChecker = new Timeline(new KeyFrame(Duration.seconds(0.5), event2 -> {
                                canCollide = true;  // Reset the flag after 0.5 seconds
                            }));
                            collisionTimeChecker.play();

                            this.direction = 4;
                            flyTimeline.stop();
                            fly();
                        }
                    });
                    flyTimeline.getKeyFrames().add(keyFrame);
                }
                break;
            case 4: // Straigth left direction (4 on numpad)
                for (int i = 0; i < this.cruiseImages.length; i++) {
                    ImageView image = this.cruiseImages[i];
                    KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 0.2 + 0.2), e -> {
                        this.currentImage.setImage(image.getImage());
                        this.currentImage.setFitWidth(image.getImage().getWidth() * DuckHunt.scale);
                        this.currentImage.setFitHeight(image.getImage().getHeight() * DuckHunt.scale);
                        currentImage.setScaleX(-1);
                        x -= speed;
                        currentImage.setTranslateX(x);
                        currentImage.setTranslateY(y);
                        if (isCollideHorizontal()) {
                            this.direction = 6;
                            flyTimeline.stop();
                            fly();
                        }
                        if (isCollideAnotherDuck()[0] && canCollide) {
                            canCollide = false;
                            Timeline collisionTimeChecker = new Timeline(new KeyFrame(Duration.seconds(0.5), event2 -> {
                                canCollide = true;  // Reset the flag after 0.5 seconds
                            }));
                            collisionTimeChecker.play();
                            this.direction = 6;
                            flyTimeline.stop();
                            fly();
                        }
                    });
                    flyTimeline.getKeyFrames().add(keyFrame);
                }
                break;
            case 9: // Diagonal right direction (9 on numpad)
                for (int i = 0; i < this.crossImages.length; i++) {
                    ImageView image = this.crossImages[i];
                    KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 0.2 + 0.2), e -> {
                        this.currentImage.setImage(image.getImage());
                        this.currentImage.setFitWidth(image.getImage().getWidth() * DuckHunt.scale);
                        this.currentImage.setFitHeight(image.getImage().getHeight() * DuckHunt.scale);
                        currentImage.setScaleX(1);
                        currentImage.setScaleY(1);
                        x += speed;
                        y -= speed;
                        currentImage.setTranslateX(x);
                        currentImage.setTranslateY(y);
                        if (isCollideHorizontal()) {
                            this.direction = 7;
                            flyTimeline.stop();
                            fly();
                        }
                        else if (isCollideVertical()) {
                            this.direction = 3;
                            flyTimeline.stop();
                            fly();
                        }
                        if (isCollideAnotherDuck()[0] && canCollide) {
                            canCollide = false;
                            Timeline collisionTimeChecker = new Timeline(new KeyFrame(Duration.seconds(0.5), event2 -> {
                                canCollide = true;  // Reset the flag after 0.5 seconds
                            }));
                            collisionTimeChecker.play();
                            if (isCollideAnotherDuck()[1]) {
                                this.direction = 3;
                            }
                            else {
                                this.direction = 7;
                            }
                            flyTimeline.stop();
                            fly();
                        }
                    });
                    flyTimeline.getKeyFrames().add(keyFrame);
                }
                break;
            case 7: // Diagonal left direction (7 on numpad)
                for (int i = 0; i < this.crossImages.length; i++) {
                    ImageView image = this.crossImages[i];
                    KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 0.2 + 0.2), e -> {
                        this.currentImage.setImage(image.getImage());
                        this.currentImage.setFitWidth(image.getImage().getWidth() * DuckHunt.scale);
                        this.currentImage.setFitHeight(image.getImage().getHeight() * DuckHunt.scale);
                        currentImage.setScaleX(-1);
                        currentImage.setScaleY(1);
                        x -= speed;
                        y -= speed;
                        currentImage.setTranslateX(x);
                        currentImage.setTranslateY(y);
                        if (isCollideHorizontal()) {
                            this.direction = 9;
                            flyTimeline.stop();
                            fly();
                        }
                        else if (isCollideVertical()) {
                            this.direction = 1;
                            flyTimeline.stop();
                            fly();
                        }
                        if (isCollideAnotherDuck()[0] && canCollide) {
                            canCollide = false;
                            Timeline collisionTimeChecker = new Timeline(new KeyFrame(Duration.seconds(0.5), event2 -> {
                                canCollide = true;  // Reset the flag after 0.5 seconds
                            }));
                            collisionTimeChecker.play();
                            if (isCollideAnotherDuck()[1]) {
                                this.direction = 1;
                            }
                            else {
                                this.direction = 9;
                            }
                            flyTimeline.stop();
                            fly();
                        }
                    });
                    flyTimeline.getKeyFrames().add(keyFrame);
                }
                break;
            case 1: // Diagonal left direction (1 on numpad)
                for (int i = 0; i < this.crossImages.length; i++) {
                    ImageView image = this.crossImages[i];
                    KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 0.2 + 0.2), e -> {
                        this.currentImage.setImage(image.getImage());
                        this.currentImage.setFitWidth(image.getImage().getWidth() * DuckHunt.scale);
                        this.currentImage.setFitHeight(image.getImage().getHeight() * DuckHunt.scale);
                        currentImage.setScaleX(-1);
                        currentImage.setScaleY(-1);
                        x -= speed;
                        y += speed;
                        currentImage.setTranslateX(x);
                        currentImage.setTranslateY(y);
                        if (isCollideHorizontal()) {
                            this.direction = 3;
                            flyTimeline.stop();
                            fly();
                        }
                        else if (isCollideVertical()) {
                            this.direction = 7;
                            flyTimeline.stop();
                            fly();
                        }
                        if (isCollideAnotherDuck()[0] && canCollide) {
                            canCollide = false;
                            Timeline collisionTimeChecker = new Timeline(new KeyFrame(Duration.seconds(0.5), event2 -> {
                                canCollide = true;  // Reset the flag after 0.5 seconds
                            }));
                            collisionTimeChecker.play();
                            if (isCollideAnotherDuck()[1]) {
                                this.direction = 7;
                            }
                            else {
                                this.direction = 3;
                            }
                            flyTimeline.stop();
                            fly();
                        }
                    });
                    flyTimeline.getKeyFrames().add(keyFrame);
                }
                break;
            case 3: // Diagonal right direction (3 on numpad)
                for (int i = 0; i < this.crossImages.length; i++) {
                    ImageView image = this.crossImages[i];
                    KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 0.2 + 0.2), e -> {
                        this.currentImage.setImage(image.getImage());
                        this.currentImage.setFitWidth(image.getImage().getWidth() * DuckHunt.scale);
                        this.currentImage.setFitHeight(image.getImage().getHeight() * DuckHunt.scale);
                        currentImage.setScaleX(1);
                        currentImage.setScaleY(-1);
                        x += speed;
                        y += speed;
                        currentImage.setTranslateX(x);
                        currentImage.setTranslateY(y);
                        if (isCollideHorizontal()) {
                            this.direction = 1;
                            flyTimeline.stop();
                            fly();
                        }
                        else if (isCollideVertical()) {
                            this.direction = 9;
                            flyTimeline.stop();
                            fly();
                        }
                        if (isCollideAnotherDuck()[0] && canCollide) {
                            canCollide = false;
                            Timeline collisionTimeChecker = new Timeline(new KeyFrame(Duration.seconds(0.5), event2 -> {
                                canCollide = true;  // Reset the flag after 0.5 seconds
                            }));
                            collisionTimeChecker.play();
                            if (isCollideAnotherDuck()[1]) {
                                this.direction = 9;
                            }
                            else {
                                this.direction = 1;
                            }
                            flyTimeline.stop();
                            fly();
                        }
                    });
                    flyTimeline.getKeyFrames().add(keyFrame);
                }
                break;
        }

        flyTimeline.play();
    }

    public boolean isCollideHorizontal() {
        int width = 256 * DuckHunt.scale * 3;

        if (this.x < -width / 2 + this.currentImage.getFitWidth() / 2 || this.x > width / 2 - this.currentImage.getFitWidth() / 2) {
            return true;
        }
        
        return false;
    }

    public boolean isCollideVertical() {
        int height = 240 * DuckHunt.scale;

        if (this.y < -height / 2 + this.currentImage.getFitHeight() / 2 || this.y > height / 2 - this.currentImage.getFitHeight() / 2) {
            if (this.isShot) {
                Duck.ducks.remove(this);
            }
            return true;
        }
        
        return false;
    }

    public boolean[] isCollideAnotherDuck() {
        for (Duck duck : Duck.ducks) {
            if (duck != this) {
                if (this.x + this.currentImage.getFitWidth() / 2 > duck.x - duck.currentImage.getFitWidth() / 2 && 
                this.x - this.currentImage.getFitWidth() / 2 < duck.x + duck.currentImage.getFitWidth() / 2 && 
                this.y + this.currentImage.getFitHeight() / 2 > duck.y - duck.currentImage.getFitHeight() / 2 && 
                this.y - this.currentImage.getFitHeight() / 2 < duck.y + duck.currentImage.getFitHeight() / 2) {
                    // Check if is this duck below the other duck
                    if (this.y > duck.y) {
                        return new boolean[] {true, true};
                    }
                    else {
                        return new boolean[] {true, false};
                    }
                }
            }
        }

        return new boolean[] {false, false};
    }

    public void killDuck() {
        if (this.isShot) {
            return;
        }
        this.isShot = true;
        flyTimeline.stop();
        if (direction == 4 || direction == 7 || direction == 1) {
            this.shotImage.setScaleX(-1);
            this.fallImage.setScaleX(-1);
            this.fallImage.setScaleY(1);
            this.shotImage.setScaleY(1);
        } 
        this.currentImage.setImage(this.shotImage.getImage());
        currentImage.setFitWidth(shotImage.getImage().getWidth() * DuckHunt.scale);
        currentImage.setFitHeight(shotImage.getImage().getHeight() * DuckHunt.scale);
        Timeline shotTimeline = new Timeline();
        shotTimeline.setCycleCount(1);
        KeyFrame shotKeyFrame = new KeyFrame(Duration.seconds(0.5), e -> {
            currentImage.setImage(this.fallImage.getImage());
            currentImage.setFitWidth(fallImage.getImage().getWidth() * DuckHunt.scale);
            currentImage.setFitHeight(fallImage.getImage().getHeight() * DuckHunt.scale);
            currentImage.setScaleY(1);
            Timeline fallTimeline = new Timeline();
            fallTimeline.setCycleCount(Timeline.INDEFINITE);
            KeyFrame fallKeyFrame = new KeyFrame(Duration.seconds(0.2), event -> {
                y += 20 * DuckHunt.scale;
                currentImage.setTranslateY(y);
                if (!Duck.ducks.contains(this)) {
                    fallTimeline.stop();
                }
                isCollideVertical();
            });
            fallTimeline.getKeyFrames().add(fallKeyFrame);
            fallTimeline.play();
            MediaPlayer fallSound = new MediaPlayer(new Media(new File("assets/effects/DuckFalls.mp3").toURI().toString()));
            fallSound.setVolume(DuckHunt.volume);
            fallSound.play();
            // If duck hits the ground, remove it from the list
        });
        shotTimeline.getKeyFrames().add(shotKeyFrame);
        shotTimeline.play();
    }

    public ImageView getCurrentImage() {
        return this.currentImage;
    }

    public boolean isShot() {
        return this.isShot;
    }

    public double getX() {
        return this.x;
    }
}
