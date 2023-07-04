import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

public class HorizontalScrolling {
    private MainGame game;

    private double screen = 0.0;
    private double crossPos = 0.0;

    private ImageView background;
    private ImageView foreground;
    private ImageView background1;
    private ImageView foreground1;
    private ImageView background2;
    private ImageView foreground2;
    private ImageView background3;
    private ImageView foreground3;

    private ImageView crosshair;

    public HorizontalScrolling(MainGame game) {
        this.game = game;
        this.crosshair = game.getCrosshair();

        game.setOnMouseMoved(e -> {
            crosshair.setTranslateX(e.getX() - background.getFitWidth() / 2);
            crosshair.setTranslateY(e.getY() - background.getFitHeight() / 2);

            crossPos = e.getX();
        });

        this.background = game.getBackground();
        this.foreground = game.getForeground();

        this.background1 = game.getBackgrounds()[0];
        this.foreground1 = game.getForegrounds()[0];
        this.background2 = game.getBackgrounds()[1];
        this.foreground2 = game.getForegrounds()[1];
        this.background3 = game.getBackgrounds()[2];
        this.foreground3 = game.getForegrounds()[2];

        
    }

    public void startScrolling() {
        AnimationTimer scrollingTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (crossPos < 32 * DuckHunt.scale) {
                    screen = Math.max(screen - 2 * DuckHunt.scale, -game.getBackground().getFitWidth());
                } else if (crossPos > 224 * DuckHunt.scale) {
                    screen = Math.min(screen + 2 * DuckHunt.scale, game.getBackground().getFitWidth());
                }
                for (Duck duck : game.getDucks()) {
                    duck.getCurrentImage().setTranslateX(duck.getX() - screen);
                }
                moveBackground();
            }
        };
        scrollingTimer.start();
    }

    public void resetBackground() {
        screen = 0.0;
    }

    private void moveBackground() {
        background1.setTranslateX(-background.getFitWidth() - screen);
        background2.setTranslateX(-screen);
        background3.setTranslateX(background.getFitWidth() - screen);

        foreground1.setTranslateX(-foreground.getFitWidth() - screen);
        foreground2.setTranslateX(-screen);
        foreground3.setTranslateX(foreground.getFitWidth() - screen);
    }
}
