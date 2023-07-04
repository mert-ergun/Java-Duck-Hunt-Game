import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DuckHunt extends Application {
    public static final double volume = 0.025;
    public static final int scale = 3;
    public static Stage stage;
    public static TitleScreen titleScreen;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle("Duck Hunt");
        primaryStage.getIcons().add(new Image("file:\\assets\\\\favicon\\\\1.png"));
        primaryStage.setResizable(false);

        StackPane root = new StackPane();
        titleScreen = new TitleScreen(root);

        primaryStage.setScene(titleScreen);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void reset() {
        stage.setScene(titleScreen);
    }

    public static void hardReset() {
        stage.setScene(new TitleScreen(new StackPane()));
    }
}
